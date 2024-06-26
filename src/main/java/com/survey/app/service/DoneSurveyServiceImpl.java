package com.survey.app.service;

import com.survey.app.exceptions.ResourceNotFoundException;
import com.survey.app.model.DoneSurvey;
import com.survey.app.repositories.DoneSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DoneSurveyServiceImpl implements DoneSurveyService {

    @Autowired
    private DoneSurveyRepository doneSurveyRepository;

    @Override
    public DoneSurvey createDoneSurvey(DoneSurvey doneSurvey) {
        doneSurvey.setRespondentName(getCurrentUserName());
        return doneSurveyRepository.save(doneSurvey);
    }

    @Override
    public DoneSurvey getDoneSurveyById(UUID id) {
        Optional<DoneSurvey> doneSurveyDB = doneSurveyRepository.findById(id);

        if (doneSurveyDB.isPresent()) {
            return doneSurveyDB.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + id);
        }
    }

    @Override
    public List<DoneSurvey> getAllDoneSurvey() {
        String userName = getCurrentUserName();
        if (isCoordinator())
            return doneSurveyRepository.findDoneSurveysByCreatorName(userName);
        else
            if(isRespondent())
                return doneSurveyRepository.findDoneSurveysByRespondentName(userName);
        throw new UsernameNotFoundException("There is a problem with your account!");
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

    private boolean isRespondent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RESPONDENT"));
    }
}
