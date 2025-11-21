package br.edu.fatecpg.reviu.domain.card;

import br.edu.fatecpg.reviu.domain.deck.Deck;
import br.edu.fatecpg.reviu.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String frontText;
    private String backText;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;
}
