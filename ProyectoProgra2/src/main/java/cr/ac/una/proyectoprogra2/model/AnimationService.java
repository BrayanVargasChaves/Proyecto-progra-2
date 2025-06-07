package cr.ac.una.spider.solitaire.services;

import cr.ac.una.spider.solitaire.controller.BoardViewController;
import cr.ac.una.spider.solitaire.model.Card;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

/**
 * Servicio que agrupa las animaciones del juego (movimiento de cartas, volteo, victoria y derrota).
 */
public class AnimationService {

    /**
     * Mueve una carta de un Pane origen a un Pane destino usando TranslateTransition.
     */
    public TranslateTransition moveCardToPane(Pane fromPane, Pane toPane, ImageView cardView, boolean stack, AnchorPane animationLayer) {
        Bounds boundsInScene = cardView.localToScene(cardView.getBoundsInLocal());
        Point2D start = animationLayer.sceneToLocal(boundsInScene.getMinX(), boundsInScene.getMinY());
        fromPane.getChildren().remove(cardView);
        animationLayer.getChildren().add(cardView);
        cardView.setLayoutX(start.getX());
        cardView.setLayoutY(start.getY());

        Bounds targetBoundsInScene = toPane.localToScene(toPane.getBoundsInLocal());
        Point2D end = animationLayer.sceneToLocal(targetBoundsInScene.getMinX(), targetBoundsInScene.getMinY());

        TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardView);
        tt.setToX(end.getX() - start.getX());
        tt.setToY(end.getY() - start.getY());
        return tt;
    }

    /**
     * Realiza la animación de volteo de carta (simula flip con escalado en X).
     */
    public ScaleTransition flipCard(ImageView cardView, Card modelCard, BoardViewController controller) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), cardView);
        st.setFromX(1);
        st.setToX(0);
        st.setOnFinished(e -> {
            modelCard.setBocaArriba(modelCard.isIsFlip() ? 0 : 1);
            cardView.setImage(controller.loadCardImage(modelCard, modelCard.isIsFlip()));
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), cardView);
            st2.setFromX(0);
            st2.setToX(1);
            st2.play();
        });
        return st;
    }

    /**
     * Animación inicial al repartir cartas.
     */
    public void initialAnimation(List<List<Card>> columns, List<Card> deck, List<Pane> columnPanes, Pane deckPane, BoardViewController controller) {
        AnchorPane layer = controller.getAnimationLayer();
        SequentialTransition seq = new SequentialTransition();
        int deckSize = deck.size() - 1;
        for (int col = 0; col < columnPanes.size(); col++) {
            final int targetColumn = col;
            ImageView cardView = (ImageView) deckPane.getChildren().get(deckSize - col);
            Card card = deck.get(deckSize - col);
            TranslateTransition tt = moveCardToPane(deckPane, columnPanes.get(col), cardView, true, layer);
            ScaleTransition flip = flipCard(cardView, card, controller);
            tt.setOnFinished(evt -> {
                columns.get(targetColumn).add(card);
                deck.remove(deckSize - targetColumn);
            });
            seq.getChildren().add(tt);
            seq.getChildren().add(flip);
        }
        seq.setOnFinished(evt -> {
            layer.getChildren().removeIf(node -> node instanceof ImageView);
            controller.renderDeck();
            controller.renderColumns();
            controller.assignDragAndClickEventsToEachCard();
            controller.updateHintPositions();
        });
        seq.play();
    }

    /**
     * Reproduce animación de victoria (destellos en las fundaciones).
     */
    public void playVictoryAnimation(List<Pane> foundationPanes, AnchorPane animationLayer) {
        for (Pane pane : foundationPanes) {
            for (Node node : pane.getChildren()) {
                if (node instanceof ImageView) {
                    ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
                    st.setFromX(1);
                    st.setFromY(1);
                    st.setToX(1.2);
                    st.setToY(1.2);
                    st.setAutoReverse(true);
                    st.setCycleCount(4);
                    st.play();
                }
            }
        }
    }

    /**
     * Reproduce animación de derrota (shake en las columnas).
     */
    public void defeatAnimation(List<Pane> columnPanes, Pane deckPane, List<Pane> foundationPanes) {
        for (Pane pane : columnPanes) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(50), pane);
            tt.setFromX(0);
            tt.setByX(10);
            tt.setAutoReverse(true);
            tt.setCycleCount(6);
            tt.play();
        }
    }
}
