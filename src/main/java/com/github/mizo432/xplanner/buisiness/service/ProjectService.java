package com.github.mizo432.xplanner.buisiness.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.repository.ProjectRepository;

/**
 * プロジェクト関連のビジネスロジックを扱うサービス。
 */
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> selectAllProjects(Person user) {
        return projectRepository.findByHiddenFalse();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Long save(Project project) {
        Project saved = projectRepository.save(project);
        return saved.getId();
    }

    public void delete(Project project) {
        projectRepository.deleteById(project.getId());
    }
}
