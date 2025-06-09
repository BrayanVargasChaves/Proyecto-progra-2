/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.service;

import cr.ac.una.proyectoprogra2.model.Card;
import cr.ac.una.proyectoprogra2.controller.GameController;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;
import javafx.geometry.Bounds;

public class GameAnimationsService {

    private static final double OFFSET_Y = 30;

    public void initialAnimation(List<List<Card>> cardsInBoard, List<Card> cardsInDeck,
                                  List<Pane> columnPanes, Pane deckPane, GameController controller) {
        deckPane.getChildren().clear();
        for (Card card : cardsInDeck) {
          //  ImageView imvCard = controller.createCardImageView(card);
          //  deckPane.getChildren().add(imvCard);
        }

        for (int i = 0; i < cardsInBoard.size(); i++) {
            List<Card> column = cardsInBoard.get(i);
            Pane pane = columnPanes.get(i);
            pane.getChildren().clear();
            for (int j = 0; j < column.size(); j++) {
          //      ImageView imv = controller.createCardImageView(column.get(j));
          //      imv.setLayoutY(j * OFFSET_Y);
          //      pane.getChildren().add(imv);
            }
        }
    }

    public TranslateTransition moveCardToPane(Pane originPane, Pane targetPane,
                                              ImageView cardView, boolean stack, AnchorPane animationLayer) {
        Bounds originBounds = originPane.localToScene(cardView.getBoundsInLocal());
        Bounds targetBounds = targetPane.localToScene(targetPane.getBoundsInLocal());

        double startX = originBounds.getMinX();
        double startY = originBounds.getMinY();
        double endX = targetBounds.getMinX();
        double endY = targetBounds.getMinY();

        // Convert scene coordinates to local coordinates of animationLayer
        startX = animationLayer.sceneToLocal(startX, startY).getX();
        startY = animationLayer.sceneToLocal(startX, startY).getY();
        endX = animationLayer.sceneToLocal(endX, endY).getX();
        endY = animationLayer.sceneToLocal(endX, endY).getY();

        cardView.setLayoutX(startX);
        cardView.setLayoutY(startY);
        animationLayer.getChildren().add(cardView);

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), cardView);
        transition.setToX(endX - startX);
        transition.setToY(endY - startY);
        return transition;
    }

    public ScaleTransition flipCard(ImageView cardView, Card card, GameController controller) {
        ScaleTransition scale1 = new ScaleTransition(Duration.millis(100), cardView);
        scale1.setToX(0);
      //  scale1.setOnFinished(evt -> cardView.setImage(controller.loadCardImage(card, true)));

        ScaleTransition scale2 = new ScaleTransition(Duration.millis(100), cardView);
        scale2.setToX(1);

        SequentialTransition seq = new SequentialTransition(scale1, scale2);
        return new ScaleTransition(Duration.millis(200), cardView); // returning placeholder
    }

    public void playVictoryAnimation(List<Pane> foundationPanes, AnchorPane animationLayer) {
        // Aquí podrías hacer brillar las fundaciones o mostrar una animación especial
        // Dependiendo si animationLayer es null (como en GameController) o no
        for (Pane pane : foundationPanes) {
            pane.setStyle("-fx-border-color: gold; -fx-border-width: 3px;");
        }
    }

    public void defeatAnimation(List<Pane> columnPanes, Pane deckPane, List<Pane> foundationPanes) {
        // Puedes hacer que las pilas se pongan grises o cambien de opacidad
        for (Pane pane : columnPanes) pane.setOpacity(0.5);
        for (Pane pane : foundationPanes) pane.setOpacity(0.5);
        if (deckPane != null) deckPane.setOpacity(0.5);
    }
}
