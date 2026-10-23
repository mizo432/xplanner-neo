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

import com.github.mizo432.xplanner.buisiness.model.TimeEntry;
import com.github.mizo432.xplanner.buisiness.model.Task;

import java.util.List;

/**
 * タイムエントリー（タイムシート）関連のコントローラー。
 */
@Controller
@RequestMapping("/timeentries")
public class TimeEntryController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listTimeEntries(@RequestParam(required = false) Integer taskId, @RequestParam(required = false) Integer personId, Model model) {
        if (taskId != null) {
            TypedQuery<TimeEntry> query = entityManager.createQuery(
                "SELECT te FROM TimeEntry te WHERE te.task.id = :taskId ORDER BY te.reportDate", TimeEntry.class);
            query.setParameter("taskId", taskId);
            model.addAttribute("timeEntries", query.getResultList());
            model.addAttribute("taskId", taskId);
        } else if (personId != null) {
            TypedQuery<TimeEntry> query = entityManager.createQuery(
                "SELECT te FROM TimeEntry te WHERE te.person1Id = :personId OR te.person2Id = :personId ORDER BY te.reportDate", TimeEntry.class);
            query.setParameter("personId", personId);
            model.addAttribute("timeEntries", query.getResultList());
            model.addAttribute("personId", personId);
        } else {
            model.addAttribute("timeEntries", new java.util.ArrayList<>());
        }
        return "view/timeentries";
    }

    @GetMapping("/{id}")
    public String viewTimeEntry(@PathVariable int id, Model model) {
        TimeEntry timeEntry = entityManager.find(TimeEntry.class, id);
        model.addAttribute("timeEntry", timeEntry);
        return "view/timeentry";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam(required = false) Integer taskId, Model model) {
        TimeEntry timeEntry = new TimeEntry();
        model.addAttribute("timeEntry", timeEntry);
        if (taskId != null) {
            Task task = entityManager.find(Task.class, taskId);
            timeEntry.setTask(task);
            model.addAttribute("taskId", taskId);
        }
        return "edit/editTimeEntry";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        TimeEntry timeEntry = entityManager.find(TimeEntry.class, id);
        model.addAttribute("timeEntry", timeEntry);
        return "edit/editTimeEntry";
    }

    @PostMapping
    public String saveTimeEntry(@ModelAttribute TimeEntry timeEntry) {
        if (timeEntry.getId() > 0) {
            entityManager.merge(timeEntry);
        } else {
            entityManager.persist(timeEntry);
        }
        return "redirect:/timeentries";
    }

    @PostMapping("/{id}/delete")
    public String deleteTimeEntry(@PathVariable int id) {
        TimeEntry timeEntry = entityManager.find(TimeEntry.class, id);
        if (timeEntry != null) {
            entityManager.remove(timeEntry);
        }
        return "redirect:/timeentries";
    }
}
