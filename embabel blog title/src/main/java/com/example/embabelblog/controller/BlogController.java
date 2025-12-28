package com.example.embabelblog.controller;

import com.example.embabelblog.agent.model.*;
import io.embabel.core.AgentRunner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final AgentRunner agentRunner;

    public BlogController(AgentRunner agentRunner) {
        this.agentRunner = agentRunner;
    }

    @PostMapping("/titles")
    public BlogTitles generateTitles(@RequestBody UserInput input) {
        return agentRunner.run(input, BlogTitles.class);
    }
}
