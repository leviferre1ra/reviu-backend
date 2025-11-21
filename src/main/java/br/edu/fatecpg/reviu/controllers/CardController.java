package br.edu.fatecpg.reviu.controllers;

import br.edu.fatecpg.reviu.domain.deck.Deck;
import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.dto.CardRequestDTO;
import br.edu.fatecpg.reviu.dto.CardResponseDTO;
import br.edu.fatecpg.reviu.dto.DeckRequestDTO;
import br.edu.fatecpg.reviu.dto.DeckResponseDTO;
import br.edu.fatecpg.reviu.services.CardService;
import br.edu.fatecpg.reviu.services.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks/{deckId}/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@PathVariable Long deckId, @RequestBody CardRequestDTO request){

        CardResponseDTO newDeck = cardService.createCard(deckId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getCardByUser(@PathVariable Long deckId){

        List<CardResponseDTO> cards= cardService.getCardByUser(deckId);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponseDTO> updateCard(@PathVariable Long deckId, @PathVariable Long cardId, @RequestBody CardRequestDTO request){

        CardResponseDTO updateCard = cardService.updateCard(deckId, cardId, request);
        return ResponseEntity.ok(updateCard);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long deckId, @PathVariable Long cardId){

        cardService.deleteCard(deckId, cardId);
        return ResponseEntity.noContent().build();

    }
}
