/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.util;

import cr.ac.una.proyectoprogra2.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardFactory {

    private static CardFactory instance;
    private List<List<Card>> cardsInColumns;
    private List<Card> cardsInDeck;

    private CardFactory() {
        cardsInColumns = new ArrayList<>();
        cardsInDeck = new ArrayList<>();
    }

    public static CardFactory getInstance() {
        if (instance == null) {
            instance = new CardFactory();
        }
        return instance;
    }

   public void createDistribution(String difficulty) {
    List<Card> allCards = generateAllCards(difficulty);
    Collections.shuffle(allCards);

    cardsInColumns.clear();
    cardsInDeck.clear();
    for (int i = 0; i < 10; i++) {
        cardsInColumns.add(new ArrayList<>());
    }
    int index = 0;
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 6; j++) {
            Card card = allCards.get(index++);
            card.setBocaArriba(j == 5 ? 1 : 0); 
            cardsInColumns.get(i).add(card);
        }
    }
    for (int i = 4; i < 10; i++) {
        for (int j = 0; j < 5; j++) {
            Card card = allCards.get(index++);
            card.setBocaArriba(j == 4 ? 1 : 0);
            cardsInColumns.get(i).add(card);
        }
    }
    while (index < allCards.size()) {
        cardsInDeck.add(allCards.get(index++));
    }
}

    private List<Card> generateAllCards(String difficulty) {
        List<Card> cards = new ArrayList<>();
        String[] suits;
        int sets;

        switch (difficulty) {
            case "f":
                suits = new String[]{"Picas"};
                sets = 8; 
                break;
            case "m":
                suits = new String[]{"Picas", "Corazones"};
                sets = 4; 
                break;
            case "d":
                suits = new String[]{"Picas", "Corazones", "Trevor", "Diamante"};
                sets = 2; 
                break;
            default:
                suits = new String[]{"Picas"};
                sets = 8;
                break;
        }

        for (String suit : suits) {
            for (int s = 0; s < sets; s++) {
                for (int value = 1; value <= 13; value++) {
                    cards.add(new Card(null, value, suit, 0));
                }
            }
        }

        return cards;
    }

    public List<List<Card>> getCardsInColumns() {
        return cardsInColumns;
    }

    public List<Card> getCardsInDeck() {
        return cardsInDeck;
    }
}
