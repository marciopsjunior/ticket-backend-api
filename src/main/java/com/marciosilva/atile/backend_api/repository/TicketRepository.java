package com.marciosilva.atile.backend_api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marciosilva.atile.backend_api.enums.TicketStatus;
import com.marciosilva.atile.backend_api.model.Ticket;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {

    private final File file = new File("src/main/resources/tickets_mock_db.json");
    private final ObjectMapper mapper;
    private List<Ticket> tickets;

    public TicketRepository() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        tickets = readFromFile();
    }

    private List<Ticket> readFromFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                mapper.writeValue(file, new ArrayList<>());
            }
            List<Ticket> list = mapper.readValue(file, new TypeReference<List<Ticket>>() {});
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ticket save(Ticket ticket) {
        ticket.setId();
        ticket.setCreatedAt();
        tickets.add(ticket);
        writeToFile();
        return ticket;
    }

    public List<Ticket> findAll() {
        return tickets;
    }

    public Optional<Ticket> findById(Long id) {
        return tickets.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public void delete(Long id) {
        tickets.removeIf(t -> t.getId().equals(id));
        writeToFile();
    }

    public Ticket updateStatus(Long id, TicketStatus status) {
        Optional<Ticket> ticketOpt = findById(id);
        ticketOpt.ifPresent(t -> {
            t.setStatus(status);
            writeToFile();
        });
        return ticketOpt.orElse(null);
    }
}