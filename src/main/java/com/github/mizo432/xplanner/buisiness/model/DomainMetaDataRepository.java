package com.github.mizo432.xplanner.buisiness.model;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.mizo432.xplanner.buisiness.model.DomainObject;
import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Note;
import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.TimeEntry;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public class DomainMetaDataRepository {
   private static final Logger log = LoggerFactory.getLogger(DomainMetaDataRepository.class);

   private final Map<Class<?>, DomainClass> metadataByClass = new HashMap<>();
   private final Map<String, DomainClass> metadataByTypeName = new HashMap<>();

   @Autowired
   private EntityManager entityManager;

   //DEBT Start using these constant everywhere especially in the web tier (all links)
   public static final String PROJECT_TYPE_NAME = "project";
   public static final String ITERATION_TYPE_NAME = "iteration";
   public static final String STORY_TYPE_NAME = "userstory";
   public static final String TASK_TYPE_NAME = "task";
   public static final String FEATURE_TYPE_NAME = "feature";
   public static final String TIME_ENTRY_TYPE_NAME = "timeentry";
   public static final String INTEGRATION_TYPE_NAME = "integration";
   public static final String PERSON_TYPE_NAME = "person";
   public static final String NOTE_TYPE_NAME = "note";

   private static DomainMetaDataRepository instance = createInstance();

   public DomainMetaDataRepository() {}

   private static DomainMetaDataRepository createInstance() {
      DomainMetaDataRepository rep = new DomainMetaDataRepository();
      rep.init();
      return rep;
   }

   public void init() {
      DomainClass domainClass;

      //DEBT(DATADRIVEN) Why do we need to specify the /do/ in all entities except iteration?
      //DEBT(DATADRIVEN) Should have an acceptance tests to check all the links values accross all entities
      //DEBT(DATADRIVEN) Remove duplication with edit/delete actions
      //DEBT(DATADRIVEN) Remove duplication within the same action mapping

      domainClass = new DomainClass(PROJECT_TYPE_NAME, Project.class);
      domainClass.addMapping(new ActionMapping("edit",           "action.edit.project",           "admin.edit",        "/images/edit.png",           "/do/edit/project",               "project", false));
      domainClass.addMapping(new ActionMapping("delete",         "action.delete.project",         "sysadmin.delete",   "/images/delete.png",         "/do/delete/project",             "project", true, true, "action.delete.confirmation"));
      add(domainClass);

      domainClass = new DomainClass(ITERATION_TYPE_NAME, Iteration.class, "project", Project.class, "iterations");
      domainClass.addMapping(new ActionMapping("edit",           "action.edit.iteration",         "edit",              "/images/edit.png",           "edit/iteration",                 "iteration", false));
      domainClass.addMapping(new ActionMapping("delete",         "action.delete.iteration",       "delete",            "/images/delete.png",         "delete/iteration",               "iteration", true, true, "action.delete.confirmation"));
      domainClass.addMapping(new ActionMapping("createStory",    "iteration.link.create_story",   "edit",              null,                         "edit/userstory",                 "iteration", false, false, null));
      domainClass.addMapping(new ActionMapping("start",          "action.start.iteration",      "edit",                  null,                         "start/iteration",                "iteration", false) {
         @Override
			public boolean isVisible(Nameable object) { return !((Iteration) object).isActive(); }
      });
      domainClass.addMapping(new ActionMapping("close",          "action.close.iteration",      "edit",             null,                               "close/iteration",               "iteration", false) {
         @Override
			public boolean isVisible(Nameable object) { return ((Iteration) object).isActive(); }
      });
      domainClass.addMapping(new ActionMapping("importStories",  "action.import.stories","edit",      null,                         "import/stories",                 "iteration", false));

      add(domainClass);

      domainClass = new DomainClass(STORY_TYPE_NAME, UserStory.class, "iteration", Iteration.class, "userStories");
      domainClass.addMapping(new ActionMapping("edit",           "action.edit.story",             "edit",              "/images/edit.png",           "/do/edit/userstory",             "story", false));
      domainClass.addMapping(new ActionMapping("delete",         "action.delete.story",           "delete",            "/images/delete.png",         "/do/delete/userstory",           "story", true, true, "action.delete.confirmation"));
      domainClass.addMapping(new ActionMapping("movecontinue",   "action.movecontinue.story",     "edit",              "/images/movecontinue.png",   "/do/move/continue/userstory",    "story", true));
      add(domainClass);

      domainClass = new DomainClass(TASK_TYPE_NAME, Task.class, "userStory", UserStory.class, "tasks");
      domainClass.addMapping(new ActionMapping("edit",           "action.edit.task",              "edit",              "/images/edit.png",           "/do/edit/task",                  "task", false));
      domainClass.addMapping(new ActionMapping("delete",         "action.delete.task",            "delete",            "/images/delete.png",         "/do/delete/task",                "task", true, true, "action.delete.confirmation"));
      domainClass.addMapping(new ActionMapping("movecontinue",   "action.movecontinue.task",      "edit",              "/images/movecontinue.png",   "/do/move/continue/task",         "task", true));
      domainClass.addMapping(new ActionMapping("edittime",       "action.edittime.task",          "edit",              "/images/clock2.png",         "/do/edit/time",                  "task", false));
      add(domainClass);

      add(new DomainClass(TIME_ENTRY_TYPE_NAME, TimeEntry.class, "task.id", Task.class, "timeEntries"));

      add(new DomainClass(INTEGRATION_TYPE_NAME, Integration.class));

      domainClass = new DomainClass(PERSON_TYPE_NAME, Person.class);
      domainClass.addMapping(new ActionMapping("edit",           "action.edit.person",            "edit",              "/images/edit.png",           "/do/edit/person",                "person", false));
      add(domainClass);

      add(new DomainClass(NOTE_TYPE_NAME, Note.class, "attachedToId", DomainObject.class, null));
   }

   public void add(DomainClass domainClass) {
      metadataByClass.put(domainClass.getJavaClass(), domainClass);
      metadataByTypeName.put(domainClass.getTypeName(), domainClass);
   }

   //DEBT(SPRING) Finish injecting fully this singleton
   public static DomainMetaDataRepository getInstance() {
      return instance;
   }

   public String classToTypeName(Class objectClass) {
      return getMetaData(objectClass).getTypeName();
   }

   @SuppressWarnings("unchecked")
   public DomainObject getParent(DomainObject child) throws HibernateException {
      DomainClass metaData = getMetaData(child.getClass());
      Long parentId = getId(child, metaData.getParentProperty());
      return (DomainObject) entityManager.find(metaData.getParentClass(), parentId);
   }

   @SuppressWarnings("unchecked")
   public void setParent(DomainObject child, DomainObject parent) {
      DomainClass metaData = getMetaData(child.getClass());
      setObjectOrId(child, metaData.getParentProperty(), parent);
      Collection<DomainObject> children = (Collection<DomainObject>)getProperty(parent, metaData.getChildrenProperty());
      children.add(child);
   }

   @SuppressWarnings("unchecked")
   public DomainObject getObject(String type, Long id) throws HibernateException {
      Class<?> targetClass = getMetaData(type).getJavaClass();
      return (DomainObject) entityManager.find(targetClass, id);
   }

   public Long getParentId(Identifiable object) {
      return getId(object, getMetaData(object.getClass()).getParentProperty());
   }

  public Map<String, DomainClass> getMetadataByTypeName() {
    return metadataByTypeName;
  }

	public DomainClass getMetaData(Class<?> objectClass) {
		DomainClass dom = metadataByClass.get(objectClass);
		if (dom == null) {
			String className = objectClass.getName();
			for (Class<?> clazz : metadataByClass.keySet()) {
				if (className.startsWith(clazz.getName())) {
					return metadataByClass.get(clazz);
				}
			}
		}
		return dom;
	}

   public DomainClass getMetaData(String type) {
      return metadataByTypeName.get(type);
   }

   private static Long getId(Identifiable object, String property) {
      if (property == null) return 0L;
      Object value = getProperty(object, property);
      if (value == null) return 0L;
      if (value instanceof Identifiable ) {
         return ((Identifiable)value).getId();
      } else {
         return ((Number) value).longValue();
      }
   }

   private static Object getProperty(Identifiable object, String property) {
      Object prop = null;
      try {
         prop = PropertyUtils.getProperty(object, property);
      } catch (Exception e) {
         log.error("configuration error with " + (object!=null?object.getClass().toString():"null") + "." + property, e);
      }
      return prop;
   }

   private static void setObjectOrId(DomainObject object, String property, DomainObject value) {
      if (property != null) {
         try {
            PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(object, property);
            Class type = descriptor.getPropertyType();
            Object target;
            if (DomainObject.class.isAssignableFrom(type)) {
               target = value;
            } else {
               target = value.getId();
            }
            PropertyUtils.setProperty(object, property, target);
         } catch (Exception e) {
            log.error("configuration error with " + (object!=null?object.getClass().toString():"null") + "." + property, e);
         }
      }
   }
}
