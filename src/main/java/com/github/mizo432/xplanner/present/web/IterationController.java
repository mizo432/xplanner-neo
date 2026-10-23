package com.github.mizo432.xplanner.present.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.mizo432.xplanner.buisiness.model.Iteration;

import java.util.List;

/**
 * イテレーション関連のコントローラー。
 */
@Controller
@RequestMapping("/iterations")
public class IterationController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listIterations(@RequestParam(required = false) Integer projectId, Model model) {
        if (projectId != null) {
            TypedQuery<Iteration> query = entityManager.createQuery(
                "SELECT i FROM Iteration i WHERE i.project.id = :projectId ORDER BY i.startDate", Iteration.class);
            query.setParameter("projectId", projectId);
            model.addAttribute("iterations", query.getResultList());
            model.addAttribute("projectId", projectId);
        } else {
            model.addAttribute("iterations", new java.util.ArrayList<>());
        }
        return "view/iterations";
    }

    @GetMapping("/{id}")
    public String viewIteration(@PathVariable int id, Model model) {
        Iteration iteration = entityManager.find(Iteration.class, id);
        model.addAttribute("iteration", iteration);
        return "view/iteration";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Integer projectId, Model model) {
        Iteration iteration = new Iteration();
        model.addAttribute("iteration", iteration);
        model.addAttribute("projectId", projectId);
        return "edit/editIteration";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Iteration iteration = entityManager.find(Iteration.class, id);
        model.addAttribute("iteration", iteration);
        return "edit/editIteration";
    }

    @PostMapping
    public String saveIteration(@ModelAttribute Iteration iteration) {
        if (iteration.getId() > 0) {
            entityManager.merge(iteration);
        } else {
            entityManager.persist(iteration);
        }
        return "redirect:/iterations";
    }

    @PostMapping("/{id}/delete")
    public String deleteIteration(@PathVariable int id) {
        Iteration iteration = entityManager.find(Iteration.class, id);
        if (iteration != null) {
            entityManager.remove(iteration);
        }
        return "redirect:/iterations";
    }
}
