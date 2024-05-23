package com.survey.app.repositories;

import com.survey.app.model.PossibleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository <PossibleAnswer, Long> {

}
