package com.leoneces.rnd_library;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class MyController {

    @Operation(summary = "Get greeting message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful response")
    })
    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hello, World!";
    }
}
