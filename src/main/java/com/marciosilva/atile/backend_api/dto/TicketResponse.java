package com.marciosilva.atile.backend_api.dto;

import com.marciosilva.atile.backend_api.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public record TicketResponse<T>(
        Boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public static <T> TicketResponse<T> success(T data, String message) {
        return new TicketResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> TicketResponse<T> failure(String message) {
        return new TicketResponse<>(false, message, null, LocalDateTime.now());
    }
}
