package com.github.mizo432.xplanner.present.web;

import java.util.List;

import com.github.mizo432.xplanner.buisiness.model.view.ProjectView;
import com.github.mizo432.xplanner.buisiness.model.view.UserStoryView;
import com.github.mizo432.xplanner.buisiness.service.ViewManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/view")
public class ViewService {
	private ViewManager viewManager;
	
	@Autowired
    public void setViewManager(ViewManager viewManager) {
		this.viewManager = viewManager;
	}

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectView> getProject(@PathVariable("projectId") Long projectId) {
        ProjectView project = viewManager.findProject(projectId);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/iteration/{iterationId}/userstories")
    public ResponseEntity<List<UserStoryView>> getUserStories(@PathVariable("iterationId") Long iterationId) {
        List<UserStoryView> userStories = viewManager.selectUserStories(iterationId);
        return ResponseEntity.ok(userStories);
    }
    
}
