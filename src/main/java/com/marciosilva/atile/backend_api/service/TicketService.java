package com.marciosilva.atile.backend_api.service;

import com.marciosilva.atile.backend_api.dto.TicketRequest;
import com.marciosilva.atile.backend_api.dto.TicketResponse;
import com.marciosilva.atile.backend_api.enums.TicketStatus;
import com.marciosilva.atile.backend_api.model.Ticket;
import com.marciosilva.atile.backend_api.repository.TicketRepository;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository repository;

    public TicketService(){
        repository = new TicketRepository();
    }

    public List<Ticket> findAll() {
        return repository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return repository.findById(id);
    }

    public Ticket create(TicketRequest request) {
        Ticket ticket = new Ticket(null, request.title(), request.description(), request.status(),null);
        return repository.save(ticket);
    }

    public Ticket updateStatus(Long id, TicketStatus status) {
        return repository.updateStatus(id, status);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
