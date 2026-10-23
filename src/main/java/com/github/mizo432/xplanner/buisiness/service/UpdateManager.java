package com.github.mizo432.xplanner.buisiness.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.enums.TaskStatus;
import com.github.mizo432.xplanner.buisiness.model.repository.TaskRepository;

@RequiredArgsConstructor
@Component
public class UpdateManager {
	private final TaskRepository taskRepository;

	@SuppressWarnings("boxing")
	public Result updateTaskStatus(int id, String status, double originalEstimate) {
		Task task = taskRepository.findById((long)id).orElse(null);
		if(task == null) {
			return new Result(true, 404, "Task not found");
		}
		TaskStatus taskStatus = TaskStatus.fromName(status);
		if(task.getNewStatus().equals(taskStatus)) {
			return new Result(true, 401, "Task status not changed");
		}
		if(taskStatus.equals(TaskStatus.NON_STARTED)) {
			return new Result(true, 402, "Moving Task to not started not implemented");
		}
		if(taskStatus.equals(TaskStatus.STARTED)) {
			task.setOriginalEstimate(originalEstimate);
			task.setCompleted(false);
		}else if(taskStatus.equals(TaskStatus.COMPLETED)) {
			task.setCompleted(true);
		}
		taskRepository.save(task);
		return new Result(false, 0, "Task updated");
		
	}
}
