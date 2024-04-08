package com.tokioschool.filmotokio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDTO {


    @NotBlank(message = "Titulo: " + FormVal.REQUIRED_FIELD)
    private String title;

    @NotBlank(message = "Review: " + FormVal.REQUIRED_FIELD)
    private String textReview;

    @NotNull(message = "FilmId: " + FormVal.REQUIRED_FIELD)
    private Long filmId;



}
