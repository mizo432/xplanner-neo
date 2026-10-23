package com.github.mizo432.xplanner.buisiness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.buisiness.model.view.ProjectView;
import com.github.mizo432.xplanner.buisiness.model.view.UserStoryView;
import com.github.mizo432.xplanner.buisiness.model.repository.ProjectRepository;
import com.github.mizo432.xplanner.buisiness.model.repository.UserStoryRepository;


@Component
public class ViewManager {
	private final ProjectRepository projectRepository;
	
	private final UserStoryRepository userStoryRepository;

	public ViewManager(ProjectRepository projectRepository, UserStoryRepository userStoryRepository) {
		this.projectRepository = projectRepository;
		this.userStoryRepository = userStoryRepository;
	}

	public ProjectView findProject(Long id) {
		return projectRepository.findById(id).map(project -> {
			ProjectView view = new ProjectView();
			view.setId(project.getId());
			view.setName(project.getName());
			return view;
		}).orElse(null);
	}

	public List<UserStoryView> selectUserStories(Long id) {
		return userStoryRepository.findByProjectId(id).stream().map(story -> {
			UserStoryView view = new UserStoryView();
			view.setId(story.getId());
			view.setName(story.getName());
			return view;
		}).toList();
	}

}
