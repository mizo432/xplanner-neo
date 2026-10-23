package com.github.mizo432.xplanner.buisiness.model;

import com.github.mizo432.xplanner.buisiness.model.DomainObject;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

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
