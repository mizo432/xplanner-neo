package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.UserStory;
import com.github.mizo432.xplanner.supporting.AuthenticationException;
import com.github.mizo432.xplanner.supporting.auth.Authorizer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

//TODO Should be turn into a Query object
//TODO Create a spring context including IterationRepository and others for every web session (logged in user)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Getters return mutable objects, acceptable for this use case")
public class IterationRepository {
    public static final String EDITABLE_ITERATIONS_QUERY_STRING =
            "select i from " + Iteration.class.getName() + " i, " + Project.class.getName() + " p " +
            "where p.hidden = false and i.projectId = p.id order by p.name, i.startDate";
    private final EntityManager entityManager;
    private final Long loggedInUser;
    private final Authorizer authorizer;

    public IterationRepository(EntityManager entityManager, Authorizer authorizer, Long loggedInUser) {
        this.entityManager = entityManager;
        this.loggedInUser = loggedInUser;
        this.authorizer = authorizer;
    }

    private boolean canUserEditIteration(Iteration iteration) throws AuthenticationException {
        return authorizer.hasPermission(iteration.getProject().getId(), loggedInUser, iteration, "edit");
    }

    public List<Iteration> fetchEditableIterations() throws AuthenticationException {
        List<Iteration> allIterations = entityManager.createNamedQuery("com.github.mizo432.xplanner.buisiness.model.GetEditableIterationQuery", Iteration.class).getResultList();
        List<Iteration> acceptedIterations = new ArrayList<>(allIterations.size());
        for (Iteration it : allIterations) {
            if (canUserEditIteration(it)) {
                acceptedIterations.add(it);
            }
        }
        return acceptedIterations;
    }

    public List<Iteration> fetchEditableIterations(Long projectId, Date startingAfter) throws AuthenticationException {
        List<Iteration> allEditableIterations = fetchEditableIterations();
        List<Iteration> acceptedIterations = new ArrayList<>(allEditableIterations.size());
        for (Iteration it : allEditableIterations) {
            if (it.getProject().getId().equals(projectId) && it.getStartDate().after(startingAfter))
                acceptedIterations.add(it);
        }
        return acceptedIterations;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Iteration getIterationForStory(UserStory story) {
        return story.getIteration();
    }

    public Iteration getIteration(Long iterationId) {
        return entityManager.getReference(Iteration.class, iterationId);
    }

   public Iteration getCurrentIteration(Long projectId) {
       java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
       try {
          Query getCurrentIterationQuery = entityManager.createNamedQuery("com.github.mizo432.xplanner.buisiness.model.GetCurrentIterationQuery");
          getCurrentIterationQuery.setParameter(1, projectId);
          getCurrentIterationQuery.setParameter(2, now);
          List<Iteration> results = getCurrentIterationQuery.getResultList();
          return (results.size() > 0) ? results.get(0) : null;
       } catch (Exception e) {
           return null;
       }
   }
}