package com.survey.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * HomeController handles requests to the root, user, and admin endpoints,
 * providing different responses based on the authenticated principal.
 */
@RestController
public class HomeController {

    private static final String STYLE_AND_BOOTSTRAP = "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f8f9fa; color: #343a40; }" +
            ".container { margin-top: 50px; }" +
            ".welcome-message { font-size: 2.5rem; font-weight: 300; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
            ".text-center { text-align: center; }" +
            "</style>";

    /**
     * Handles requests to the root endpoint ("/").
     *
     * @param principal the authenticated principal
     * @return a welcome message with the principal's name if authenticated, otherwise a general welcome message
     */
    @GetMapping("/")
    public String home(Principal principal) {
        String name;
        if (principal != null) {
            name = principal.getName();
            return "<html><head>" + STYLE_AND_BOOTSTRAP + "</head><body>" +
                    "<div class='container'><h1 class='text-center welcome-message'>Welcome Admin " + name + "</h1></div>" +
                    "</body></html>";
        }
        return "<html><head>" + STYLE_AND_BOOTSTRAP + "</head><body>" +
                "<div class='container'><h1 class='text-center welcome-message'>Welcome</h1></div>" +
                "</body></html>";
    }

    /**
     * Handles requests to the user endpoint ("/user").
     *
     * @return a welcome message for users
     */
    @GetMapping("/user")
    public String user() {
        return "<html><head>" + STYLE_AND_BOOTSTRAP + "</head><body>" +
                "<div class='container'><h1 class='text-center welcome-message'>Welcome User</h1></div>" +
                "</body></html>";
    }

    /**
     * Handles requests to the admin endpoint ("/admin").
     *
     * @param principal the authenticated principal
     * @return a welcome message with the principal's name for admins
     */
    @GetMapping("/admin")
    public String admin(Principal principal) {
        String name = principal.getName();
        return "<html><head>" + STYLE_AND_BOOTSTRAP + "</head><body>" +
                "<div class='container'><h1 class='text-center welcome-message'>Welcome Admin " + name + "</h1></div>" +
                "</body></html>";
    }
}
