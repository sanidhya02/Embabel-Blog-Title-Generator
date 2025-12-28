package com.example.blogtitler;

import io.embabel.agent.api.Action;
import io.embabel.agent.api.Actor;
import io.embabel.agent.api.Agent;
import io.embabel.agent.api.UserInput;
import io.embabel.agent.api.annotations.AchievesGoal;
import io.embabel.agent.api.llm.Ai;
import io.embabel.agent.api.llm.LlmOptions;
import io.embabel.agent.api.session.OperationContext;

import java.util.List;

/**
 * An AI agent that extracts topics from text and generates blog titles.
 * Demonstrates Embabel's type-safe, domain-driven approach to agent design.
 */
@Agent(description = "Blog Title Generator Agent")
public class BlogTitlerAgent {

    private final Actor techWriter = new Actor<>(
        """
        You are an expert technical writer. Always give clear,
        concise, and straight-to-the-point answers.
        """,
        LlmOptions.withAutoLlm()
    );

    /**
     * Represents extracted topics from the input text.
     * Using proper types instead of strings for better structure.
     */
    public record Topics(List topics) {}

    /**
     * Represents blog titles for a specific topic.
     */
    public record TopicTitles(
        String topic,
        List titles
    ) {}

    /**
     * The final result containing all generated titles organized by topic.
     */
    public record BlogTitles(List topicTitles) {}

    /**
     * Step 1: Extract 1-3 key topics from the input text.
     * 
     * @param userInput The text to analyze
     * @param ai AI context for LLM interactions
     * @return Structured list of topics
     */
    @Action
    public Topics extractTopics(UserInput userInput, Ai ai) {
        System.out.println("\n📝 Extracting topics...");
        
        Topics topics = techWriter.promptRunner(ai)
            .creating(Topics.class)
            .fromPrompt("""
                Extract 1-3 key topics from the following text:
                %s
                """.formatted(userInput.getContent()));
        
        System.out.println("✅ Topics extracted: " + topics.topics());
        return topics;
    }

    /**
     * Step 2: Generate blog titles for each extracted topic in parallel.
     * The @AchievesGoal annotation tells Embabel this is the final step.
     * 
     * @param topics The extracted topics
     * @param context Operation context for parallel execution
     * @return All generated titles organized by topic
     */
    @Action
    @AchievesGoal(description = "Generate Titles for Topics")
    public BlogTitles generateBlogTitles(Topics topics, OperationContext context) {
        System.out.println("\n✨ Generating titles for each topic (in parallel)...");
        
        // Parallel execution: process all topics simultaneously
        var titles = context.parallelMap(
            topics.topics(),
            10, // max concurrency
            topic -> {
                System.out.println("  🔄 Processing topic: " + topic);
                return techWriter.promptRunner(context)
                    .creating(TopicTitles.class)
                    .fromPrompt("""
                        Generate two catchy blog titles for this topic:
                        %s
                        """.formatted(topic));
            }
        );
        
        return new BlogTitles(titles);
    }
}
