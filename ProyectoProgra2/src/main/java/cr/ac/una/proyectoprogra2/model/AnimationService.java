package cr.ac.una.proyectoprogra2.model;

import cr.ac.una.proyectoprogra2.controller.GameController;
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

public class AnimationService {

    private static final double OFFSET_Y = 30;

    public TranslateTransition moveCardToPane(
            Node fromNode,
            Pane toPane,
            ImageView cardView,
            boolean stack,
            AnchorPane animationLayer
    ) {
        Bounds boundsInScene = cardView.localToScene(cardView.getBoundsInLocal());
        Point2D start = animationLayer.sceneToLocal(boundsInScene.getMinX(), boundsInScene.getMinY());

        if (fromNode instanceof Pane) {
            ((Pane) fromNode).getChildren().remove(cardView);
        } else {
            ((Pane) fromNode.getParent()).getChildren().remove(cardView);
        }

        animationLayer.getChildren().add(cardView);
        cardView.setLayoutX(start.getX());
        cardView.setLayoutY(start.getY());

        Bounds targetBounds = toPane.localToScene(toPane.getBoundsInLocal());
        Point2D end = animationLayer.sceneToLocal(targetBounds.getMinX(), targetBounds.getMinY());

        TranslateTransition tt = new TranslateTransition(Duration.millis(300), cardView);
        tt.setToX(end.getX() - start.getX());
        tt.setToY(end.getY() - start.getY());
        return tt;
    }

    public SequentialTransition flipCard(
            ImageView cardView,
            Card modelCard,
            GameController controller
    ) {
        ScaleTransition shrink = new ScaleTransition(Duration.millis(150), cardView);
        shrink.setFromX(1);
        shrink.setToX(0);

        ScaleTransition expand = new ScaleTransition(Duration.millis(150), cardView);
        expand.setFromX(0);
        expand.setToX(1);

        shrink.setOnFinished(e -> {
            modelCard.setBocaArriba(modelCard.isIsFlip() ? 0 : 1);
            cardView.setImage(controller.loadCardImage(modelCard, modelCard.isIsFlip()));
        });

        return new SequentialTransition(shrink, expand);
    }

   public void initialAnimation(
        List<List<Card>> columns,
        List<Card> deck,
        List<ImageView> deckCardViews,
        List<Pane> columnPanes,
        Node deckNode,
        GameController controller
) {
    AnchorPane layer = controller.getAnimationLayer();
    SequentialTransition seq = new SequentialTransition();
    int deckSize = deck.size() - 1;

    for (int col = 0; col < columnPanes.size(); col++) {
        final int target = col;
        int deckIndex = deckSize - col;

        if (deckIndex < 0 || deckIndex >= deckCardViews.size()) {
            System.err.println("Error: deckIndex fuera de rango: " + deckIndex);
            continue;
        }

        ImageView cardView = deckCardViews.get(deckIndex);
        Card card = deck.get(deckIndex);

        // Detectar si es la última carta de la columna
        boolean isFinalCard = (col == 4 || col == 5);

        if (isFinalCard) {
            // IMPORTANTE: cambiar estado del modelo y la imagen ANTES de mover
            card.setBocaArriba(1);
            cardView.setImage(controller.loadCardImage(card, true));
        } else {
            card.setBocaArriba(0);  // asegurar que no estén boca arriba antes del flip
            cardView.setImage(controller.loadCardImage(card, false));
        }

        TranslateTransition tt = moveCardToPane(deckNode, columnPanes.get(col), cardView, true, layer);

        SequentialTransition flip;
        if (isFinalCard) {
            flip = new SequentialTransition(); // No animación
        } else {
            flip = flipCard(cardView, card, controller);
        }

        tt.setOnFinished(evt -> {
            columns.get(target).add(card);
            deck.remove(deckIndex);
        });

        seq.getChildren().addAll(tt, flip);
    }

    seq.setOnFinished(evt -> {
        layer.getChildren().removeIf(n -> n instanceof ImageView);
        controller.renderColumns();
        controller.assignDragAndClickEventsToEachCard();
        controller.updateHintPositions();
    });

    seq.play();
}

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

    public void defeatAnimation(List<Pane> columnPanes, Node deckPane, List<Pane> foundationPanes) {
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
