package com.tokioschool.filmotokio.util;

import java.time.LocalDate;
import java.util.Date;

public class FormVal {


    public static final String REQUIRED_FIELD = "Campo obrigatório";

    public static final String SIZE_MIN_6 = " deve ter no mínimo 6 caracteres";
    public static final String SIZE_MIN_4 = " deve ter no mínimo 4 caracteres";
    public static final String EMAIL = "Formato de e-mail inválido";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

    public static final String PASS_EQ_USERNAME = "A password não pode ser igual ao username";

    public static final String USERNAME_EXISTS = "Já existe registo para este username";

    public static final String EMAIL_EXISTS = "Já existe registo para este email";;

    public static final String PERSON_EXISTS = "Já existe uma pessoa registada com este nome";

    public static final String PERSON_TYPE_EXISTS = "Já existe uma pessoa registada com este nome com o mesmo Tipo";

    public static final String FILM_EXISTS = "Já existe um filme registado com este titulo";


    private static Integer geyCurrYear () {
        return LocalDate.now().getYear();
    }

}
