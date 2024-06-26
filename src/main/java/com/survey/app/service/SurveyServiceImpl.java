package com.survey.app.service;

import com.survey.app.exceptions.ResourceNotFoundException;
import com.survey.app.model.Question;
import com.survey.app.model.Survey;
import com.survey.app.repositories.QuestionRepository;
import com.survey.app.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Survey createSurvey(Survey survey) {
        survey.setCreatorName(getCurrentUserName());

        for(Question q : survey.getQuestionList()){
           if(questionRepository.findById(q.getId()).isPresent())
               questionRepository.findById(q.getId()).get().setSurvey(survey);
        }
        return surveyRepository.save(survey);
    }

    @Override
    public Survey updateSurvey(UUID id, Survey survey) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            Survey surveyToUpdate = surveyDB.get();
            surveyToUpdate.setOpen(survey.isOpen());
            surveyToUpdate.setTitle(survey.getTitle());
            surveyToUpdate.setQuestionList(survey.getQuestionList());
            surveyRepository.save(surveyToUpdate);
            return surveyToUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + survey.getId());
        }
    }

    @Override
    public Survey getSurveyById(UUID id) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            return surveyDB.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + id);
        }
    }

    @Override
    public List<Survey> getAllSurvey() {
        if (isCoordinator())
            return surveyRepository.findByCreatorName(getCurrentUserName());
        else
            return surveyRepository.findAllByOpenIsTrue();
    }

    @Override
    public void deleteSurvey(UUID id) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            this.surveyRepository.delete(surveyDB.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + id);
        }
    }

    private String getCurrentUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    private boolean isCoordinator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"));
    }
}
