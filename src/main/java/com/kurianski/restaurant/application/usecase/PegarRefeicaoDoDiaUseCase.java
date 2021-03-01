package com.kurianski.restaurant.application.usecase;

import com.kurianski.restaurant.domain.RefeicoesDoDia;

import java.time.DayOfWeek;

public interface PegarRefeicaoDoDiaUseCase {
    public RefeicoesDoDia execute(DayOfWeek diaDaSemana);
}
