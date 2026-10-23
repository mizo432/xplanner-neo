package com.github.mizo432.xplanner.present.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.service.ProjectService;

/**
 * プロジェクト関連のコントローラー。
 */
@Controller
@RequestMapping("/projects")
public class ProjectController extends AbstractController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.selectAllProjects(null));
        return "view/projects";
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "view/project";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("project", new Project());
        return "edit/editProject";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "edit/editProject";
    }

    @PostMapping
    public String saveProject(@ModelAttribute Project project) {
        projectService.save(project);
        return "redirect:/projects";
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project != null) {
            projectService.delete(project);
        }
        return "redirect:/projects";
    }

    @GetMapping("/list-fragment")
    public String listProjectsFragment(Model model) {
        model.addAttribute("projects", projectService.selectAllProjects(null));
        return "view/projects-fragment";
    }
}
