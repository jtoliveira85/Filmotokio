package com.tokioschool.filmotokio.domain;

import com.tokioschool.filmotokio.domain.enums.RoleEnum;
import com.tokioschool.filmotokio.domain.enums.TypePersonEnum;
import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Nome: " + FormVal.REQUIRED_FIELD)
    private String name;

    @Column
    @NotBlank(message = "Surname: " + FormVal.REQUIRED_FIELD)
    private String surname;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo: " + FormVal.REQUIRED_FIELD)
    @Column(name = "type_person")
    private TypePersonEnum typePersonEnum;

    // Atores
    @ManyToMany(mappedBy = "actores", fetch = FetchType.EAGER)
    private Set<Film> filmsActor;

    @ManyToMany(mappedBy = "musicos", fetch = FetchType.EAGER)
    private Set<Film> filmMusicos;

    @ManyToMany(mappedBy = "guionistas", fetch = FetchType.EAGER)
    private Set<Film> filmGuionistas;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", typePersonEnum=" + typePersonEnum +
                ", filmsActor=" + filmsActor +
                ", filmMusicos=" + filmMusicos +
                ", filmGuionistas=" + filmGuionistas +
                '}';
    }

    public String getFullName() {
        return name + " " + surname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
