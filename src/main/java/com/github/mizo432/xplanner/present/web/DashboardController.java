package com.github.mizo432.xplanner.present.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ダッシュボード関連のコントローラー。
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractController {

    @GetMapping
    public String dashboard(Model model) {
        return "view/dashboard";
    }
}
