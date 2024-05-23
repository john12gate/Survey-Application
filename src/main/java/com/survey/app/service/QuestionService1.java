package com.survey.app.service;

import com.survey.app.model.Question;

import java.util.List;

public interface QuestionService1 {

   Question createQuestion(Question question);

   Question updateQuestion(long id, Question question);

   Question getQuestionById(long id);

   List<Question> getAllQuestion();

   boolean deleteQuestion(long id);

   List<Question> searchQuestionsByTitle(String title);

   List<Question> filterQuestionsByDifficulty(String difficulty);

   Question getRandomQuestion();

   Long getQuestionsCount();

   List<Question> getQuestionsByCategory(String category);
}
