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

import com.github.mizo432.xplanner.buisiness.model.Role;

import java.util.List;

/**
 * ロール管理関連のコントローラー。
 */
@Controller
@RequestMapping("/roles")
public class RoleController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listRoles(Model model) {
        TypedQuery<Role> query = entityManager.createQuery(
            "SELECT r FROM Role r ORDER BY r.name", Role.class);
        model.addAttribute("roles", query.getResultList());
        return "view/roles";
    }

    @GetMapping("/{id}")
    public String viewRole(@PathVariable int id, Model model) {
        Role role = entityManager.find(Role.class, id);
        model.addAttribute("role", role);
        return "view/role";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "edit/editRole";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Role role = entityManager.find(Role.class, id);
        model.addAttribute("role", role);
        return "edit/editRole";
    }

    @PostMapping
    public String saveRole(@ModelAttribute Role role) {
        if (role.getId() > 0) {
            entityManager.merge(role);
        } else {
            entityManager.persist(role);
        }
        return "redirect:/roles";
    }

    @PostMapping("/{id}/delete")
    public String deleteRole(@PathVariable int id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
        }
        return "redirect:/roles";
    }
}
