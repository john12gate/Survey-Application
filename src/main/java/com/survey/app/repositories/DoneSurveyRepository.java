package com.survey.app.repositories;

import com.survey.app.model.DoneSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoneSurveyRepository extends JpaRepository<DoneSurvey, UUID> {
    List<DoneSurvey> findDoneSurveysByCreatorName(String creatorName);
    List<DoneSurvey> findDoneSurveysByRespondentName(String respondentName);
}
