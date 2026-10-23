package com.github.mizo432.xplanner.supporting;

import java.io.Serializable;
import java.security.Principal;

import com.github.mizo432.xplanner.buisiness.model.Person;

public class PersonPrincipal implements Principal, Serializable {
    private Person person;

    public PersonPrincipal(Person person) {
        this.person = person;
    }

    public String getName() {
        return person.getUserId();
    }

    public Person getPerson() {
        return person;
    }
}
