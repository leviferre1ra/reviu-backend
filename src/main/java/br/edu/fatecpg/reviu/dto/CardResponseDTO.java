package br.edu.fatecpg.reviu.dto;

import br.edu.fatecpg.reviu.domain.card.Card;

import java.util.List;

public record CardResponseDTO(Long id, String frontText, String backText) {
    public CardResponseDTO(Card card){
        this(
                card.getId(),
                card.getFrontText(),
                card.getBackText()
        );
    }
}
