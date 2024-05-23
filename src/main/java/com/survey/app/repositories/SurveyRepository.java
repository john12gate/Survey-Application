package com.survey.app.repositories;

import com.survey.app.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SurveyRepository extends JpaRepository<Survey, UUID> {
    List<Survey> findByCreatorName(String creatorName);
    List<Survey> findAllByOpenIsTrue();
}
