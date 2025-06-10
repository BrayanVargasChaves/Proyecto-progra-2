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

    public void createDistribution(int difficulty) {
        List<Card> allCards = generateAllCards(difficulty);
        Collections.shuffle(allCards);

        cardsInColumns.clear();
        cardsInDeck.clear();

        // Crear 10 columnas vacías
        for (int i = 0; i < 10; i++) {
            cardsInColumns.add(new ArrayList<>());
        }

        // Repartir 54 cartas en columnas (6 primeras columnas con 6 cartas, 4 restantes con 5)
        int index = 0;
        for (int i = 0; i < 54; i++) {
            List<Card> column = cardsInColumns.get(i % 10);
            Card card = allCards.get(index++);
            // Solo la última carta de cada columna se muestra boca arriba
            int posInCol = column.size();
            card.setBocaArriba((posInCol == 5 || (i % 10 >= 6 && posInCol == 4)) ? 1 : 0);
            column.add(card);
        }

        // Las cartas restantes van a la baraja
        while (index < allCards.size()) {
            cardsInDeck.add(allCards.get(index++));
        }
    }

    private List<Card> generateAllCards(int difficulty) {
        List<Card> cards = new ArrayList<>();
        String[] suits;
        int sets;

        switch (difficulty) {
            case 1:
                suits = new String[]{"Picas"};
                sets = 8; // 8 sets de 13 = 104 cartas
                break;
            case 2:
                suits = new String[]{"spades", "Corazones"};
                sets = 4; // 4 sets por palo
                break;
            case 4:
                suits = new String[]{"Picas", "Corazones", "Trevor", "Diamantes"};
                sets = 2; // 2 sets por palo
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
