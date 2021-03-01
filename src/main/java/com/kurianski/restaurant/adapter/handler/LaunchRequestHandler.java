package com.kurianski.restaurant.adapter.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class LaunchRequestHandler implements com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        String speechText = "Bem-vindo a skill do Bandeijão da USP. O que deseja saber?";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withReprompt(speechText)
                .build();
    }
}
