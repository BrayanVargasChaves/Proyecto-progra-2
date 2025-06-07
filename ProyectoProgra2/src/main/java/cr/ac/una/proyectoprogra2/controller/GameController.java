package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Card;
import cr.ac.una.proyectoprogra2.service.GameAnimationsService;
import cr.ac.una.proyectoprogra2.util.CardFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class GameController extends Controller implements Initializable {

    @FXML private AnchorPane animationLayer;

    @FXML private Pane pnFila1, pnFila2, pnFila3, pnFila4, pnFila5,
                      pnFila6, pnFila7, pnFila8, pnFila9, pnFila10;

    @FXML private Pane pnBaraja;

    private List<Pane> columnPanes;
    private List<Pane> foundationPanes;
    private List<List<Card>> cardsInBoard;
    private List<Card> cardsInDeck;
    private List<List<Card>> cardsInFoundations;
    private Map<Integer, Card> imageCache = new HashMap<>();
    private Integer selectedCardValue;
    private int selectedColumnIndex; //columna seleccionada para mover con click
    private int selectedCardIndex; //indice seleccionado para mover con click
    private Map<String, Integer> cardValues = new HashMap<>();
    private final double OFFSET_Y = 30;
    private GameAnimationsService animationService;
    private BooleanProperty gameWon;
    private BooleanProperty gameFailed;
    private int hintSourceColumnIndex;
    private int hintCardIndex;
    private int hintTargetColumnIndex;
    private int lastHintTargetColumnIndex;;

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
    @FXML
    private MFXButton btnDeck;
    @FXML
    private Rectangle recHintStart;
    @FXML
    private Rectangle recHintEnd;
    @FXML
    private Rectangle recSelectedCard;

    public void initialize() {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recHintEnd.setVisible(false);
        recHintStart.setVisible(false);
        recSelectedCard.setVisible(false);
        lastHintTargetColumnIndex = -1;
        gameWon = new SimpleBooleanProperty(false);
        gameFailed = new SimpleBooleanProperty(false);
        attachGameWonListener();
        attachGameFailedListener();
        fillCardsValues();
        animationService = new GameAnimationsService();
        CardFactory.getInstance().createDistribution(1);
        cardsInBoard = CardFactory.getInstance().getCardsInColumns();
        cardsInDeck = CardFactory.getInstance().getCardsInDeck();
        cardsInFoundations = new ArrayList<>();
        columnPanes = Arrays.asList(
                pnFila1, pnFila2, pnFila3, pnFila4, pnFila5,
                pnFila6, pnFila7, pnFila8, pnFila9, pnFila10
        );
         foundationPanes = Arrays.asList(
                pnPila1, pnPila2, pnPila3, pnPila4, pnPila5,
                 pnPila6, pnPila7, pnPila8
         );
         renderDeck();
        // En la animacion inicial, se hacen los renders necesarios
        animationService.initialAnimation(cardsInBoard, cardsInDeck, columnPanes, pnBaraja, this);
    }
    
    private void renderColumns() {
         // Se dejan invisibles los rectagulos de las pistas porque ya se hizo un movimiento
        recHintEnd.setVisible(false);
        recHintStart.setVisible(false);
        for (int columnIndex = 0; columnIndex < cardsInBoard.size(); columnIndex++) {
            Pane columnPane = columnPanes.get(columnIndex);
            columnPane.getChildren().clear();
            List<Card> column = cardsInBoard.get(columnIndex);
            for (int cardIndex = 0; cardIndex < column.size(); cardIndex++) {
                ImageView cardImageView = createCardImageView(column.get(cardIndex));
                cardImageView.setLayoutY(cardIndex * OFFSET_Y);
                columnPane.getChildren().add(cardImageView);
            }
        }
    }

     private void renderFoundations() {
        for (int foundation = 0; foundation < cardsInFoundations.size(); foundation++) {
            Pane foundationPane = foundationPanes.get(foundation);
            foundationPane.getChildren().clear();
            List<Card> cards = cardsInFoundations.get(foundation);
            for (int i = 0; i < cards.size(); i++) {
                ImageView imvCard = createCardImageView(cards.get(i));
                foundationPane.getChildren().add(imvCard);
            }
        }
    }
    
    private void assignDragAndClickEventsToEachCard() {
        // Se recorre cada columna
        for (int col = 0; col < columnPanes.size(); col++) {
            Pane pane = columnPanes.get(col);
            List<Card> cards = cardsInBoard.get(col);
            // Se recorre cada carta de cada columna
            for (int i = 0; i < cards.size(); i++) {
                ImageView imv = (ImageView) pane.getChildren().get(i);
                Card card = cards.get(i);
                final int columnIndex = col;
                final int cardIndex = i;
                // Se llama el metodo para asignar cada evento
                addDragAndClickHandlers(imv, card, columnIndex, cardIndex);
            }
        }
    }

    private void addDragAndClickHandlers(ImageView imv, Card card, int col, int cardIndex) {
        final boolean[] dragging = {false};
        // Offset entre la posición del cursor y la esquina superior de la imagen
        final double[] dragOffset = new double[2];
        final double[] originalPos = new double[2];

        // Primer evento: Cuando presiona el ratón sobre la carta
        imv.setOnMousePressed(e -> {
            // Guardamos posición original para poder devolver la carta
            originalPos[0] = imv.getLayoutX();
            originalPos[1] = imv.getLayoutY();
            // Calculamos offset para arrastrar la carta
            dragOffset[0] = e.getSceneX() - originalPos[0];
            dragOffset[1] = e.getSceneY() - originalPos[1];
            dragging[0] = false;
        });

        // Segundo evento: Mientras mantiene presionado y mueve el ratón
        // NOTA: EVENT INCOMPLETO
        imv.setOnMouseDragged(e -> {
            if (!canMoveSequence(col, cardIndex)) {
                System.out.println("Movimiento invalido: la secuencia no esta ordenada");
                selectedCardValue = null;
                return;
            }
            dragging[0] = true;
            Pane currentPane = columnPanes.get(col);
            // Se coloca el pane actual para que las cartas al moverse, esten al frente

            currentPane.toFront();
            recHintEnd.toFront();
            recHintStart.toFront();
            enableCardFollowMouse(e, dragOffset, col, cardIndex);
        });

        // Tercer evento: Al soltar el ratón o soltar arrastre o ejecutar click
        imv.setOnMouseReleased(e -> {
            //si el dragging es true, entonces restaura la posicion inicial de la carta
            if (dragging[0]) {
                // Se revisa si el usuario puso cartas en otra columna
                Pane paneTarget = findIntersectingPane(imv, col, cardIndex);
                if (paneTarget != null) {
                    int targetColumn = columnPanes.indexOf(paneTarget);
                    transferCardsToColumn(targetColumn, col, cardIndex);
                } else {
                    // no intersecta ninguna columna, entonces se restauran posiciones iniciales
 //                   restoreInitialPositions(originalPos[0], originalPos[1], col, cardIndex);
                }
                //agregar la logica para cuando se intersecta una carta
                //tambien se ocupa la logica para seleccionar una cadena de cartas
            } else {
                // Aquí el click rápido
                handleCardClick(card, col, cardIndex);
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

    @FXML
    private void onActionBtnDeck(ActionEvent event) {
    }

    private void attachGameWonListener() {
        gameWon.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                animationService.playVictoryAnimation(foundationPanes, animationLayer);
            }
        });
    }

    private void attachGameFailedListener() {
        gameFailed.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                animationService.defeatAnimation(columnPanes, pnBaraja, foundationPanes);
            }
        });
    }

    private void fillCardsValues() {
        cardValues.put("A", 1);
        cardValues.put("2", 2);
        cardValues.put("3", 3);
        cardValues.put("4", 4);
        cardValues.put("5", 5);
        cardValues.put("6", 6);
        cardValues.put("7", 7);
        cardValues.put("8", 8);
        cardValues.put("9", 9);
        cardValues.put("10", 10);
        cardValues.put("J", 11);
        cardValues.put("Q", 12);
        cardValues.put("K", 13);
    }

    private void renderDeck() {
        pnBaraja.getChildren().clear();
        for (Card card : cardsInDeck) {
            ImageView imvCard = createCardImageView(card);
            pnBaraja.getChildren().add(imvCard);
        }
    }

    private void handleCardClick(Card card, int col, int cardIndex) {
    }

    private void transferCardsToColumn(int targetColumn, int col, int cardIndex) {
    }

    private Pane findIntersectingPane(ImageView imv, int col, int cardIndex) {
        return null;
    }

    private void enableCardFollowMouse(MouseEvent e, double[] dragOffset, int col, int cardIndex) {
    }

    private boolean canMoveSequence(int col, int cardIndex) {
        return false;
    }

    
}