package com.tokioschool.filmotokio.domain;

import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "film")
public class Film {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Titulo: " + FormVal.REQUIRED_FIELD)
    private String title;

    @Column
    @NotNull(message = "Ano: " + FormVal.REQUIRED_FIELD)
    @Digits(integer = 4, fraction = 0, message = "Ano: O ano deve ter 4 dígitos")
    @Range(min = 1900, max = 2100, message = "Ano: inválido")
    private Integer year;

    @Column
    @NotNull(message = "Duração: " + FormVal.REQUIRED_FIELD)
    @Range(min = 1, max = 9999, message = "Duração: Deve ser entre 1 e 9999")
    private Integer duration;

    @Column
    @NotBlank(message = "Sinopse: " + FormVal.REQUIRED_FIELD)
    private String sypnosis;

    @Column
//    @NotNull(message = "Imagem: " + FormVal.REQUIRED_FIELD)
//    @NotBlank(message = "Imagem: " + FormVal.REQUIRED_FIELD)
    private String poster;

    @Column
    private boolean migrate;

    @Column(name = "date_migration")
    private LocalDateTime dateMigration;

    // Relações
    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    private final Set<Review> reviews = new HashSet<>();


    // PERONS
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    @NotNull(message = "Diretor: " + FormVal.REQUIRED_FIELD)
    private Person diretor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fotografo_id")
    @NotNull(message = "Fotógrafo: " + FormVal.REQUIRED_FIELD)
    private Person fotografo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_actores",
            joinColumns = @JoinColumn(name = "id_film"),
            inverseJoinColumns = @JoinColumn(name = "id_actor"))
    @Size(min = 1, message = "Atore(s): " + FormVal.REQUIRED_FIELD)
    private Set<Person> actores;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_musicos",
            joinColumns = @JoinColumn(name = "id_filme"),
            inverseJoinColumns = @JoinColumn(name = "id_musico")
    )
    @Size(min = 1, message = "Musico(s): " + FormVal.REQUIRED_FIELD)
    private Set<Person> musicos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_guionistas",
            joinColumns = @JoinColumn(name = "id_filme"),
            inverseJoinColumns = @JoinColumn(name = "id_guionista")
    )
    @Size(min = 1, message = "Guionista(s): " + FormVal.REQUIRED_FIELD)
    private Set<Person> guionistas;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "film", fetch = FetchType.EAGER)
    private Set<Score>scores;


    public String getURLforFilm() {
        return this.title.replaceAll("[^a-zA-Z0-9-]", "-");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", scores=" + scores +
                '}';
    }

    public Double getFilmScore() {

        double totalScore = 0;
        for (Score score : scores) {
            totalScore += score.getValue();
        }

        return totalScore / scores.size();
    }
}
