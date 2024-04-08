package com.tokioschool.filmotokio.domain;

import com.tokioschool.filmotokio.util.FormVal;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "Score: " + FormVal.REQUIRED_FIELD)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", value=" + value +
//                ", user=" + user +
//                ", film=" + film +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Objects.equals(id, score.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
