package com.github.mizo432.xplanner.buisiness.model;

import com.github.mizo432.xplanner.buisiness.model.DomainObject;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = {"EQ_DOESNT_OVERRIDE_EQUALS", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "DomainObject implements equals based on ID, sufficient for JPA entities; getters return mutable objects, acceptable for JPA")
public class Feature extends DomainObject {
    private String name;
    private String description;
    private UserStory story;

    public UserStory getUserStory() {
        return story;
    }

    // TODO: add management of the inverse relationship tasks
    public void setStory(UserStory story) {
        this.story = story;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
