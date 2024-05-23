package com.survey.app.service;

import com.survey.app.exceptions.ResourceNotFoundException;
import com.survey.app.model.PossibleAnswer;
import com.survey.app.model.Question;
import com.survey.app.repositories.AnswerRepository;
import com.survey.app.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService1 {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Question createQuestion(Question question) {
        for(PossibleAnswer possibleAnswer : question.getPossibleAnswerSet()){
            if(answerRepository.findById(possibleAnswer.getId()).isPresent()) {
                answerRepository.findById(possibleAnswer.getId()).get().addQuestion(question);
            }
        }
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(long id, Question question) {
        Optional<Question> questionDB = this.questionRepository.findById(id);

        if(questionDB.isPresent()) {
            Question questionUpdate = questionDB.get();
            questionUpdate.setCustomAnswer(question.getCustomAnswer());
            questionUpdate.setQuestionText(question.getQuestionText());
            questionUpdate.setRequired(question.isRequired());
            questionUpdate.setPossibleAnswerSet(question.getPossibleAnswerSet());
            questionUpdate.setDifficulty(question.getDifficulty());
            questionUpdate.setCategory(question.getCategory());
            questionUpdate.setTitle(question.getTitle());
            questionRepository.save(questionUpdate);
            return questionUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id :" + question.getId());
        }
    }

    @Override
    public Question getQuestionById(long id) {
        Optional<Question> questionDB = this.questionRepository.findById(id);
        if(questionDB.isPresent()) {
            return questionDB.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id :" + id);
        }
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public boolean deleteQuestion(long id) {
        Optional<Question> questionDB = this.questionRepository.findById(id);
        if(questionDB.isPresent()) {
            this.questionRepository.delete(questionDB.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id :" + id);
        }
        return false;
    }

    @Override
    public List<Question> searchQuestionsByTitle(String title) {
        return questionRepository.findByTitleContaining(title);
    }

    @Override
    public List<Question> filterQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    @Override
    public Question getRandomQuestion() {
        return questionRepository.findRandomQuestion();
    }

    @Override
    public Long getQuestionsCount() {
        return questionRepository.count();
    }

    @Override
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }
}
