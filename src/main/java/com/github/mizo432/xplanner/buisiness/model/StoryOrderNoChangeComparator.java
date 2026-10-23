/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */
package com.github.mizo432.xplanner.buisiness.model;

import java.util.Comparator;

import com.github.mizo432.xplanner.buisiness.model.UserStory;

/**
 * Created by IntelliJ IDEA.
 * User: sg310782
 * Date: Mar 13, 2006
 * Time: 2:51:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoryOrderNoChangeComparator implements Comparator<UserStory> {

   @Override
   public int compare(UserStory userStory1, UserStory userStory2) {
      int compare = Integer.compare(userStory1.getOrderNo(), userStory2.getOrderNo());
      if (compare == 0) {
         compare = -Integer.compare(userStory1.getPreviousOrderNo(),
               userStory2.getPreviousOrderNo());
      }
      return compare;
   }
}
