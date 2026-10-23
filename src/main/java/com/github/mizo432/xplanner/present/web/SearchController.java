package com.github.mizo432.xplanner.present.web;

import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.UserStory;
import com.github.mizo432.xplanner.buisiness.model.repository.IterationRepositoryJpa;
import com.github.mizo432.xplanner.buisiness.model.repository.PersonRepository;
import com.github.mizo432.xplanner.buisiness.model.repository.ProjectRepository;
import com.github.mizo432.xplanner.buisiness.model.repository.TaskRepository;
import com.github.mizo432.xplanner.buisiness.model.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 検索機能のコントローラー。
 */
@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IterationRepositoryJpa iterationRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String search(@RequestParam(required = false) String q, @RequestParam(required = false) Long projectId, Model model) {
        if (q == null || q.isEmpty()) {
            model.addAttribute("results", new ArrayList<>());
            model.addAttribute("query", "");
            return "view/search";
        }
        return searchResults(q, projectId, model);
    }

    @GetMapping("/results-fragment")
    public String searchFragment(@RequestParam String q, @RequestParam(required = false) Long projectId, Model model) {
        return searchResults(q, projectId, model);
    }

    private String searchResults(String q, Long projectId, Model model) {
        List<Object> results = new ArrayList<>();
        String pattern = "%" + q + "%";

        // プロジェクト検索
        List<Project> projects = projectRepository.findByNameContaining(pattern);
        if (projectId != null) {
            projects = projects.stream()
                    .filter(p -> p.getId().equals(projectId))
                    .toList();
        }
        results.addAll(projects);

        // イテレーション検索
        List<Iteration> iterations = iterationRepository.findByNameContaining(pattern);
        results.addAll(iterations);

        // ストーリー検索
        List<UserStory> stories = userStoryRepository.findByNameContaining(pattern);
        results.addAll(stories);

        // タスク検索
        List<Task> tasks = taskRepository.findByNameContaining(pattern);
        results.addAll(tasks);

        // 人物検索
        List<Person> people = personRepository.findByNameContaining(pattern);
        results.addAll(people);

        model.addAttribute("results", results);
        model.addAttribute("query", q);
        return "view/search-results-fragment";
    }
}
