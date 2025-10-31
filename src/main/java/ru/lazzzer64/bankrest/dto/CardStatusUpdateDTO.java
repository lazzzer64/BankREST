package ru.lazzzer64.bankrest.dto;

import jakarta.validation.constraints.NotNull;
import ru.lazzzer64.bankrest.entity.CardStatus;

public class CardStatusUpdateDTO {
    @NotNull(message = "Статус обязателен")
    private CardStatus status;

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }
}
