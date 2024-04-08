package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    int countAllByMigrateFalse();

    Film findByTitle(String title);

    boolean existsByTitle(String title);

    List<Film> findByTitleContainingIgnoreCase(String title);
    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT f FROM Film f WHERE UPPER(f.diretor.name) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(f.diretor.surname) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Film> findByDiretorNameContainingIgnoreCaseOrDiretorSurnameContainingIgnoreCase(@Param("name") String name, Pageable pageable);


    @Transactional
    @Modifying
    @Query("UPDATE Film f SET f.migrate = true, f.dateMigration = :dateToday WHERE f.migrate = false")
    void updateMigratedFilm(@Param("dateToday") LocalDateTime date);

}
