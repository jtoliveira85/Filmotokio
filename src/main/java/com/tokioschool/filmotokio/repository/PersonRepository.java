package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePersonEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Person p WHERE p.name = :name AND p.surname = :surname")
    boolean existsByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    @Query("SELECT COUNT(p) > 0 FROM Person p WHERE p.name = :name AND p.surname = :surname AND p.typePersonEnum = :typePersonEnum")
    boolean existsByNameAndSurnameAndType(@Param("name") String name, @Param("surname") String surname, @Param("typePersonEnum") TypePersonEnum typePersonEnum);

}
