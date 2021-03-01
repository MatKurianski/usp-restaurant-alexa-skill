package com.kurianski.restaurant.application.usecase;

import com.kurianski.restaurant.adapter.api.CardapioAdapter;
import com.kurianski.restaurant.application.port.CardapioPort;
import com.kurianski.restaurant.domain.RefeicoesDoDia;

import java.time.DayOfWeek;

public class PegarRefeicaoDoDiaUseCaseImpl implements PegarRefeicaoDoDiaUseCase {

    private final CardapioPort cardapioPort;

    public PegarRefeicaoDoDiaUseCaseImpl() {
        cardapioPort = new CardapioAdapter();
    }

    public RefeicoesDoDia execute(DayOfWeek diaDaSemana) {
        return cardapioPort.getCardapioDiaSemana(diaDaSemana);
    }
}
