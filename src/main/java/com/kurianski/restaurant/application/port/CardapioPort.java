package com.kurianski.restaurant.application.port;

import com.kurianski.restaurant.domain.RefeicoesDoDia;

import java.time.DayOfWeek;

public interface CardapioPort {
    RefeicoesDoDia getCardapioDiaSemana(DayOfWeek diaSemana);
}
