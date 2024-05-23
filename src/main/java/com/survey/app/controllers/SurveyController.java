package com.survey.app.controllers;

import com.survey.app.model.Survey;
import com.survey.app.service.SurveyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Api(tags = "Survey Management", description = "Operations pertaining to survey management")
@RequestMapping("api/v1/survey")
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    /**
     * Get all surveys.
     *
     * @return A list of all surveys.
     */
    @ApiOperation(value = "Get all surveys", notes = "Retrieve a list of all surveys.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of surveys"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurvey();
        return ResponseEntity.ok().body(surveys);
    }

    /**
     * Get a survey by ID.
     *
     * @param id The ID of the survey.
     * @return The survey with the specified ID.
     */
    @ApiOperation(value = "Get a survey by ID", notes = "Retrieve a survey by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved survey"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "{id}")
    public ResponseEntity<Survey> getSurveyById(
            @ApiParam(value = "Survey ID", required = true) @PathVariable("id") UUID id) {
        Survey survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok().body(survey);
    }

    /**
     * Create a new survey.
     *
     * @param survey The survey object to be created.
     * @return The created survey.
     */
    @ApiOperation(value = "Create a new survey", notes = "Create a new survey.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created survey"),
            @ApiResponse(code = 401, message = "You are not authorized to create the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<Survey> createSurvey(
            @ApiParam(value = "Survey object", required = true) @RequestBody Survey survey) {
        Survey createdSurvey = surveyService.createSurvey(survey);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }

    /**
     * Update an existing survey.
     *
     * @param id     The ID of the survey to be updated.
     * @param survey The updated survey object.
     * @return The updated survey.
     */
    @ApiOperation(value = "Update an existing survey", notes = "Update a survey by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated survey"),
            @ApiResponse(code = 401, message = "You are not authorized to update the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(path = "{id}")
    public ResponseEntity<Survey> updateSurvey(
            @ApiParam(value = "Survey ID", required = true) @PathVariable("id") UUID id,
            @ApiParam(value = "Updated survey object", required = true) @Valid @NotNull @RequestBody Survey survey) {
        Survey updatedSurvey = surveyService.updateSurvey(id, survey);
        return ResponseEntity.ok().body(updatedSurvey);
    }

    /**
     * Delete a survey by ID.
     *
     * @param id The ID of the survey to be deleted.
     * @return HTTP status indicating the outcome of the operation.
     */
    @ApiOperation(value = "Delete a survey by ID", notes = "Delete a survey by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted survey"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(path = "{id}")
    public HttpStatus deleteSurveyById(
            @ApiParam(value = "Survey ID", required = true) @PathVariable("id") UUID id) {
        surveyService.deleteSurvey(id);
        return HttpStatus.OK;
    }
}
