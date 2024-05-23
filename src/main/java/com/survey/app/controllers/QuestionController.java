package com.survey.app.controllers;

import com.survey.app.model.Question;
import com.survey.app.repositories.QuestionRepo;
import com.survey.app.service.QuestionService1;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing questions.
 */
@Api(tags = "Question Management")
@RequestMapping("api/v1/question")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService1 questionService;

    @Autowired
    private QuestionRepo questionRepo;

    /**
     * Get a list of all questions.
     * @return ResponseEntity with a list of questions
     */
    @ApiOperation(value = "Get all questions", notes = "Get a list of all questions.")
    @GetMapping("/get-all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestion();
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    /**
     * Get a list of all questions with pagination support.
     * @param page Page number
     * @param size Page size
     * @return ResponseEntity with a list of questions
     */
    @ApiOperation(value = "Get all questions with pagination", notes = "Get a list of all questions with pagination support.")
    @GetMapping("/get-all-questions-with-pagination")
    public ResponseEntity<List<Question>> getAllQuestions(
            @ApiParam(value = "Page number", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Page size", defaultValue = "10") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questionPage = questionRepo.findAll(pageable);
        List<Question> questions = questionPage.getContent();
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    /**
     * Get a question by ID.
     * @param id Question ID
     * @return ResponseEntity with a question
     */
    @ApiOperation(value = "Get a question by ID", notes = "Get a question by its unique ID.")
    @GetMapping(path = "{id}")
    public ResponseEntity<Question> getQuestionById(
            @ApiParam(value = "Question ID") @PathVariable long id) {
        try {
            Question question = questionService.getQuestionById(id);
            if (question == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Create a new question.
     * @param question Question object
     * @return ResponseEntity with the created question
     */
    @ApiOperation(value = "Create a new question", notes = "Create a new question.")
    @PostMapping()
    public ResponseEntity<Question> createQuestion(
            @ApiParam(value = "Question object", required = true) @RequestBody Question question) {
        try {
            Question newQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Update an existing question.
     * @param id Question ID
     * @param question Updated question object
     * @return ResponseEntity with the updated question
     */
    @ApiOperation(value = "Update a question", notes = "Update an existing question.")
    @PutMapping(path = "{id}")
    public ResponseEntity<Question> updateQuestion(
            @ApiParam(value = "Question ID") @PathVariable long id,
            @ApiParam(value = "Updated question object", required = true) @RequestBody Question question) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, question);
            if (updatedQuestion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete a question by ID.
     * @param id Question ID
     * @return ResponseEntity with the result of the deletion
     */
    @ApiOperation(value = "Delete a question", notes = "Delete a question by its unique ID.")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteQuestion(
            @ApiParam(value = "Question ID") @PathVariable long id) {
        try {
            if (!questionService.deleteQuestion(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Filter questions by difficulty level.
     * @param difficulty Difficulty level
     * @return ResponseEntity with a list of filtered questions
     */
    @ApiOperation(value = "Filter questions by difficulty", notes = "Get a list of questions filtered by difficulty level.")
    @GetMapping("/filter")
    public ResponseEntity<List<Question>> filterQuestionsByDifficulty(
            @ApiParam(value = "Difficulty level", example = "Easy") @RequestParam String difficulty) {
        try {
            List<Question> questions = questionService.filterQuestionsByDifficulty(difficulty);
            return ResponseEntity.status(HttpStatus.OK).body(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get a random question.
     * @return ResponseEntity with a random question
     */
    @ApiOperation(value = "Get a random question", notes = "Get a random question.")
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        try {
            Question question = questionService.getRandomQuestion();
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get the total count of questions.
     * @return ResponseEntity with the total count of questions
     */
    @ApiOperation(value = "Get the total count of questions", notes = "Get the total count of questions.")
    @GetMapping("/count")
    public ResponseEntity<Long> getQuestionsCount() {
        try {
            long count = questionService.getQuestionsCount();
            return ResponseEntity.status(HttpStatus.OK).body(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get questions by category.
     * @param category Category name
     * @return ResponseEntity with a list of questions in the specified category
     */
    @ApiOperation(value = "Get questions by category", notes = "Get a list of questions by category.")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(
            @ApiParam(value = "Category name") @PathVariable String category) {
        try {
            List<Question> questions = questionService.getQuestionsByCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

