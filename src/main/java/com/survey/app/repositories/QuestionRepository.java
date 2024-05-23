package com.survey.app.repositories;

import com.survey.app.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTitleContaining(String title);

    List<Question> findByDifficulty(String difficulty);

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();


    long count();

    List<Question> findByCategory(String category);

    Page<Question> findAll(Pageable pageable);
}
