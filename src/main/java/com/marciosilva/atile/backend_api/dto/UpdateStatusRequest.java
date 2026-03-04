package com.marciosilva.atile.backend_api.dto;

import com.marciosilva.atile.backend_api.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull(message = "Status is required")
        TicketStatus status
) {}
