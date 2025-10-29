package ru.lazzzer64.bankrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazzzer64.bankrest.DTO.CardRequestDTO;
import ru.lazzzer64.bankrest.DTO.CardResponseDTO;
import ru.lazzzer64.bankrest.entity.BankAccount;
import ru.lazzzer64.bankrest.entity.Card;
import ru.lazzzer64.bankrest.entity.CardStatus;
import ru.lazzzer64.bankrest.repository.BankAccountRepository;
import ru.lazzzer64.bankrest.repository.CardRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public CardResponseDTO createCard(CardRequestDTO requestDTO) {
        BankAccount account = requestDTO.getUser().getBankAccount();

        Card card = new Card();
        card.setCardNumber(requestDTO.getCardNumber());
        card.setExpiryDate(requestDTO.getExpiryDate());
        card.setAccount(account);
        card.setStatus(CardStatus.ACTIVE);

        Card savedCard = cardRepository.save(card);
        return convertToDTO(savedCard);
    }

    @Transactional(readOnly = true)
    public CardResponseDTO getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена с ID: " + id));
        return convertToDTO(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponseDTO> getAllCards() {
        return cardRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
//
//    @Transactional(readOnly = true)
//    public Page<CardResponseDTO> getCardsByAccountId(BankAccount bankAccount, Pageable pageable) {
//        return cardRepository.findByAccount(bankAccount, pageable)
//                .map(this::convertToDTO);
//    }

    public CardResponseDTO updateCardStatus(Long id, CardStatus status) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена с ID: " + id));

        card.setStatus(status);

        Card updatedCard = cardRepository.save(card);
        return convertToDTO(updatedCard);
    }

    private CardResponseDTO convertToDTO(Card card) {
        CardResponseDTO dto = new CardResponseDTO();
        dto.setId(card.getId());
        dto.setMaskedCardNumber(card.getMaskedCardNumber());
        dto.setExpireDate(card.getExpiryDate());
        dto.setCardStatus(card.getStatus());
        dto.setBalance(card.getBalance());
        return dto;
    }
}