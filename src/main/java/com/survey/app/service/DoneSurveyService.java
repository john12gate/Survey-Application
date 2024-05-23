package com.survey.app.service;

import com.survey.app.model.DoneSurvey;

import java.util.List;
import java.util.UUID;

public interface DoneSurveyService {

    DoneSurvey createDoneSurvey(DoneSurvey doneSurvey);
    DoneSurvey getDoneSurveyById(UUID id);
    List<DoneSurvey> getAllDoneSurvey();

}
