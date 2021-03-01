package com.kurianski.restaurant.adapter.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.Predicates;
import com.kurianski.restaurant.application.usecase.PegarRefeicaoDoDiaUseCaseImpl;
import com.kurianski.restaurant.domain.RefeicoesDoDia;

import java.time.DayOfWeek;
import java.util.*;

public class RefeicoesDoDiaIntentHandler implements RequestHandler {
    private final PegarRefeicaoDoDiaUseCaseImpl pegarRefeicaoDoDiaUseCase;
    private static final String DIA_DA_SEMANA_KEY = "dia_semana";

    public RefeicoesDoDiaIntentHandler() {
        pegarRefeicaoDoDiaUseCase = new PegarRefeicaoDoDiaUseCaseImpl();
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("RefeicoesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String, Slot> slots = this.extractSlots(input);

        if (slots.containsKey(DIA_DA_SEMANA_KEY)) {
            Slot diaSemanaSlot = slots.get(DIA_DA_SEMANA_KEY);
            DayOfWeek diaSemana = this.getDayOfWeekFromString(diaSemanaSlot);

            if (diaSemana != null) {
                RefeicoesDoDia refeicoesDoDia = pegarRefeicaoDoDiaUseCase.execute(diaSemana);

                StringBuilder resposta = new StringBuilder();
                resposta.append("No almoço dessa ");
                resposta.append(diaSemanaSlot.getValue());
                resposta.append("-feira tem ");
                resposta.append(refeicoesDoDia.getLunch().getMenu());
                resposta.append(". O total de calorias é ");
                resposta.append(refeicoesDoDia.getLunch().getCalories());

                return input.getResponseBuilder().withSpeech(resposta.toString()).build();
            }
        }
        return input.getResponseBuilder().withSpeech("Não foi possível completar sua solicitação").build();
    }

    private Map<String, Slot> extractSlots(HandlerInput handlerInput) {
        return Optional.of(handlerInput)
                .map(HandlerInput::getRequestEnvelope)
                .map(RequestEnvelope::getRequest)
                .map(request -> request instanceof IntentRequest ? (IntentRequest) request : null)
                .map(IntentRequest::getIntent)
                .map(Intent::getSlots)
                .orElse(new HashMap<>());
    }

    private DayOfWeek getDayOfWeekFromString(Slot dayOfWeek) {
        switch (dayOfWeek.getValue().toLowerCase()) {
            case "segunda":
            case "segunda-feira":
            case "2ª":
                return DayOfWeek.MONDAY;
            case "terça":
            case "terça-feira":
            case "3ª":
                return DayOfWeek.TUESDAY;
            case "quarta":
            case "quarta-feira":
            case "4ª":
                return DayOfWeek.WEDNESDAY;
            case "quinta":
            case "quinta-feira":
            case "5ª":
                return DayOfWeek.THURSDAY;
            case "sexta":
            case "sexta-feira":
            case "6ª":
                return DayOfWeek.FRIDAY;
            default:
                return null;
        }
    }
}