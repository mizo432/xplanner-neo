package com.github.mizo432.xplanner.buisiness.model;

//DEBT Should include setters
//DEBT Should det
public interface Nameable extends Identifiable {
    String getName();
    String getDescription();
    String getAttribute(String attributeName);
}
