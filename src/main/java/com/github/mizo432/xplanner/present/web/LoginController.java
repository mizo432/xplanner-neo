package com.github.mizo432.xplanner.present.web;

import com.github.mizo432.xplanner.supporting.AuthenticationException;
import com.github.mizo432.xplanner.supporting.Authenticator;
import com.github.mizo432.xplanner.supporting.AuthenticatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン関連のコントローラー。
 */
@Controller
@RequestMapping("/login")
public class LoginController extends AbstractController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("error", false);
        return "security/login";
    }

    @PostMapping
    public String login(
            @RequestParam String userId,
            @RequestParam String password,
            HttpServletRequest request,
            HttpSession session,
            Model model) {
        try {
            Authenticator authenticator = new AuthenticatorImpl();
            authenticator.authenticate(request, userId, password);
            return "redirect:/projects";
        } catch (AuthenticationException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "ユーザーIDまたはパスワードが正しくありません");
            return "security/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        try {
            Authenticator authenticator = new AuthenticatorImpl();
            Integer principalId = (Integer) session.getAttribute("principalId");
            if (principalId != null) {
                authenticator.logout(request, principalId);
            }
        } catch (AuthenticationException e) {
            // ログアウト時のエラーは無視
        }
        session.invalidate();
        return "redirect:/login";
    }
}
