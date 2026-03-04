package com.marciosilva.atile.backend_api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marciosilva.atile.backend_api.enums.TicketStatus;
import com.marciosilva.atile.backend_api.model.Ticket;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketRepository {

    private final File file = new File("tickets_mock_db.json");
    private final ObjectMapper mapper;

    public TicketRepository() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (!file.exists()) {
            try {
                file.createNewFile();
                mapper.writeValue(file, new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Ticket> readFromFile() {
        try {
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Ticket>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Ticket> tickets) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ticket save(Ticket ticket) {
        List<Ticket> tickets = readFromFile();
        ticket.setId();
        ticket.setCreatedAt();

        tickets.add(ticket);
        writeToFile(tickets);

        return ticket;
    }

    public List<Ticket> findAll() {
        return readFromFile();
    }

    public Optional<Ticket> findById(Long id) {
        return readFromFile()
                .stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst();
    }

    public Ticket updateStatus(Long id, TicketStatus status) {
        List<Ticket> tickets = readFromFile();

        Optional<Ticket> ticketOpt = tickets.stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst();

        ticketOpt.ifPresent(t -> t.setStatus(status));

        writeToFile(tickets);

        return ticketOpt.orElse(null);
    }

    public boolean delete(Long id) {
        List<Ticket> tickets = readFromFile();

        boolean removed = tickets.removeIf(t -> id.equals(t.getId()));

        if (removed) {
            writeToFile(tickets);
        }

        return removed;
    }
}