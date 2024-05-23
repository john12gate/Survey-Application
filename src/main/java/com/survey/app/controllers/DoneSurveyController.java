package com.survey.app.controllers;

import com.survey.app.model.DoneSurvey;
import com.survey.app.service.DoneSurveyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Done Survey Management", description = "Operations pertaining to done surveys management")
@RequestMapping("api/v1/done_survey")
@RestController
public class DoneSurveyController {

    @Autowired
    private DoneSurveyService doneSurveyService;

    /**
     * Get all done surveys.
     *
     * @return A list of all done surveys.
     */
    @ApiOperation(value = "Get all done surveys", notes = "Retrieve a list of all done surveys.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of done surveys"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<DoneSurvey>> getAllDoneSurveys() {
        List<DoneSurvey> doneSurveys = doneSurveyService.getAllDoneSurvey();
        return ResponseEntity.ok().body(doneSurveys);
    }

    /**
     * Get a done survey by ID.
     *
     * @param id The ID of the done survey.
     * @return The done survey with the specified ID.
     */
    @ApiOperation(value = "Get a done survey by ID", notes = "Retrieve a done survey by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved done survey"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "{id}")
    public ResponseEntity<DoneSurvey> getDoneSurveyById(
            @ApiParam(value = "Done Survey ID", required = true) @PathVariable("id") UUID id) {
        DoneSurvey doneSurvey = doneSurveyService.getDoneSurveyById(id);
        return ResponseEntity.ok().body(doneSurvey);
    }

    /**
     * Create a new done survey.
     *
     * @param doneSurvey The done survey object to be created.
     * @return The created done survey.
     */
    @ApiOperation(value = "Create a new done survey", notes = "Create a new done survey.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created done survey"),
            @ApiResponse(code = 401, message = "You are not authorized to create the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<DoneSurvey> createDoneSurvey(
            @ApiParam(value = "Done Survey object", required = true) @RequestBody DoneSurvey doneSurvey) {
        DoneSurvey createdDoneSurvey = doneSurveyService.createDoneSurvey(doneSurvey);
        return ResponseEntity.status(201).body(createdDoneSurvey);
    }
}
