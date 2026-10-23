package com.github.mizo432.xplanner.present.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 基本的なSpring MVCコントローラーの基底クラス。
 */
@RequestMapping("/")
public abstract class AbstractController {

    protected void addModelAttribute(Model model, String name, Object value) {
        model.addAttribute(name, value);
    }

    protected String getViewName(String view) {
        return view;
    }

    protected int getParameterAsInt(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    protected String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }
}
