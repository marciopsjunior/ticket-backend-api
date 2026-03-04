package com.marciosilva.atile.backend_api.model;

import com.marciosilva.atile.backend_api.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket {

    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;

    public Ticket() {
    }

    public Ticket(Long id, String titulo, String descricao, TicketStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = titulo;
        this.description = descricao;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(){
        id = ThreadLocalRandom.current().nextLong(1, 10_000_000_000L);;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }

    public void setStatus(TicketStatus status){
        this.status = status;
    }
}
