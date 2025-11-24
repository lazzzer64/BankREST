package ru.lazzzer64.bankrest.dto.cardDTO;

import java.math.BigDecimal;

public class CardTransactionDTO {
    private BigDecimal balabnce;

    public CardTransactionDTO(BigDecimal balabnce) {
        this.balabnce = balabnce;
    }

    public BigDecimal getBalabnce() {
        return balabnce;
    }

    public void setBalabnce(BigDecimal balabnce) {
        this.balabnce = balabnce;
    }
}