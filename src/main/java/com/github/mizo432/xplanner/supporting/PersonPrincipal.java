package com.github.mizo432.xplanner.supporting;

import java.io.Serializable;
import java.security.Principal;

import com.github.mizo432.xplanner.buisiness.model.Person;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Getters return mutable objects, acceptable for this use case")
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
