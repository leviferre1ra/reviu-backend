package br.edu.fatecpg.reviu.dto;

import br.edu.fatecpg.reviu.domain.card.Card;
import br.edu.fatecpg.reviu.domain.deck.Deck;

import java.util.List;

public record DeckResponseDTO(Long id, String name, List<CardResponseDTO> cards) {
    public DeckResponseDTO(Deck deck){
        this(
                deck.getId(),
                deck.getName(),
                deck.getCards() != null
                    ? deck.getCards().stream().map(CardResponseDTO::new).toList()
                    : List.of()

        );
    }
}
