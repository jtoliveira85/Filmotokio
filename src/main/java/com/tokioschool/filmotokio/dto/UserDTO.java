package com.tokioschool.filmotokio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username: " + FormVal.REQUIRED_FIELD)
    @Size(min = 4, message = "Username: " + FormVal.SIZE_MIN_4)
    private String username;

    @NotBlank(message = "Nome: " + FormVal.REQUIRED_FIELD)
    private String name;

    @NotBlank(message = "Apelido: " + FormVal.REQUIRED_FIELD)
    private String surname;

    @NotBlank(message = "Email: " + FormVal.REQUIRED_FIELD)
    @Pattern(regexp = FormVal.EMAIL_REGEX, message =  FormVal.EMAIL)
    private String email;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Europe/Lisbon")
    private Date birthDate;

    private String image;

//    private Set<Role> roles = new HashSet<>();

}
