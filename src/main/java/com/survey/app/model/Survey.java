package com.survey.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Survey represents a collection of questions designed to gather insights
 * and opinions from respondents. Each survey is carefully crafted with a
 * specific goal in mind, ensuring that the data collected is valuable
 * and actionable.
 */
@ApiModel(description = "Represents a collection of questions designed to gather insights and opinions from respondents")
@Entity(name = "SURVEY")
public class Survey {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The unique identifier of the survey, automatically generated")
    private UUID id;

    @NotBlank
    @ApiModelProperty(notes = "The title of the survey, providing context and purpose")
    private String title;

    @ApiModelProperty(notes = "Flag indicating whether the survey is open or closed for responses")
    private boolean open;

    @ApiModelProperty(notes = "The name of the creator of the survey, ensuring accountability and ownership")
    private String creatorName;

    @OneToMany(mappedBy = "survey")
    private Set<Question> questionList = new HashSet<>();

    /**
     * Default constructor for JPA.
     * This constructor facilitates the seamless integration and persistence of survey data within the JPA context.
     */
    public Survey() {
    }

    /**
     * Parameterized constructor for creating a Survey instance with detailed survey information.
     *
     * @param id           The unique identifier of the survey, enhancing data traceability
     * @param title        The title of the survey, providing context and purpose
     * @param creatorName  The name of the creator of the survey, ensuring accountability and ownership
     * @param open         Flag indicating whether the survey is open or closed for responses
     * @param questionList The list of questions included in the survey, designed to gather specific insights
     */
    public Survey(UUID id, String title, String creatorName, boolean open, Set<Question> questionList) {
        this.id = id;
        this.title = title;
        this.creatorName = creatorName;
        this.open = open;
        this.questionList = questionList;
    }

    // Getters and setters omitted for brevity


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Set<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<Question> questionList) {
        this.questionList = questionList;
    }
}
