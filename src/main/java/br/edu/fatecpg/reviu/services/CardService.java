package br.edu.fatecpg.reviu.services;

import br.edu.fatecpg.reviu.domain.card.Card;
import br.edu.fatecpg.reviu.domain.deck.Deck;
import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.dto.CardRequestDTO;
import br.edu.fatecpg.reviu.dto.CardResponseDTO;
import br.edu.fatecpg.reviu.dto.DeckRequestDTO;
import br.edu.fatecpg.reviu.dto.DeckResponseDTO;
import br.edu.fatecpg.reviu.repositories.CardRepository;
import br.edu.fatecpg.reviu.repositories.DeckRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    //CREATE
    public CardResponseDTO createCard(Long deckId, CardRequestDTO request) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        Card card = new Card();
        card.setFrontText(request.frontText());
        card.setBackText(request.backText());
        card.setDeck(deck);


        cardRepository.save(card);
        return new CardResponseDTO(card.getId(), card.getFrontText(), card.getBackText());
    }

    //VIEW
    public List<CardResponseDTO> getCardByUser(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        List<Card> cards = cardRepository.findByDeckId(deckId);
        return cards.stream()
                .map(CardResponseDTO::new)
                .toList();
    }

    //UPDATE
    public CardResponseDTO updateCard(Long deckId, Long cardId, CardRequestDTO request){
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!card.getDeck().getId().equals(deck.getId())){
            throw new IllegalArgumentException("This card does not belong to this deck");
        }

        card.setFrontText(request.frontText());
        card.setBackText(request.backText());
        cardRepository.save(card);
        return new CardResponseDTO(card);
    }

    //DELETE
    public void deleteCard(Long deckId, Long cardId){
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!card.getDeck().getId().equals(deck.getId())){
            throw new IllegalArgumentException("This card does not belong to this deck");
        }

        cardRepository.delete(card);
    }


}
