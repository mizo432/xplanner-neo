package com.github.mizo432.xplanner.buisiness.model;

import java.util.Comparator;

import com.github.mizo432.xplanner.buisiness.model.Person;

public class NotificationReceiversComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        return ((Person)o1).getName().compareTo(((Person)o2).getName());
    }
}
