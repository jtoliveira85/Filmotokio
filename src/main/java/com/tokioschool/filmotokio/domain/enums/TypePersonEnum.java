package com.tokioschool.filmotokio.domain.enums;

public enum TypePersonEnum {


    ACTOR("Actor"),
    DIRETOR("Diretor"),
    FOTOGRAFO("Fotografo"),
    GUIONISTA("Guionista"),
    MUSICO("Musico");



    private final String typePerson;

    TypePersonEnum(String typePerson) {
        this.typePerson = typePerson;
    }

    public String getTypePerson() {
        return typePerson;
    }
}
