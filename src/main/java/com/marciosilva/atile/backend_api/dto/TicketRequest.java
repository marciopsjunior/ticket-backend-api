package com.marciosilva.atile.backend_api.dto;

import com.marciosilva.atile.backend_api.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotBlank(message = "Title is required!")
        String title,
        @NotBlank(message = "Description is required!")
        String description,
        @NotNull(message = "Status is required!")
        TicketStatus status
) {
}
