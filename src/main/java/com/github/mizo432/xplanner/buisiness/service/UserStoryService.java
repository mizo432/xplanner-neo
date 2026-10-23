package com.github.mizo432.xplanner.buisiness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mizo432.xplanner.buisiness.model.UserStory;
import com.github.mizo432.xplanner.buisiness.model.repository.UserStoryRepository;

/**
 * ユーザーストーリー関連のビジネスロジックを扱うサービス。
 */
@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;

    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public List<UserStory> selectAllUserStories(Long iterationId) {
        return userStoryRepository.findByIteration(iterationId);
    }

    public UserStory findById(Long id) {
        return userStoryRepository.findById(id).orElse(null);
    }

    public Long save(UserStory story) {
        UserStory saved = userStoryRepository.save(story);
        return saved.getId();
    }

    public void delete(UserStory story) {
        userStoryRepository.delete(story);
    }

    public List<UserStory> selectStoriesForPersonWhereCustomer(Long personId) {
        return userStoryRepository.findByCustomerId(personId);
    }

    public List<UserStory> selectStoriesForPersonWhereTracker(Long personId) {
        return userStoryRepository.findByTrackerId(personId);
    }
}
