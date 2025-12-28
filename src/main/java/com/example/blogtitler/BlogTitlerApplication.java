package com.example.blogtitler;

import io.embabel.agent.api.UserInput;
import io.embabel.agent.api.session.Session;
import io.embabel.agent.api.session.SessionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogTitlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogTitlerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(SessionManager sessionManager) {
        return args -> {
            System.out.println("🚀 Embabel Blog Title Generator\n");
            System.out.println("=".repeat(60));

            String inputText = """
                LangGraph introduces a graph-based paradigm for building LLM-powered agents.
                It allows developers to create modular, debuggable, and reliable agent workflows
                using nodes, edges, and state passing.
                """;

            System.out.println("\n📖 Input Text:\n" + inputText);
            System.out.println("=".repeat(60));

            Session session = sessionManager.createSession("blog-titler");
            UserInput userInput = new UserInput(inputText);

            var result = session.achieve(
                BlogTitlerAgent.BlogTitles.class,
                userInput
            );

            System.out.println("\n✅ Processing Complete!");
            System.out.println("=".repeat(60));
            System.out.println("\n📊 Results:\n");
            
            result.topicTitles().forEach(topicTitle -> {
                System.out.println("📌 Topic: " + topicTitle.topic());
                topicTitle.titles().forEach(title -> 
                    System.out.println("  ✨ " + title)
                );
                System.out.println();
            });
        };
    }
}
