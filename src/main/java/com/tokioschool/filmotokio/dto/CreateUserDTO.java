package com.tokioschool.filmotokio.dto;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Username: " + FormVal.REQUIRED_FIELD)
    @Size(min = 4, message = "Username: " + FormVal.SIZE_MIN_4)
    private String username;

    @NotBlank(message = "Password: " + FormVal.REQUIRED_FIELD)
    @Size(min = 4, message = "Password: " + FormVal.SIZE_MIN_4)
    private String password;

    @NotBlank(message = "Nome: " + FormVal.REQUIRED_FIELD)
    private String name;

    @NotBlank(message = "Apelido: " + FormVal.REQUIRED_FIELD)
    private String surname;

    @NotBlank(message = "Email: " + FormVal.REQUIRED_FIELD)
    @Pattern(regexp = FormVal.EMAIL_REGEX, message = "Email: " + FormVal.EMAIL)
    private String email;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;


    private Set<Role> roles = new HashSet<>();


    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
