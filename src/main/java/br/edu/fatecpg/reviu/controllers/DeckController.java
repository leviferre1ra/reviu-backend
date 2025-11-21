package br.edu.fatecpg.reviu.controllers;

import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.dto.DeckRequestDTO;
import br.edu.fatecpg.reviu.dto.DeckResponseDTO;
import br.edu.fatecpg.reviu.services.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @PostMapping
    public ResponseEntity<DeckResponseDTO> createDeck(@AuthenticationPrincipal User user, @RequestBody DeckRequestDTO request){

        DeckResponseDTO newDeck = deckService.createDeck(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
    }

    @GetMapping
    public ResponseEntity<List<DeckResponseDTO>> getDeckByUser(@AuthenticationPrincipal User user){

        List<DeckResponseDTO> decks= deckService.getDeckByUser(user.getId());
        return ResponseEntity.ok(decks);
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<DeckResponseDTO> updateDeck(@AuthenticationPrincipal User user, @PathVariable Long deckId, @RequestBody DeckRequestDTO request){

        DeckResponseDTO updateDeck = deckService.updateDeck(user.getId(), deckId, request);
        return ResponseEntity.ok(updateDeck);
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@AuthenticationPrincipal User user, @PathVariable Long deckId){

        deckService.deleteDeck(user.getId(), deckId);
        return ResponseEntity.noContent().build();

    }
}
