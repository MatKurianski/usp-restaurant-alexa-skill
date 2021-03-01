package com.kurianski.restaurant.adapter.domain;

import com.kurianski.restaurant.domain.RefeicoesDoDia;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ApiResponse {
    List<RefeicoesDoDia> meals;
}
