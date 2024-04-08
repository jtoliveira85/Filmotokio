package com.tokioschool.filmotokio.controller.mvc;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePersonEnum;
import com.tokioschool.filmotokio.exception.EntityRegistrationException;
import com.tokioschool.filmotokio.service.PersonService;
import com.tokioschool.filmotokio.util.FormVal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));

    }


    @GetMapping("/create")
    public String createFilmm(Model model) {

        model.addAttribute("newPerson", new Person());
        model.addAttribute("personTypes", TypePersonEnum.values());

        return "film/create-person";
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("newPerson") Person newPerson, BindingResult result, RedirectAttributes redirectAttributes) throws EntityRegistrationException {

        ModelAndView mv = new ModelAndView("film/create-person");

        logger.info("Peron Controler -> Add new Person");

        if (result.hasErrors()) {
            mv.addObject("personTypes", TypePersonEnum.values());
            return mv;
        }
        if (personService.existsByNameAndSurnameAndType(newPerson.getName(), newPerson.getSurname(), newPerson.getTypePersonEnum())) {
            result.rejectValue("name",
                    null,
                    FormVal.PERSON_TYPE_EXISTS + String.format(" %s: %s %s",newPerson.getTypePersonEnum(), newPerson.getName(), newPerson.getSurname()));
            logger.warn("Person Controler -> Add new Person -> Person for seme type: {} already exists: {} {}", newPerson.getTypePersonEnum(), newPerson.getName(), newPerson.getSurname());
            mv.addObject("personTypes", TypePersonEnum.values());
            return mv;
        }

        boolean isPersonAdded = personService.save(newPerson);

        if (!isPersonAdded) {
            logger.error("Peron Controler -> Erro ao Registar uma Pessoa");
            throw new EntityRegistrationException("Erro ao Criar uma Pessoa");
        }

        redirectAttributes.addFlashAttribute("message", newPerson.getTypePersonEnum().getTypePerson() + " " + newPerson.getFullName() + " Registado com sucesso!");
        mv.setViewName("redirect:/");
        return mv;
    }
}
