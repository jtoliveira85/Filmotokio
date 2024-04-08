package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.domain.enums.RoleEnum;
import com.tokioschool.filmotokio.domain.enums.TypePersonEnum;
import com.tokioschool.filmotokio.repository.PersonRepository;
import com.tokioschool.filmotokio.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public boolean existsByNameAndSurname(String name, String surname) {
        return personRepository.existsByNameAndSurname(name, surname);
    }

    @Override
    public boolean existsByNameAndSurnameAndType(String name, String surname, TypePersonEnum typePerson) {
        return personRepository.existsByNameAndSurnameAndType(name, surname, typePerson);
    }

    @Override
    public boolean save(Person person) {

        if (person != null) {
            personRepository.save(person);
            logger.info("PersonServer: {}: {} {} Saved!",
                    person.getTypePersonEnum() ,person.getName(), person.getSurname());
            return true;
        }

        return false;
    }

    @Override
    public Set<Person> getAlldiretores() {

        List<Person> allPersons = (List<Person>) personRepository.findAll();
        List<Person> allDiretores = allPersons.stream().filter(p -> p.getTypePersonEnum() == TypePersonEnum.DIRETOR).toList();

        return new HashSet<>(allDiretores);
    }

    @Override
    public Set<Person> getAllfotografos() {

        List<Person> allPersons = (List<Person>) personRepository.findAll();
        List<Person> allFotografos = allPersons.stream().filter(p -> p.getTypePersonEnum() == TypePersonEnum.FOTOGRAFO).toList();

        return new HashSet<>(allFotografos);
    }

    @Override
    public Set<Person> getAllActores() {
        List<Person> allPersons = (List<Person>) personRepository.findAll();
        List<Person> allActores = allPersons.stream().filter(p -> p.getTypePersonEnum() == TypePersonEnum.ACTOR).toList();

        return new HashSet<>(allActores);
    }

    @Override
    public Set<Person> getAllMusicos() {

        List<Person> allPersons = (List<Person>) personRepository.findAll();
        List<Person> allAllMusicos = allPersons.stream().filter(p -> p.getTypePersonEnum() == TypePersonEnum.MUSICO).toList();

        return new HashSet<>(allAllMusicos);

    }

    @Override
    public Set<Person> getAllGuionistas() {

        List<Person> allPersons = personRepository.findAll();
        List<Person> allGuionistas = allPersons.stream().filter(p -> p.getTypePersonEnum() == TypePersonEnum.GUIONISTA).toList();

        return new HashSet<>(allGuionistas);
    }
}
