package com.tokioschool.filmotokio.controller.rest;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.dto.CreateReviewDTO;
import com.tokioschool.filmotokio.dto.ReviewDTO;
import com.tokioschool.filmotokio.exception.RequiredObjectsIsNullException;
import com.tokioschool.filmotokio.exception.ResourceAlreadyExistsException;
import com.tokioschool.filmotokio.exception.ResourceNotFoundException;
import com.tokioschool.filmotokio.exception.api.ExceptionResponse;
import com.tokioschool.filmotokio.mapper.Mapper;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.security.FilmoTokioUserDetailsService;
import com.tokioschool.filmotokio.security.SecurityUtils;
import com.tokioschool.filmotokio.security.jwt.JwtRequest;
import com.tokioschool.filmotokio.security.jwt.JwtResponse;
import com.tokioschool.filmotokio.security.jwt.JwtTokenUtil;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.ReviewService;
import com.tokioschool.filmotokio.service.UserService;

import com.tokioschool.filmotokio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private final ReviewService reviewService;

    private final FilmService filmService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final FilmoTokioUserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;



    @Autowired
    public ApiRestController(ReviewService reviewService, AuthenticationManager authenticationManager, FilmoTokioUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
                             FilmService filmService, UserService userService) {
        this.reviewService = reviewService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.filmService = filmService;
        this.userService = userService;
    }


    @Operation(summary = "Login de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtem um token", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "500", description = "Dados incorretos", content = @Content),
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) throws Exception {

        authenticate(authRequest.getUsername(), authRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @Operation(summary = "Adicionar uma Review", description = "Adiciona uma Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Não Autorizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe review do user atual para este filme"),
            @ApiResponse(responseCode = "201", description = "Review Registada", content = @Content(schema = @Schema(implementation = CreateReviewDTO.class)))

    })
    @PostMapping(value = "/new-review", consumes = "application/json")
    public ResponseEntity<?> newReview(@Valid @RequestBody CreateReviewDTO createReviewDTO, BindingResult result){

        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed for the following fields: ");
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }


        Film filmToReview = filmService.findById(createReviewDTO.getFilmId());

        if (filmToReview == null) {
            throw new ResourceNotFoundException("Film com id: " + createReviewDTO.getFilmId() + ", Não existe:");
        }

        Review review = reviewService.findByFilmIdAndUserId(createReviewDTO.getFilmId(), SecurityUtils.getLoggedUserId());

        if (review != null) {
            throw new ResourceAlreadyExistsException("Já existe uma review deste usuario para este filme");
        }

        Review newReview = Mapper.parseObject(createReviewDTO, Review.class);

        newReview.setFilm(filmService.findById(createReviewDTO.getFilmId()));
        newReview.setUser(userService.findById(SecurityUtils.getLoggedUserId()));
        newReview.setDate(LocalDateTime.now());

        reviewService.save(newReview);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @Operation(summary = "Obter reviews por user id", description = "Obtem Uma review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostra as reviews para o user id", content = @Content(schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "403", description = "Não autorizado", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User não existe", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping(value = "/user/{id}/reviews", produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<List<ReviewDTO>> showReviewsByUserId(@PathVariable("id") Long userId) {

        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + userId);
        }

        List<ReviewDTO> listReviews = Mapper.parseListObjects(reviewService.findAllByUserId(userId), ReviewDTO.class);

        return ResponseEntity.ok(listReviews);
    }




    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException bce) {
            throw new Exception("Invalid credentials");
        }
    }

}
