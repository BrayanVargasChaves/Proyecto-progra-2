package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Card;
import cr.ac.una.proyectoprogra2.service.GameAnimationsService;
import cr.ac.una.proyectoprogra2.util.CardFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class GameController {

    @FXML private AnchorPane animationLayer;

    @FXML private Pane pnFila1, pnFila2, pnFila3, pnFila4, pnFila5,
                      pnFila6, pnFila7, pnFila8, pnFila9, pnFila10;

    @FXML private Pane pnBaraja;

    private final double OFFSET_Y = 30;
    private List<Pane> columnPanes;
    private List<List<Card>> cardsInBoard;
    private List<Card> cardsInDeck;

    private final GameAnimationsService animationService = new GameAnimationsService();
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnTerminarMasTarde;
    @FXML
    private Label lbPuntuacion;
    @FXML
    private Label lbTime;
    @FXML
    private Pane pnPila1;
    @FXML
    private Pane pnPila2;
    @FXML
    private Pane pnPila3;
    @FXML
    private Pane pnPila4;
    @FXML
    private Pane pnPila5;
    @FXML
    private Pane pnPila6;
    @FXML
    private Pane pnPila7;
    @FXML
    private Pane pnPila8;
    @FXML
    private MFXButton btnRendirce;
    @FXML
    private MFXButton btnPista;

    public void initialize() {
        columnPanes = Arrays.asList(pnFila1, pnFila2, pnFila3, pnFila4, pnFila5,
                                    pnFila6, pnFila7, pnFila8, pnFila9, pnFila10);

        CardFactory.getInstance().createDistribution(1);
        cardsInBoard = CardFactory.getInstance().getCardsInColumns();
        cardsInDeck = CardFactory.getInstance().getCardsInDeck();

        renderColumns();
        animationLayer.toFront();
    }

    private void renderColumns() {
        for (int col = 0; col < columnPanes.size(); col++) {
            Pane pane = columnPanes.get(col);
            List<Card> cards = cardsInBoard.get(col);

            pane.getChildren().clear();

            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                ImageView imv = createCardImageView(card);
                imv.setLayoutY(i * OFFSET_Y);
                pane.getChildren().add(imv);
            }
        }

        assignDragAndClickEventsToEachCard();
    }

    private void assignDragAndClickEventsToEachCard() {
        for (int col = 0; col < columnPanes.size(); col++) {
            Pane pane = columnPanes.get(col);
            List<Card> cards = cardsInBoard.get(col);

            for (int i = 0; i < cards.size(); i++) {
                ImageView imv = (ImageView) pane.getChildren().get(i);
                Card card = cards.get(i);
                addDragAndClickHandlers(imv, card, col, i);
            }
        }
    }

    private void addDragAndClickHandlers(ImageView imv, Card card, int col, int cardIndex) {
        final double[] dragOffset = new double[2];
        final boolean[] dragging = {false};

        imv.setOnMousePressed(e -> {
            dragOffset[0] = e.getSceneX() - imv.getLayoutX();
            dragOffset[1] = e.getSceneY() - imv.getLayoutY();
            dragging[0] = false;
        });

        imv.setOnMouseDragged(e -> {
            if (!card.isIsFlip()) return;
            dragging[0] = true;
            followMouse(e, dragOffset, col, cardIndex);
        });

        imv.setOnMouseReleased(e -> {
            if (!dragging[0]) return;
            int target = findTargetColumnIndex(imv);
            if (target != -1 && target != col) {
                moveCardSequence(col, target, cardIndex);
            } else {
                resetCards(col, cardIndex);
            }
        });
    }

    private void followMouse(MouseEvent e, double[] dragOffset, int col, int fromIndex) {
        List<Card> column = cardsInBoard.get(col);
        for (int i = fromIndex; i < column.size(); i++) {
            ImageView card = (ImageView) columnPanes.get(col).getChildren().get(i);
            if (!animationLayer.getChildren().contains(card)) {
                Point2D scenePos = card.localToScene(0, 0);
                Point2D local = animationLayer.sceneToLocal(scenePos);
                card.setLayoutX(local.getX());
                card.setLayoutY(local.getY());
                animationLayer.getChildren().add(card);
            }
            double x = e.getSceneX() - dragOffset[0];
            double y = e.getSceneY() - dragOffset[1] + (i - fromIndex) * OFFSET_Y;
            card.setTranslateX(x - card.getLayoutX());
            card.setTranslateY(y - card.getLayoutY());
            card.toFront();
        }
    }

    private void resetCards(int col, int fromIndex) {
        List<Card> column = cardsInBoard.get(col);
        Pane pane = columnPanes.get(col);
        for (int i = fromIndex; i < column.size(); i++) {
            ImageView imv = (ImageView) animationLayer.getChildren().remove(0);
            imv.setTranslateX(0);
            imv.setTranslateY(0);
            imv.setLayoutX(0);
            imv.setLayoutY(i * OFFSET_Y);
            pane.getChildren().add(imv);
        }
    }

    private int findTargetColumnIndex(ImageView draggedCard) {
        for (int i = 0; i < columnPanes.size(); i++) {
            Pane pane = columnPanes.get(i);
            if (pane.getBoundsInParent().intersects(draggedCard.getBoundsInParent())) {
                return i;
            }
        }
        return -1;
    }

    private void moveCardSequence(int fromCol, int toCol, int cardIndex) {
        List<Card> from = cardsInBoard.get(fromCol);
        List<Card> to = cardsInBoard.get(toCol);

        List<Card> moving = new ArrayList<>(from.subList(cardIndex, from.size()));
        from.subList(cardIndex, from.size()).clear();
        to.addAll(moving);

        renderColumns();
    }

    public ImageView createCardImageView(Card card) {
        ImageView imv = new ImageView(loadCardImage(card, card.isIsFlip()));
        imv.setFitWidth(80);
        imv.setPreserveRatio(true);
        return imv;
    }

    public javafx.scene.image.Image loadCardImage(Card card, boolean isFlip) {
        String cardName = card.getSuit().toLowerCase() + "_" + card.getValue() + ".png";
        String backName = "back_blue.png";
        String url = "/cards/" + (isFlip ? cardName : backName);

        java.net.URL imageUrl = getClass().getResource(url);
        if (imageUrl == null) {
            System.err.println("No se pudo cargar la imagen: " + url);
            return new javafx.scene.image.Image("https://via.placeholder.com/80x120.png?text=?");
        }

        return new javafx.scene.image.Image(imageUrl.toExternalForm());
    }

    @FXML
    private void onActionTerminarMasTarde(ActionEvent event) {
    }

    @FXML
    private void onActionBtnRendirce(ActionEvent event) {
    }

    @FXML
    private void onActionBtnPista(ActionEvent event) {
    }
}