package com.github.mizo432.xplanner.buisiness.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.github.mizo432.xplanner.TestCacheConfiguration;
import com.github.mizo432.xplanner.TestcontainersConfiguration;
import com.github.mizo432.xplanner.buisiness.model.Project;

@Import({TestcontainersConfiguration.class, TestCacheConfiguration.class})
@DataJpaTest
class ProjectRepositoryIntegrationTest {

	@Autowired
	private ProjectRepository projectRepository;

	@DisplayName("プロジェクトを保存してIDで取得できる")
	@Test
	void shouldSaveAndFindProjectById() {
		Project project = new Project();
		project.setName("Test Project");
		project.setDescription("A test project");
		project.setHidden(false);

		Project saved = projectRepository.save(project);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo("Test Project");

		Project found = projectRepository.findById(saved.getId()).orElseThrow();
		assertThat(found.getName()).isEqualTo("Test Project");
		assertThat(found.getDescription()).isEqualTo("A test project");
		assertThat(found.isHidden()).isFalse();
	}

	@DisplayName("非公開フラグがfalseのプロジェクトのみを取得できる")
	@Test
	void shouldFindProjectsByHiddenFalse() {
		Project visible = new Project();
		visible.setName("Visible Project");
		visible.setHidden(false);
		projectRepository.save(visible);

		Project hidden = new Project();
		hidden.setName("Hidden Project");
		hidden.setHidden(true);
		projectRepository.save(hidden);

		List<Project> visibleProjects = projectRepository.findByHiddenFalse();

		assertThat(visibleProjects).hasSize(1);
		assertThat(visibleProjects.get(0).getName()).isEqualTo("Visible Project");
	}

	@DisplayName("名前でプロジェクトを検索できる")
	@Test
	void shouldFindProjectByNameContaining() {
		Project project1 = new Project();
		project1.setName("Project Alpha");
		project1.setHidden(false);
		projectRepository.save(project1);

		Project project2 = new Project();
		project2.setName("Beta Project");
		project2.setHidden(false);
		projectRepository.save(project2);

		List<Project> found = projectRepository.findByNameContaining("Alpha");

		assertThat(found).hasSize(1);
		assertThat(found.get(0).getName()).isEqualTo("Project Alpha");
	}

	@DisplayName("プロジェクトを削除できる")
	@Test
	void shouldDeleteProject() {
		Project project = new Project();
		project.setName("To Delete");
		project.setHidden(false);
		Project saved = projectRepository.save(project);
		Long id = saved.getId();

		projectRepository.delete(saved);

		assertThat(projectRepository.findById(id)).isEmpty();
	}
}
