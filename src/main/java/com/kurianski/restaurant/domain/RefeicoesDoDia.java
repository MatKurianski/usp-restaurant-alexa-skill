package com.kurianski.restaurant.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RefeicoesDoDia {
    Refeicao lunch;
    Refeicao dinner;
    String date;
}
