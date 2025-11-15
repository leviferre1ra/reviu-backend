package br.edu.fatecpg.reviu.services;

import br.edu.fatecpg.reviu.domain.deck.Deck;
import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.repositories.DeckRepository;
import br.edu.fatecpg.reviu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeckService {
    public final DeckRepository deckRepository;
    public final UserRepository userRepository;

    public Deck createDeck(Long userId, Deck deck) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return deckRepository.save(deck);
    }
}
