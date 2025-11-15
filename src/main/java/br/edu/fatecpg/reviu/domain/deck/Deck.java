package br.edu.fatecpg.reviu.domain.deck;

import br.edu.fatecpg.reviu.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "decks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    //@OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
