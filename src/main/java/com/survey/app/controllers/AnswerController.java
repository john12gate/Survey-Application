package com.survey.app.controllers;

import com.survey.app.model.PossibleAnswer;
import com.survey.app.service.AnswerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Answer Management", description = "Operations pertaining to answer management")
@RequestMapping("api/v1/answer")
@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * Get all possible answers.
     *
     * @return A list of all possible answers.
     */
    @ApiOperation(value = "Get all possible answers", notes = "Retrieve a list of all possible answers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of possible answers"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping()
    public ResponseEntity<List<PossibleAnswer>> getAllAnswers() {
        List<PossibleAnswer> answers = answerService.getAllAnswer();
        return ResponseEntity.ok().body(answers);
    }

    /**
     * Get a possible answer by ID.
     *
     * @param id The ID of the possible answer.
     * @return The possible answer with the specified ID.
     */
    @ApiOperation(value = "Get a possible answer by ID", notes = "Retrieve a possible answer by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved possible answer"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "{id}")
    public ResponseEntity<PossibleAnswer> getAnswerById(
            @ApiParam(value = "Possible Answer ID", required = true) @PathVariable long id) {
        PossibleAnswer answer = answerService.getAnswerById(id);
        return ResponseEntity.ok().body(answer);
    }

    /**
     * Create a new possible answer.
     *
     * @param possibleAnswer The possible answer object to be created.
     * @return The created possible answer.
     */
    @ApiOperation(value = "Create a new possible answer", notes = "Create a new possible answer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created possible answer"),
            @ApiResponse(code = 401, message = "You are not authorized to create the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping()
    public ResponseEntity<PossibleAnswer> createAnswer(
            @ApiParam(value = "Possible Answer object", required = true) @RequestBody PossibleAnswer possibleAnswer) {
        PossibleAnswer createdAnswer = answerService.createAnswer(possibleAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    /**
     * Update a possible answer.
     *
     * @param id The ID of the possible answer to be updated.
     * @param possibleAnswer The updated possible answer object.
     * @return The updated possible answer.
     */
    @ApiOperation(value = "Update a possible answer", notes = "Update an existing possible answer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated possible answer"),
            @ApiResponse(code = 401, message = "You are not authorized to update the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(path = "{id}")
    public ResponseEntity<PossibleAnswer> updateAnswer(
            @ApiParam(value = "Possible Answer ID", required = true) @PathVariable long id,
            @ApiParam(value = "Updated Possible Answer object", required = true) @RequestBody PossibleAnswer possibleAnswer) {
        PossibleAnswer updatedAnswer = answerService.updateAnswer(id, possibleAnswer);
        return ResponseEntity.ok().body(updatedAnswer);
    }

    /**
     * Delete a possible answer by ID.
     *
     * @param id The ID of the possible answer to be deleted.
     * @return HTTP status OK if the operation was successful.
     */
    @ApiOperation(value = "Delete a possible answer by ID", notes = "Delete a possible answer by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted possible answer"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(path = "{id}")
    public HttpStatus deleteAnswer(
            @ApiParam(value = "Possible Answer ID", required = true) @PathVariable long id) {
        answerService.deleteAnswer(id);
        return HttpStatus.OK;
    }
}
