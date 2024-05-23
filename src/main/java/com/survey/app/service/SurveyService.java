package com.survey.app.service;

import com.survey.app.model.Survey;

import java.util.List;
import java.util.UUID;

public interface SurveyService {

        Survey createSurvey(Survey survey);
        Survey updateSurvey(UUID id, Survey survey);
        Survey getSurveyById(UUID id);
        List<Survey> getAllSurvey();
        void deleteSurvey(UUID id);

}
