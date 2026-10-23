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

import com.github.mizo432.xplanner.buisiness.model.Note;

import java.util.List;

/**
 * ノート関連のコントローラー。
 */
@Controller
@RequestMapping("/notes")
public class NoteController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listNotes(@RequestParam Integer attachedToId, Model model) {
        if (attachedToId != null) {
            TypedQuery<Note> query = entityManager.createQuery(
                "SELECT n FROM Note n WHERE n.attachedToId = :attachedToId ORDER BY n.submissionTime", Note.class);
            query.setParameter("attachedToId", attachedToId);
            model.addAttribute("notes", query.getResultList());
            model.addAttribute("attachedToId", attachedToId);
        } else {
            model.addAttribute("notes", new java.util.ArrayList<>());
        }
        return "view/notes";
    }

    @GetMapping("/{id}")
    public String viewNote(@PathVariable int id, Model model) {
        Note note = entityManager.find(Note.class, id);
        model.addAttribute("note", note);
        return "view/note";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Integer attachedToId, Model model) {
        Note note = new Note();
        note.setAttachedToId(attachedToId);
        model.addAttribute("note", note);
        model.addAttribute("attachedToId", attachedToId);
        return "edit/editNote";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Note note = entityManager.find(Note.class, id);
        model.addAttribute("note", note);
        return "edit/editNote";
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        if (note.getId() > 0) {
            entityManager.merge(note);
        } else {
            entityManager.persist(note);
        }
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable int id) {
        Note note = entityManager.find(Note.class, id);
        if (note != null) {
            entityManager.remove(note);
        }
        return "redirect:/notes";
    }
}
