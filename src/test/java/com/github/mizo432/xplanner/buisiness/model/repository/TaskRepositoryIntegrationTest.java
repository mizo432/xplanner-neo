package com.github.mizo432.xplanner.buisiness.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.github.mizo432.xplanner.TestCacheConfiguration;
import com.github.mizo432.xplanner.TestcontainersConfiguration;
import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

@Import({TestcontainersConfiguration.class, TestCacheConfiguration.class})
@DataJpaTest
class TaskRepositoryIntegrationTest {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private IterationRepositoryJpa iterationRepository;

	@Autowired
	private UserStoryRepository userStoryRepository;

	@DisplayName("タスクを保存してIDで取得できる")
	@Test
	void shouldSaveAndFindTaskById() {
		Task task = new Task();
		task.setName("Test Task");
		task.setDescription("A test task");
		task.setEstimatedHours(8.0);
		task.setAcceptorId(1);

		Task saved = taskRepository.save(task);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo("Test Task");
		assertThat(saved.getEstimatedHours()).isEqualTo(8.0);

		Task found = taskRepository.findById(saved.getId()).orElseThrow();
		assertThat(found.getName()).isEqualTo("Test Task");
	}

	@DisplayName("タスク名で検索できる")
	@Test
	void shouldFindTaskByNameContaining() {
		Task task1 = new Task();
		task1.setName("Implement Login");
		task1.setEstimatedHours(4.0);
		taskRepository.save(task1);

		Task task2 = new Task();
		task2.setName("Implement Logout");
		task2.setEstimatedHours(2.0);
		taskRepository.save(task2);

		List<Task> found = taskRepository.findByNameContaining("Login");

		assertThat(found).hasSize(1);
		assertThat(found.get(0).getName()).isEqualTo("Implement Login");
	}

	@DisplayName("現在のイテレーションかつ担当者IDに一致するタスクを取得できる")
	@Test
	void shouldFindCurrentTasksForPerson() {
		// Projectを作成
		Project project = new Project();
		project.setName("Test Project");
		project.setHidden(false);
		projectRepository.save(project);

		// Iterationを作成
		Iteration iteration = new Iteration();
		iteration.setName("Sprint 1");
		iteration.setStartDate(new Date());
		iteration.setEndDate(new Date(System.currentTimeMillis() + 86400000L)); // 1日後
		iteration.setProject(project);
		iterationRepository.save(iteration);

		// UserStoryを作成
		UserStory userStory = new UserStory();
		userStory.setName("Test Story");
		userStory.setIteration(iteration);
		userStoryRepository.save(userStory);

		// Taskを作成
		Task task = new Task();
		task.setName("Assigned Task");
		task.setAcceptorId(100);
		task.setEstimatedHours(4.0);
		task.setUserStory(userStory);
		taskRepository.save(task);

		List<Task> currentTasks = taskRepository.findCurrentTasksForPerson(new Date(), 100L);

		assertThat(currentTasks).hasSize(1);
		assertThat(currentTasks.get(0).getName()).isEqualTo("Assigned Task");
		assertThat(currentTasks.get(0).getAcceptorId()).isEqualTo(100);
	}

	@DisplayName("タスクを削除できる")
	@Test
	void shouldDeleteTask() {
		Task task = new Task();
		task.setName("To Delete");
		task.setEstimatedHours(2.0);
		Task saved = taskRepository.save(task);
		Long id = saved.getId();

		taskRepository.delete(saved);

		assertThat(taskRepository.findById(id)).isEmpty();
	}
}
