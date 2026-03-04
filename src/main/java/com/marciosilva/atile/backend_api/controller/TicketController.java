package com.marciosilva.atile.backend_api.controller;
import com.marciosilva.atile.backend_api.dto.TicketRequest;
import com.marciosilva.atile.backend_api.dto.TicketResponse;
import com.marciosilva.atile.backend_api.dto.UpdateStatusRequest;
import com.marciosilva.atile.backend_api.enums.TicketStatus;
import com.marciosilva.atile.backend_api.model.Ticket;
import com.marciosilva.atile.backend_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service = new TicketService();

    @GetMapping
    public TicketResponse<List<Ticket>> getTickets(){
        List<Ticket> tickets = service.findAll();
        return TicketResponse.success(
                tickets,
                "Records found!"
        );
    }

    @GetMapping("/{id}")
    public TicketResponse<Ticket> getTicketById(@PathVariable Long id) {
        return service.findById(id)
                .map(ticket -> TicketResponse.success(ticket, "Ticket found"))
                .orElse(TicketResponse.failure("Ticket not found"));
    }

    @PostMapping
    public TicketResponse<Ticket> createTicket(@Valid @RequestBody TicketRequest request) {
        try {
            Ticket created = service.create(request);
            return TicketResponse.success(created, "Ticket created successfully");
        } catch (Exception e) {
            return TicketResponse.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public TicketResponse<Ticket> updateTicketStatus(@PathVariable Long id, @RequestBody @Valid UpdateStatusRequest request) {
        try {
            Ticket updated = service.updateStatus(id, request.status());
            if (updated == null) {
                return TicketResponse.failure("Ticket not found");
            }
            return TicketResponse.success(null, "Ticket status updated successfully");
        } catch (Exception e) {
            return TicketResponse.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public TicketResponse<Void> deleteTicket(@PathVariable Long id) {
        service.delete(id);
        return TicketResponse.success(null, "Ticket deleted successfully");
    }
}
