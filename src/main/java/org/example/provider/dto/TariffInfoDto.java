package org.example.provider.dto;

import java.math.BigDecimal;
// JSON 1
// ну, я думаю, это будет передаваться на фронтенд при создании страницы тарифов
// то есть название, тип (ибо структура одна и та же, а наполнение-то разное)
// стоимость, описание и число клиентов. все кроме последнего в таблице
// количество клиентов вычисляется

public record TariffInfoDto(
        Long id,
        String type,
        String name,
        BigDecimal cost,
        String description,
        int clientCount
) {}
