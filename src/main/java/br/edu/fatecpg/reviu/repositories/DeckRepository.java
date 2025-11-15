package br.edu.fatecpg.reviu.repositories;

import br.edu.fatecpg.reviu.domain.deck.Deck;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
}
