package com.kurianski.restaurant.adapter.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.request.Predicates;
import com.kurianski.restaurant.application.usecase.PegarRefeicaoDoDiaUseCaseImpl;
import com.kurianski.restaurant.domain.Refeicao;
import com.kurianski.restaurant.domain.RefeicoesDoDia;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RefeicoesDoDiaIntentHandler implements RequestHandler {
    private final PegarRefeicaoDoDiaUseCaseImpl pegarRefeicaoDoDiaUseCase;
    private static final String DIA_DA_SEMANA_KEY = "dia_semana";
    private static final String TIPO_REFEICAO_KEY = "tipo_refeicao";

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

        Slot diaSemanaSlot = slots.get(DIA_DA_SEMANA_KEY);
        Slot tipoRefeicaoSlot = slots.get(TIPO_REFEICAO_KEY);

        DayOfWeek diaSemana = Optional.ofNullable(this.getDayOfWeekFromString(diaSemanaSlot))
                                .orElse(DayOfWeek.from(LocalDate.now()));

        RefeicoesDoDia refeicoesDoDia = pegarRefeicaoDoDiaUseCase.execute(diaSemana);

        String resposta = this.buildResponse(diaSemanaSlot, tipoRefeicaoSlot, refeicoesDoDia);

        return input.getResponseBuilder().withSpeech(resposta).build();
    }

    private String buildResponse(Slot diaSemanaSlot, Slot tipoRefeicao, RefeicoesDoDia refeicoesDoDia) {
        StringBuilder resposta = new StringBuilder();
        Refeicao refeicao;

        if (
            tipoRefeicao.getValue().equalsIgnoreCase("almoço") ||
            tipoRefeicao.getValue().equalsIgnoreCase("almoçar")
        ) {
            resposta.append("No almoço ");
            refeicao = refeicoesDoDia.getLunch();
        } else {
            resposta.append("Na janta ");
            refeicao = refeicoesDoDia.getDinner();
        }

        String diaDaSemana = diaSemanaSlot.getValue();

        if(Objects.isNull(diaDaSemana))
            resposta.append("de hoje ");
        else {
            resposta.append("dessa ");
            resposta.append(diaDaSemana);
            resposta.append("-feira tem ");
        }

        resposta.append(refeicao.getMenu());
        resposta.append(". O total de calorias é ");
        resposta.append(refeicao.getCalories());

        return resposta.toString();
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
        if(Objects.isNull(dayOfWeek.getValue())) return null;
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