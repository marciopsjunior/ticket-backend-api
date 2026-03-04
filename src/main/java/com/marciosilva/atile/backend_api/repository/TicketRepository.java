package com.marciosilva.atile.backend_api.repository;

import com.marciosilva.atile.backend_api.enums.TicketStatus;
import com.marciosilva.atile.backend_api.model.Ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketRepository {

    private static final List<Ticket> tickets = new CopyOnWriteArrayList<>();

    public TicketRepository() {
    }

    public Ticket save(Ticket ticket) {
        ticket.setId();
        ticket.setCreatedAt();

        tickets.add(ticket);

        return ticket;
    }

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets);
    }

    public Optional<Ticket> findById(Long id) {
        return tickets.stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst();
    }

    public Ticket updateStatus(Long id, TicketStatus status) {
        Optional<Ticket> ticketOpt = tickets.stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst();

        ticketOpt.ifPresent(t -> t.setStatus(status));

        return ticketOpt.orElse(null);
    }

    public boolean delete(Long id) {
        boolean removed = tickets.removeIf(t -> id.equals(t.getId()));
        return removed;
    }
}