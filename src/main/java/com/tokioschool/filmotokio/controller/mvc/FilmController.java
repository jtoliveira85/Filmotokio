package com.tokioschool.filmotokio.controller.mvc;

import com.tokioschool.filmotokio.domain.*;
import com.tokioschool.filmotokio.exception.EntityRegistrationException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import com.tokioschool.filmotokio.security.SecurityUtils;
import com.tokioschool.filmotokio.service.*;
import com.tokioschool.filmotokio.util.DirectoryUtil;
import com.tokioschool.filmotokio.util.FormVal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/film")
public class FilmController {

    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final PersonService personService;
    private final FilmService filmService;
    private final FileService fileService;
    private final UserService userService;
    private final ScoreService scoreService;

    private final ReviewService reviewService;

    @Autowired
    public FilmController(PersonService personService, FilmService filmService, FileService fileService, ScoreService scoreService, UserService userService, ReviewService reviewService) {
        this.personService = personService;
        this.filmService = filmService;
        this.fileService = fileService;
        this.scoreService = scoreService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, "search", stringTrimmerEditor);
    }

    @GetMapping("/create")
    public String createFilm(Model model) {
        model.addAttribute("newFilm", new Film());

        model.addAttribute("diretorList", personService.getAlldiretores());
        model.addAttribute("fotografoList", personService.getAllfotografos());
        model.addAttribute("actoresList", personService.getAllActores());
        model.addAttribute("musicosList", personService.getAllMusicos());
        model.addAttribute("guionistasList", personService.getAllGuionistas());


//        User user = SecurityUtils.loggedUser();

        return "film/create-film";
    }

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ModelAndView save(@Valid @ModelAttribute("newFilm") Film newFilm, BindingResult result, @RequestParam("posterFilm") MultipartFile imageFile, RedirectAttributes redirectAttributes) throws EntityRegistrationException {

        Long savedFilmId = null;
        ModelAndView mv = new ModelAndView("film/create-film");

        Set<Person> atoresSelecionados = newFilm.getActores();
        Set<Person> musicosSelecionados = newFilm.getMusicos();
        Set<Person> guionistasSelecionados = newFilm.getGuionistas();

        System.out.println("........................................");
        atoresSelecionados.forEach(System.out::println);
        System.out.println("........................................");

        mv.addObject("atoresSelecionados", atoresSelecionados);



        logger.info("Film Controller -> Add new Film");

        if (result.hasErrors() || imageFile.isEmpty()) {

            return returnToCreateFilm(mv, imageFile);
        }

        if (filmService.existsByTitle(newFilm.getTitle())) {
            logger.warn("Film Controler -> Add new Film -> Film already exists: {}", newFilm.getTitle());
            result.rejectValue("title", null, "Filme: " + FormVal.FILM_EXISTS);
        }


        if (!imageFile.isEmpty()) {
            boolean isFileUploaded = fileService.uploadFilmFile(imageFile, newFilm);

            if (isFileUploaded) {
                savedFilmId = filmService.save(newFilm);
            }
        }

        if (savedFilmId != null) {
            redirectAttributes.addFlashAttribute("message", "Filme Registado com sucesso: " + newFilm.getTitle());
        }

        mv.setViewName("redirect:/");
        return mv;
    }


    @GetMapping("{filmName}")
    public ModelAndView showFilm(@PathVariable("filmName") String filmName) {

        System.out.println("FILM TITLE: " + filmName);

        Film film = filmService.findByTitle(filmName);

        Double filmScore = scoreService.getScoreByFilmId(film.getId());
        filmScore = (filmScore != null) ? filmScore : 0.0;

        Score myScore = scoreService.findScoreByFilmIdAndUserId(film.getId(), SecurityUtils.getLoggedUserId());
        Review myReview = reviewService.findByFilmIdAndUserId(film.getId(), SecurityUtils.getLoggedUserId());
        List<Review> allReviews = reviewService.findAllByFilmId(film.getId());

        ModelAndView mv = new ModelAndView();
        mv.setViewName("film/show-film");

        mv.addObject("film", film);
        mv.addObject("filmImageURL", DirectoryUtil.IMAGE_FILM_DIR);
        mv.addObject("filmScore", filmScore);
        mv.addObject("myScore", myScore);
        mv.addObject("myReview", myReview);
        mv.addObject("allReviews", allReviews);

        return mv;
    }

    @PostMapping("/score/{id}")
    public ModelAndView scoreFilm(@PathVariable("id") Long filmId,
                                  @RequestParam(value = "newScore", required = false) @Nullable Integer scoreValue,
                                  RedirectAttributes redirectAttributes) {
        Film film = filmService.findById(filmId);

        System.out.println("FILM ID: " + filmId);
        System.out.println("SCORE: " + scoreValue);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/film/" + film.getTitle());

        if (scoreValue == null) {
            System.out.println("scoreValue: " + scoreValue);
            redirectAttributes.addFlashAttribute("scoreError", "Selecione um valor!");
            return mv;
        }

        Score score = scoreService.findScoreByFilmIdAndUserId(filmId, SecurityUtils.getLoggedUserId());

        if (score == null) {
            score = new Score();
        }

        score.setValue(scoreValue);
        score.setFilm(film);
        score.setUser(userService.findById(SecurityUtils.getLoggedUserId()));
        scoreService.save(score);

        System.out.println("OK");

        return mv;
    }

    @PostMapping("/review/{id}")
    public ModelAndView reviewFilm(@PathVariable("id") Long filmId,
                                   @RequestParam(value = "myReviewTitle") String reviewTitle,
                                   @RequestParam(value = "myReviewText") String reviewText,
                                   RedirectAttributes redirectAttributes) {

        Film film = filmService.findById(filmId);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/film/" + film.getTitle());

        Review review = reviewService.findByFilmIdAndUserId(filmId, SecurityUtils.getLoggedUserId());

        if (reviewText == null || reviewText.isBlank() || reviewTitle == null || reviewTitle.isBlank()) {
            redirectAttributes.addFlashAttribute("reviewError", "Todos os campos são obrigatórios!");
            redirectAttributes.addFlashAttribute("myReviewTitle", reviewTitle);
            redirectAttributes.addFlashAttribute("myReviewText", reviewText);
            return mv;
        }

        reviewText = reviewText.trim();

        if (review == null) {
            review = new Review();
        }

        review.setTitle(reviewTitle);
        review.setTextReview(reviewText);
        review.setUser(userService.findById(SecurityUtils.getLoggedUserId()));
        review.setFilm(film);
        review.setDate(LocalDateTime.now());

        reviewService.save(review);

        return mv;
    }


    private ModelAndView returnToCreateFilm(ModelAndView mv, MultipartFile file){

        mv.addObject("diretorList", personService.getAlldiretores());
        mv.addObject("fotografoList", personService.getAllfotografos());
        mv.addObject("actoresList", personService.getAllActores());
        mv.addObject("musicosList", personService.getAllMusicos());
        mv.addObject("guionistasList", personService.getAllGuionistas());

        if (file.isEmpty()) {
            mv.addObject("posterFilmError", "Imagem: " + FormVal.REQUIRED_FIELD);
        }

        return mv;
    }


    @GetMapping("/search")
    public ModelAndView searchFilm(@RequestParam(name = "search", required = false) String search,
                                   @RequestParam(name = "pageNumber", defaultValue = "0") int page,
                                   @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(name = "searchType", required = false) String searchType) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("film/film-list");

        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Film> filmPage;
        if (searchType == null) {
            searchType = "film";
        }
        filmPage = switch (searchType) {
            case "film" -> (search != null && !search.isEmpty()) ?
                    filmService.searchFilmsByTitlePageable(search, page, pageSize) :
                    filmService.findAllPageable(page, pageSize);
            case "diretor" -> (search != null && !search.isEmpty()) ?
                    filmService.searchFilmByDirectorNamePageable(search, page, pageSize) :
                    filmService.findAllPageable(page, pageSize);
            default -> filmService.findAllPageable(page, pageSize);
        };

        mv.addObject("filmPage", filmPage);

        int totalPages = (int) Math.ceil((double) filmPage.getTotalElements() / pageSize);

        mv.addObject("totalPages", totalPages);

        mv.addObject("pageNumber", page);
        mv.addObject("search", search);
        mv.addObject("pageSize", pageSize);
        mv.addObject("searchType", searchType);

        return mv;
    }


}
