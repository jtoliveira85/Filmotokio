package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePersonEnum;
import com.tokioschool.filmotokio.dto.CreateUserDTO;

import java.util.Set;

public interface PersonService {

    boolean existsByNameAndSurname(String name, String surname);

    boolean existsByNameAndSurnameAndType(String name, String surname, TypePersonEnum typePerson);

    boolean save(Person person);

    Set<Person> getAlldiretores();
    Set<Person> getAllfotografos();
    Set<Person> getAllActores();
    Set<Person> getAllMusicos();
    Set<Person> getAllGuionistas();

}
