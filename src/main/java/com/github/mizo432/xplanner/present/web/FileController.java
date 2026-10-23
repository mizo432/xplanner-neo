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

import com.github.mizo432.xplanner.buisiness.model.File;
import com.github.mizo432.xplanner.buisiness.model.Directory;

import java.util.List;

/**
 * ファイル管理関連のコントローラー。
 */
@Controller
@RequestMapping("/files")
public class FileController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listFiles(@RequestParam(required = false) Integer directoryId, Model model) {
        if (directoryId != null) {
            TypedQuery<File> query = entityManager.createQuery(
                "SELECT f FROM File f WHERE f.directory.id = :directoryId ORDER BY f.name", File.class);
            query.setParameter("directoryId", directoryId);
            model.addAttribute("files", query.getResultList());
            model.addAttribute("directoryId", directoryId);
        } else {
            TypedQuery<File> query = entityManager.createQuery(
                "SELECT f FROM File f ORDER BY f.name", File.class);
            model.addAttribute("files", query.getResultList());
        }
        return "view/files";
    }

    @GetMapping("/{id}")
    public String viewFile(@PathVariable int id, Model model) {
        File file = entityManager.find(File.class, id);
        model.addAttribute("file", file);
        return "view/file";
    }

    @GetMapping("/download/{id}")
    public String downloadFile(@PathVariable int id, Model model) {
        File file = entityManager.find(File.class, id);
        model.addAttribute("file", file);
        return "redirect:/files/" + id;
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        File file = new File();
        model.addAttribute("file", file);
        return "edit/editFile";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        File file = entityManager.find(File.class, id);
        model.addAttribute("file", file);
        return "edit/editFile";
    }

    @PostMapping
    public String saveFile(@ModelAttribute File file) {
        if (file.getId() > 0) {
            entityManager.merge(file);
        } else {
            entityManager.persist(file);
        }
        return "redirect:/files";
    }

    @PostMapping("/{id}/delete")
    public String deleteFile(@PathVariable int id) {
        File file = entityManager.find(File.class, id);
        if (file != null) {
            entityManager.remove(file);
        }
        return "redirect:/files";
    }
}
