package com.github.mizo432.xplanner.present.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.mizo432.xplanner.buisiness.model.Setting;

import java.util.List;

/**
 * 設定管理関連のコントローラー。
 */
@Controller
@RequestMapping("/settings")
public class SettingController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String listSettings(Model model) {
        TypedQuery<Setting> query = entityManager.createQuery(
            "SELECT s FROM Setting s ORDER BY s.name", Setting.class);
        model.addAttribute("settings", query.getResultList());
        return "view/settings";
    }

    @PostMapping
    public String saveSetting(@ModelAttribute Setting setting) {
        if (setting.getId() > 0) {
            entityManager.merge(setting);
        } else {
            entityManager.persist(setting);
        }
        return "redirect:/settings";
    }
}
