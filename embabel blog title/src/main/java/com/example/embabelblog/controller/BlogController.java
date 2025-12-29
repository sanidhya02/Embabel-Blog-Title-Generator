package com.example.embabelblog.controller;

import com.example.embabelblog.agent.BlogTitleAgent;
import com.example.embabelblog.agent.model.*;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.OperationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogTitleAgent blogTitleAgent;
    private final Ai ai;
    private final OperationContext operationContext;

    public BlogController(BlogTitleAgent blogTitleAgent, Ai ai, OperationContext operationContext) {
        this.blogTitleAgent = blogTitleAgent;
        this.ai = ai;
        this.operationContext = operationContext;
    }

    @PostMapping("/titles")
    public BlogTitles generateTitles(@RequestBody UserInput input) {
        Topics topics = blogTitleAgent.extractTopics(input, ai);
        return blogTitleAgent.generateTitles(topics, operationContext);
    }
}
