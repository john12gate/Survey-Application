package com.survey.app.repositories;

import com.survey.app.model.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends PagingAndSortingRepository<Question, Long> {
// Other Method goes here

}
