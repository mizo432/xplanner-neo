/*
 * Created by IntelliJ IDEA.
 * User: Jacques
 * Date: May 28, 2005
 * Time: 5:49:35 PM
 */
package com.github.mizo432.xplanner.buisiness.model;

import java.util.HashMap;
import java.util.Map;

import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

public class DomainObjectWikiLinkFormatter {
   String fromText;
   String toText;

   Map<Class<? extends DomainObject>, String> schemeByClass = new HashMap<>();

   public DomainObjectWikiLinkFormatter() {
      initSchemeByClassMap();
   }

   @SuppressWarnings("unchecked")
   private void initSchemeByClassMap() {
      schemeByClass.put((Class<? extends DomainObject>) (Class<?>) Project.class, "project");
      schemeByClass.put((Class<? extends DomainObject>) (Class<?>) Iteration.class, "iteration");
//      schemeByClass.put(Feature.class, "feature");
      schemeByClass.put((Class<? extends DomainObject>) (Class<?>) UserStory.class, "story");
      schemeByClass.put((Class<? extends DomainObject>) (Class<?>) Task.class, "task");
   }

   public String format(DomainObject object) {
      String link = "";
      if (object != null) {
         String scheme = schemeByClass.get(object.getClass());
         if (scheme != null) {
            link = scheme + ":" + object.getId();
         }
      }
      return link;
   }
}