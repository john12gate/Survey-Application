package com.survey.app;

import com.survey.app.model.PossibleAnswer;
import com.survey.app.model.Question;
import com.survey.app.model.Survey;
import com.survey.app.repositories.AnswerRepository;
import com.survey.app.repositories.QuestionRepository;
import com.survey.app.repositories.SurveyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

@SpringBootApplication
@EnableSwagger2
public class SurveyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(QuestionRepository questionRepository, AnswerRepository answerRepository, SurveyRepository surveyRepository){

		return args -> {
			Survey survey1 = new Survey();
			survey1.setTitle("Cel mai tare chestionar");
			survey1.setOpen(true);
			surveyRepository.save(survey1);

			Question question1 = new Question(1, "Personal Profile", "What is your name", false, "easy", "", "");
			question1.setSurvey(survey1);
			questionRepository.save(question1);

			PossibleAnswer possibleAnswer1 = new PossibleAnswer(1, "Answer1", new HashSet<>());
			possibleAnswer1.addQuestion(question1);
			answerRepository.save(possibleAnswer1);
		};
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo"))
				.paths(PathSelectors.any())
				.build();
	}
}

