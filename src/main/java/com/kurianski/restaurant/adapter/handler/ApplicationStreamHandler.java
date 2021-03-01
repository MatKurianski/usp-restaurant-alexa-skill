package com.kurianski.restaurant.adapter.handler;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class ApplicationStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new LaunchRequestHandler(),
                        new RefeicoesDoDiaIntentHandler(),
                        new CancelandStopIntentHandler(),
                        new FallbackIntentHandler(),
                        new HelpIntentHandler(),
                        new SessionEndedRequestHandler()
                ).build();
    }

    public ApplicationStreamHandler() {
        super(getSkill());
    }
}
