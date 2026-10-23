package com.github.mizo432.xplanner.present.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.mizo432.xplanner.buisiness.model.UserStory;
import com.github.mizo432.xplanner.buisiness.service.UserStoryService;

/**
 * ユーザーストーリー関連のコントローラー。
 */
@Controller
@RequestMapping("/stories")
public class UserStoryController extends AbstractController {

    @Autowired
    private UserStoryService userStoryService;

    @GetMapping
    public String listStories(@RequestParam Long iterationId, Model model) {
        model.addAttribute("stories", userStoryService.selectAllUserStories(iterationId));
        model.addAttribute("iterationId", iterationId);
        return "view/stories";
    }

    @GetMapping("/list-fragment")
    public String listStoriesFragment(@RequestParam Long iterationId, Model model) {
        model.addAttribute("stories", userStoryService.selectAllUserStories(iterationId));
        model.addAttribute("iterationId", iterationId);
        return "view/stories-fragment";
    }

    @GetMapping("/{id}")
    public String viewStory(@PathVariable Long id, Model model) {
        UserStory story = userStoryService.findById(id);
        model.addAttribute("story", story);
        return "view/story";
    }

    @GetMapping("/edit")
    public String showNewForm(@RequestParam Long iterationId, Model model) {
        UserStory story = new UserStory();
        model.addAttribute("story", story);
        model.addAttribute("iterationId", iterationId);
        return "edit/editStory";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserStory story = userStoryService.findById(id);
        model.addAttribute("story", story);
        return "edit/editStory";
    }

    @PostMapping
    public String saveStory(@ModelAttribute UserStory story) {
        userStoryService.save(story);
        return "redirect:/stories";
    }

    @PostMapping("/{id}/delete")
    public String deleteStory(@PathVariable Long id) {
        UserStory story = userStoryService.findById(id);
        if (story != null) {
            userStoryService.delete(story);
        }
        return "redirect:/stories";
    }
}
