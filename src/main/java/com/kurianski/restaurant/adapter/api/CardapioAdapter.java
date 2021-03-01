package com.kurianski.restaurant.adapter.api;

import com.kurianski.restaurant.adapter.domain.ApiResponse;
import com.kurianski.restaurant.application.port.CardapioPort;
import com.kurianski.restaurant.domain.RefeicoesDoDia;
import feign.Feign;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;

import java.time.DayOfWeek;

public class CardapioAdapter implements CardapioPort {
    private final CardapioApi cardapioApi;
    private final String USP_RESTAURANT_HASH = System.getenv("USP_RESTAURANT_HASH");

    public CardapioAdapter() {
        String UspRestaurantUrl = System.getenv("USP_RESTAURANT_URL");

        cardapioApi = Feign.builder()
                .encoder(new FormEncoder())
                .decoder(new GsonDecoder())
                .target(CardapioApi.class, UspRestaurantUrl);
    }

    @Override
    public RefeicoesDoDia getCardapioDiaSemana(DayOfWeek diaSemana) {
        ApiResponse apiResponse = cardapioApi.pegarRefeicoesDaSemana(USP_RESTAURANT_HASH);
        if(apiResponse.getMeals().isEmpty()) return null;
        return apiResponse.getMeals().get(diaSemana.getValue() - 1);
    }
}
