package com.github.mizo432.xplanner.present.web;

import com.github.mizo432.xplanner.buisiness.service.Result;
import com.github.mizo432.xplanner.buisiness.service.UpdateManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/update")
public class UpdateService {
	private UpdateManager updateManager;
	
	@Autowired
	public void setUpdateManager(UpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	@PostMapping("/task/{taskId}/status")
    public ResponseEntity<Result> post(@PathVariable("taskId") int taskId, 
                                        @RequestParam("status") String status, 
                                        @RequestParam("originalEstimate") double originalEstimate) {
		Result result = updateManager.updateTaskStatus(taskId, status, originalEstimate);
		return ResponseEntity.ok(result);
    }
}
