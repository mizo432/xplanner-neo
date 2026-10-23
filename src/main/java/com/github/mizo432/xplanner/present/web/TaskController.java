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

import com.github.mizo432.xplanner.buisiness.model.Task;

import java.util.List;

/**
 * タスク関連のコントローラー。
 */
@Controller
@RequestMapping("/tasks")
public class TaskController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listTasks(@RequestParam(required = false) Integer iterationId, @RequestParam(required = false) Integer personId, Model model) {
        if (iterationId != null) {
            TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.iteration.id = :iterationId ORDER BY t.name", Task.class);
            query.setParameter("iterationId", iterationId);
            model.addAttribute("tasks", query.getResultList());
            model.addAttribute("iterationId", iterationId);
        } else if (personId != null) {
            TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.assignedTo.id = :personId ORDER BY t.name", Task.class);
            query.setParameter("personId", personId);
            model.addAttribute("tasks", query.getResultList());
            model.addAttribute("personId", personId);
        } else {
            model.addAttribute("tasks", new java.util.ArrayList<>());
        }
        return "view/tasks";
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        Task task = entityManager.find(Task.class, id);
        model.addAttribute("task", task);
        return "view/task";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Integer iterationId, Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("iterationId", iterationId);
        return "edit/editTask";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Task task = entityManager.find(Task.class, id);
        model.addAttribute("task", task);
        return "edit/editTask";
    }

    @PostMapping
    public String saveTask(@ModelAttribute Task task) {
        if (task.getId() > 0) {
            entityManager.merge(task);
        } else {
            entityManager.persist(task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable int id) {
        Task task = entityManager.find(Task.class, id);
        if (task != null) {
            entityManager.remove(task);
        }
        return "redirect:/tasks";
    }
}
