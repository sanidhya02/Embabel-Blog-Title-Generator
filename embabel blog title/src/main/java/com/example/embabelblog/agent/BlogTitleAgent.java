package com.example.embabelblog.agent;

import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.AchievesGoal;
import org.springframework.stereotype.Component;

import com.example.embabelblog.agent.model.*;

import java.util.List;

@Agent(description = "Generates blog titles using Gemini LLM")
@Component
public class BlogTitleAgent {

    @Action
    public Topics extractTopics(UserInput input, Ai ai) {

        String prompt = """
            Extract 13 concise blog topics from the following text.
            Respond strictly as JSON.

            Text:
            %s
            """.formatted(input.content());

        return ai.withAutoLlm()
                 .createObject(prompt, Topics.class);
    }

    @Action
    @AchievesGoal(description = "Generate blog titles")
    public BlogTitles generateTitles(Topics topics, OperationContext context) {

        List<TopicTitles> results = context.parallelMap(
            topics.topics(),
            10,
            topic -> {

                String prompt = """
                    Generate exactly 2 catchy blog titles for the topic below.
                    Respond strictly as JSON.

                    Topic: %s
                    """.formatted(topic);

                TopicTitles titles = context.ai()
                    .withAutoLlm()
                    .createObject(prompt, TopicTitles.class);

                return new TopicTitles(topic, titles.titles());
            }
        );

        return new BlogTitles(results);
    }
}
