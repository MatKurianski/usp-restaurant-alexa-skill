package com.kurianski.restaurant.adapter.api;

import com.kurianski.restaurant.adapter.domain.ApiResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CardapioApi {
    @RequestLine("POST /")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ApiResponse pegarRefeicoesDaSemana(@Param("hash") String hash);
}
