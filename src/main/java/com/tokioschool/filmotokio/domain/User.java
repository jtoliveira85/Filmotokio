package com.tokioschool.filmotokio.domain;

import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = FormVal.REQUIRED_FIELD)
    @Size(min = 4, message = "O username" + FormVal.SIZE_MIN_4)
    private String username;

    @Column(name = "password")
    @NotBlank(message = FormVal.REQUIRED_FIELD)
    @Size(min = 4, message = "A password" + FormVal.SIZE_MIN_4)
    private String password;

    @Column(name = "name")
    @NotBlank(message = FormVal.REQUIRED_FIELD)
    private String name;

    @Column(name = "surname")
    @NotBlank(message = FormVal.REQUIRED_FIELD)
    private String surname;

    @Column(name = "email")
    @NotBlank(message = FormVal.REQUIRED_FIELD)
    @Pattern(regexp = FormVal.EMAIL_REGEX, message = FormVal.EMAIL)
    private String email;

    @Column(name = "image")
    private String image;


    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles  = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private final Set<Review> reviews = new HashSet<>();

   @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Film> films;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
