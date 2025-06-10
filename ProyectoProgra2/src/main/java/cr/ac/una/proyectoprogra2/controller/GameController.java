package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Card;
import cr.ac.una.proyectoprogra2.util.CardFactory;
import cr.ac.una.proyectoprogra2.model.AnimationService;
import cr.ac.una.proyectoprogra2.model.Sonidos;
import cr.ac.una.proyectoprogra2.util.AppContext;
import cr.ac.una.proyectoprogra2.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController extends Controller implements Initializable {

    @FXML
    private AnchorPane animationLayer;
    @FXML
    private MFXButton btnDeck;
    @FXML
    private Label lbPuntuacion;
    @FXML
    private Label lbTime;
    @FXML
    private Pane pnFila1;
    @FXML
    private Pane pnFila2;
    @FXML
    private Pane pnFila3;
    @FXML
    private Pane pnFila4;
    @FXML
    private Pane pnFila5;
    @FXML
    private Pane pnFila6;
    @FXML
    private Pane pnFila7;
    @FXML
    private Pane pnFila8;
    @FXML
    private Pane pnFila9;
    @FXML
    private Pane pnFila10;

    private Pane pnDeck;
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
    private Rectangle recHintEnd;
    @FXML
    private Rectangle recHintStart;
    @FXML
    private Rectangle recSelectedCard;

    private List<Pane> columnPanes;
    private List<Pane> foundationPanes;
    private List<List<Card>> cardsInBoard;
    private List<Card> cardsInDeck;
    private List<List<Card>> cardsInFoundations;
    private Integer selectedCardValue;
    private int selectedColumnIndex; 
    private int selectedCardIndex; 
    private Map<String, Integer> cardValues = new HashMap<>();
    private final double OFFSET_Y = 30;
    private AnimationService animationService;
    private BooleanProperty gameWon;
    private BooleanProperty gameFailed;
    private int hintSourceColumnIndex;
    private int hintCardIndex;
    private int hintTargetColumnIndex;
    private int lastHintTargetColumnIndex;
    private List<ImageView> deckCardViews = new ArrayList<>();
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnTerminarMasTarde;
    @FXML
    private MFXButton btnRendirce;
    @FXML
    private MFXButton btnPista;
    @FXML
    private Pane pnBaraja;
    @FXML
    private AnchorPane GamePane;
    @FXML
    private ImageView imgFondo;
    private boolean cronometroActivo = false;
    private Thread cronometroThread;
    int puntosIniciales = 500;
    int puntuacionActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        puntuacionActual = puntosIniciales;
        iniciarCronometro();
        lbPuntuacion.setText(String.valueOf(puntosIniciales));    
        Sonidos.asignarSonido(btnTerminarMasTarde);
        Sonidos.asignarSonido(btnRendirce);
        Sonidos.asignarSonidoPista(btnPista);
        Sonidos.asignarSonidoDeck(btnDeck);
        Sonidos.reproducirLoop("sonidoAmbiente.mp3");
        
        recHintStart.setVisible(false);
        recHintEnd.setVisible(false);
        recSelectedCard.setVisible(false);

        gameWon = new SimpleBooleanProperty(false);
        gameFailed = new SimpleBooleanProperty(false);
        gameWonListener();
        gameFailedListener();

        asignarValoresCartas();
        animationService = new AnimationService();

        String diff = ((String) AppContext.getInstance().get("dificultad"));
        CardFactory.getInstance().createDistribution(diff);
        cardsInBoard = CardFactory.getInstance().getCardsInColumns();
        cardsInDeck = CardFactory.getInstance().getCardsInDeck();
        cardsInFoundations = new ArrayList<>();

        columnPanes = Arrays.asList(
                pnFila1, pnFila2, pnFila3, pnFila4, pnFila5,
                pnFila6, pnFila7, pnFila8, pnFila9, pnFila10
        );
        foundationPanes = Arrays.asList(
                pnPila1, pnPila2, pnPila3, pnPila4,
                pnPila5, pnPila6, pnPila7, pnPila8
        ); 
        Pane deckParent = (Pane) btnDeck.getParent(); 
        for (Card card : cardsInDeck) {
            ImageView imageView = createCardImageView(card); 
            imageView.setVisible(false);
            deckParent.getChildren().add(imageView);
        }
        renderizarColumnas();
        GamePane.widthProperty().addListener((obs, oldVal, newVal) -> redistribuirSeparacionPanes());
        redistribuirSeparacionPanes();
        redistribuirSeparacionPanes();
        animationService.initialAnimation(
                cardsInBoard,
                cardsInDeck,
                deckCardViews,
                columnPanes,
                btnDeck,
                this
        );
        
                imgFondo.fitHeightProperty().bind(root.heightProperty());
        imgFondo.fitWidthProperty().bind(root.widthProperty()); 
        root.setMaxHeight(640);
        root.setMaxWidth(400);
    }
    
   private void iniciarCronometro() {
    cronometroActivo = true;
    cronometroThread = new Thread(() -> {
        int segundosTranscurridos = 0;
        while (cronometroActivo) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            segundosTranscurridos++;
            int minutos = segundosTranscurridos / 60;
            int segundos = segundosTranscurridos % 60;
            Platform.runLater(() -> {
                lbTime.setText(String.format("%02d:%02d", minutos, segundos));
            });
        }
    });
    cronometroThread.setDaemon(true);
    cronometroThread.start();
}

    private void redistribuirSeparacionPanes() {
        double totalWidth = GamePane.getWidth();
        int cantidadPanes = columnPanes.size();

        double paneWidth = columnPanes.get(0).getPrefWidth(); 
        double espacioTotalDisponible = totalWidth - (cantidadPanes * paneWidth);

        if (espacioTotalDisponible < 0) {
            espacioTotalDisponible = 0;
        }

        double espacioEntrePanes = espacioTotalDisponible / (cantidadPanes + 1);

        for (int i = 0; i < cantidadPanes; i++) {
            Pane pane = columnPanes.get(i);
            double layoutX = espacioEntrePanes + i * (paneWidth + espacioEntrePanes);
            pane.setLayoutX(layoutX);
        }
    }

    @FXML
    private void onActionTerminarMasTarde(ActionEvent e) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnDeck(ActionEvent e) {
        boolean hayColumnaVacia = cardsInBoard.stream().anyMatch(List::isEmpty);
        if (hayColumnaVacia) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Movimiento inválido");
        alerta.setHeaderText(null);
        alerta.setContentText("No se puede repartir cartas mientras haya columnas vacías.");
        alerta.showAndWait();
        return;
        }
        if (cardsInDeck.isEmpty()) {
            return;
        }

        double offset = 15; 
        SequentialTransition seq = new SequentialTransition();

        Bounds deckBounds = btnDeck.localToScene(btnDeck.getBoundsInLocal());
        double startX = deckBounds.getMinX();
        double startY = deckBounds.getMinY();

        for (int col = 0; col < columnPanes.size(); col++) {
            final int target = col;
            int deckIndex = cardsInDeck.size() - 1 - col;
            if (deckIndex < 0) {
                continue;
            }

            Card card = cardsInDeck.get(deckIndex);
            ImageView iv = createCardImageView(card);

            iv.setLayoutX(startX);
            iv.setLayoutY(startY);
            animationLayer.getChildren().add(iv);

            Pane targetPane = columnPanes.get(col);
            ImageView lastCardView = null;
            if (!targetPane.getChildren().isEmpty()) {
                lastCardView = targetPane.getChildren().stream()
                        .filter(n -> n instanceof ImageView)
                        .map(n -> (ImageView) n)
                        .max((iv1, iv2) -> Double.compare(iv1.getLayoutY(), iv2.getLayoutY()))
                        .orElse(null);
            }
            double destX, destY;
            if (lastCardView != null) {
                Bounds lastCardBounds = lastCardView.localToScene(lastCardView.getBoundsInLocal());
                destX = lastCardBounds.getMinX();
                destY = lastCardBounds.getMinY() + offset;
            } else {
                Bounds paneBounds = targetPane.localToScene(targetPane.getBoundsInLocal());
                destX = paneBounds.getMinX();
                destY = paneBounds.getMinY();
            }
            Point2D startInLayer = animationLayer.sceneToLocal(startX, startY);
            Point2D destInLayer = animationLayer.sceneToLocal(destX, destY);
            iv.setLayoutX(startInLayer.getX());
            iv.setLayoutY(startInLayer.getY());
            TranslateTransition tt = new TranslateTransition(Duration.millis(500), iv);
            tt.setFromX(0);
            tt.setFromY(0);
            tt.setToX(destInLayer.getX() - startInLayer.getX());
            tt.setToY(destInLayer.getY() - startInLayer.getY());
            SequentialTransition flip = animationService.flipCard(iv, card, this);

            tt.setOnFinished(evt -> {
                cardsInBoard.get(target).add(card);
                cardsInDeck.remove(deckIndex);
            });

            seq.getChildren().addAll(tt, flip);
        }

        seq.setOnFinished(evt -> {
            animationLayer.getChildren().removeIf(n -> n instanceof ImageView);
            renderizarColumnas();
            assignDragAndClickEventsToEachCard();
            actualizarPosicionPista();
        });

        seq.play();
    }

    @FXML
    private void onActionBtnRendirce(ActionEvent e) throws InterruptedException {
        gameFailed.set(true);
        Thread.sleep(2000);
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();

    }

    @FXML
    private void onActionBtnPista(ActionEvent e) {
        actualizarPosicionPista();
         onActionLbHint(e);
    }

    private void gameWonListener() {
        gameWon.addListener((o, oldVal, newVal) -> {
        if (newVal) {            
            animationService.playVictoryAnimation(foundationPanes, animationLayer);           
            new Thread(() -> {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> {                  
                    FlowController.getInstance().goViewInWindow("FinDelJuegoView");                  
                    ((Stage) root.getScene().getWindow()).close();
                });
            }).start();
        }
    });
    }

    private void gameFailedListener() {
        gameFailed.addListener((o, oldVal, newVal) -> {
        if (newVal) {
            animationService.defeatAnimation(columnPanes, btnDeck, foundationPanes);
            new Thread(() -> {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> {
                    FlowController.getInstance().goViewInWindow("DerrotaView");
                    Stage currentStage = (Stage) root.getScene().getWindow();
                    currentStage.close();
                });
            }).start();
        }
    });
    }

    private void asignarValoresCartas() {
        cardValues = new HashMap<>();
        List<String> ranks = Arrays.asList(
                "A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K"
        );
        for (int i = 0; i < ranks.size(); i++) {
            cardValues.put(ranks.get(i), i + 1);
        }
    }

    public void renderizarColumnas() {
        recHintStart.setVisible(false);
        recHintEnd.setVisible(false);
        for (int i = 0; i < columnPanes.size(); i++) {
            Pane p = columnPanes.get(i);
            p.getChildren().clear();
            List<Card> col = cardsInBoard.get(i);
            for (int j = 0; j < col.size(); j++) {
                ImageView iv = createCardImageView(col.get(j));
                iv.setLayoutY(j * OFFSET_Y);
                p.getChildren().add(iv);
            }
        }
    }

    void onActionLbHint(ActionEvent event) {
        if (hintSourceColumnIndex == -1 || hintCardIndex == -1 || hintTargetColumnIndex == -1) {
            recHintStart.setVisible(true);
            recHintEnd.setVisible(false); 

            Bounds deckBoundsInScene = pnDeck.localToScene(pnDeck.getBoundsInLocal());            
            Bounds deckBoundsInLayer = animationLayer.sceneToLocal(deckBoundsInScene);

            recHintStart.setLayoutX(deckBoundsInLayer.getMinX());
            recHintStart.setLayoutY(deckBoundsInLayer.getMinY());
            recHintStart.setWidth(deckBoundsInLayer.getWidth());
            recHintStart.setHeight(deckBoundsInLayer.getHeight());

            return; 
        }

        if (hintSourceColumnIndex < 0 || hintSourceColumnIndex >= columnPanes.size()) {
            System.err.println("Error: hintSourceColumnIndex inválido: " + hintSourceColumnIndex);
            return;
        }

        if (hintTargetColumnIndex < 0 || hintTargetColumnIndex >= columnPanes.size()) {
            System.err.println("Error: hintTargetColumnIndex inválido: " + hintTargetColumnIndex);
            return;
        }

        Pane sourcePane = columnPanes.get(hintSourceColumnIndex);
        if (sourcePane.getChildren().isEmpty()) {
            System.err.println("Error: La columna fuente está vacía");
            return;
        }

        if (hintCardIndex < 0 || hintCardIndex >= sourcePane.getChildren().size()) {
            System.err.println("Error: hintCardIndex inválido: " + hintCardIndex
                    + ", tamaño de columna: " + sourcePane.getChildren().size());
            return;
        }

        recHintEnd.setVisible(true);
        recHintStart.setVisible(true);

        ImageView startView = (ImageView) sourcePane.getChildren().get(hintCardIndex);

        Bounds startBoundsInScene = startView.localToScene(startView.getBoundsInLocal());
        Bounds startBoundsInLayer = animationLayer.sceneToLocal(startBoundsInScene);
        recHintStart.setLayoutX(startBoundsInLayer.getMinX());
        recHintStart.setLayoutY(startBoundsInLayer.getMinY());
        recHintStart.setWidth(startBoundsInLayer.getWidth());
        recHintStart.setHeight(startBoundsInLayer.getHeight() + OFFSET_Y * (sourcePane.getChildren().size() - hintCardIndex - 1));

        Pane targetPane = columnPanes.get(hintTargetColumnIndex);
        if (targetPane.getChildren().isEmpty()) {
            double cardW = startBoundsInLayer.getWidth();
            double cardH = startBoundsInLayer.getHeight();
            Point2D topLeftScene = targetPane.localToScene(0, 0);
            Point2D topLeftInLayer = animationLayer.sceneToLocal(topLeftScene);
            recHintEnd.setLayoutX(topLeftInLayer.getX());
            recHintEnd.setLayoutY(topLeftInLayer.getY());
            recHintEnd.setWidth(cardW);
            recHintEnd.setHeight(cardH);
        } else {
            int lastIdx = targetPane.getChildren().size() - 1;
            ImageView endView = (ImageView) targetPane.getChildren().get(lastIdx);
            Bounds endBoundsInScene = endView.localToScene(endView.getBoundsInLocal());
            Bounds endBoundsInLayer = animationLayer.sceneToLocal(endBoundsInScene);
            recHintEnd.setLayoutX(endBoundsInLayer.getMinX());
            recHintEnd.setLayoutY(endBoundsInLayer.getMinY());
            recHintEnd.setWidth(endBoundsInLayer.getWidth());
            recHintEnd.setHeight(endBoundsInLayer.getHeight());
        }
    }

    public void actualizarPosicionPista() {      
        for (int sourceColIndex = 0; sourceColIndex < cardsInBoard.size(); sourceColIndex++) {      
            List<Card> sourceColumn = cardsInBoard.get(sourceColIndex);       
            for (int cardIndex = 0; cardIndex < sourceColumn.size(); cardIndex++) {             
                if (!canMoveSequence(sourceColIndex, cardIndex)) {
                    continue;
                }             
                Card sourceCard = sourceColumn.get(cardIndex);
                String sourceCardSuit = sourceCard.getSuit();
                int sourceCardValue = cardValues.get(sourceCard.getValue());
                if (cardIndex != sourceColumn.size() - 1) {
                    Card sourceCardUp = sourceColumn.get(cardIndex + 1);
                    boolean isNextCardFlip = sourceCardUp.isIsFlip();
                    String sourceCardSuitUp = sourceCardUp.getSuit();
                    int sourceCardValueUp = cardValues.get(sourceCardUp.getValue());
                    if (sourceCardSuitUp.equals(sourceCardSuit)
                            && sourceCardValue + 1 == sourceCardValueUp) {
                        continue;
                    }
                }
            
                for (int targetColIndex = 0; targetColIndex < cardsInBoard.size(); targetColIndex++) {
                   
                    if (targetColIndex == sourceColIndex) {
                        continue;
                    }
                  
                    List<Card> targetColumn = cardsInBoard.get(targetColIndex);

                    if (targetColumn.isEmpty()) {
                        hintCardIndex = cardIndex;
                        hintSourceColumnIndex = sourceColIndex;
                        lastHintTargetColumnIndex = hintTargetColumnIndex;
                        hintTargetColumnIndex = targetColIndex;
                        return;
                    }
                  
                    Card targetCard = targetColumn.get(targetColumn.size() - 1);
                    String targetCardSuit = targetCard.getSuit();
                    int targetCardValue = cardValues.get(targetCard.getValue());                 
                    if (sourceCardSuit.equals(targetCardSuit) && sourceCardValue + 1 == targetCardValue) {
                        hintCardIndex = cardIndex;
                        hintSourceColumnIndex = sourceColIndex;
                        lastHintTargetColumnIndex = hintTargetColumnIndex;
                        hintTargetColumnIndex = targetColIndex;
                        return;
                    }
                }
            }
        }
        if (!cardsInDeck.isEmpty()) {
            hintCardIndex = -1;
            hintSourceColumnIndex = -1;
            lastHintTargetColumnIndex = hintTargetColumnIndex;
            hintTargetColumnIndex = -1;
            return;
        }
        gameFailed.set(true);
    }

    @Override
    public void initialize() {

    }

    public void renderizarMazo() {
        pnDeck.getChildren().clear();
        for (Card card : cardsInDeck) {
            ImageView imvCard = createCardImageView(card);
            pnDeck.getChildren().add(imvCard);
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

    public ImageView createCardImageView(Card card) {
        ImageView imv = new ImageView(loadCardImage(card, card.isIsFlip()));
        imv.setFitWidth(50);
        imv.setPreserveRatio(true);
        return imv;
    }

    public Image loadCardImage(Card card, boolean isFlip) {
        String cardValue = card.getValue();
        String rawSuit = card.getSuit();
        String cardName = cardValue + "_" + rawSuit.substring(0, 1).toUpperCase()
                + rawSuit.substring(1).toLowerCase() + ".JPG";
        String backName = "Reverso.png";
        String basePath = "/cr/ac/una/proyectoprogra2/resources/Cards2/";
        String fullPath = basePath + (isFlip ? cardName : backName);

        System.out.println("DEBUG: buscando recurso → " + fullPath);
        URL imageUrl = getClass().getResource(fullPath);
        System.out.println("DEBUG: imageUrl = " + imageUrl);
        if (imageUrl == null) {
            imageUrl = getClass().getResource(basePath + "ReversoUNO.PNG");           
        }
        return new Image(imageUrl.toExternalForm());
    }

    public void assignDragAndClickEventsToEachCard() {
       
        for (int col = 0; col < columnPanes.size(); col++) {
            Pane pane = columnPanes.get(col);
            List<Card> cards = cardsInBoard.get(col);
           
            for (int i = 0; i < cards.size(); i++) {
                ImageView imv = (ImageView) pane.getChildren().get(i);
                Card card = cards.get(i);
                final int columnIndex = col;
                final int cardIndex = i;             
                addDragAndClickHandlers(imv, card, columnIndex, cardIndex);
            }
        }
    }

    private void addDragAndClickHandlers(ImageView imv, Card card, int columnIndex, int cardIndex) {
        final boolean[] dragging = {false};
        final double[] dragOffset = new double[2];
        final double[] originalPos = new double[2];
        
        imv.setOnMousePressed(e -> {
            Sonidos.reproducir("carta.mp3");
            originalPos[0] = imv.getLayoutX();
            originalPos[1] = imv.getLayoutY();
            dragOffset[0] = e.getSceneX() - originalPos[0];
            dragOffset[1] = e.getSceneY() - originalPos[1];
            dragging[0] = false;
        });

        imv.setOnMouseDragged(e -> {
            if (!canMoveSequence(columnIndex, cardIndex)) {
                System.out.println("Movimiento invalido: la secuencia no esta ordenada");
                selectedCardValue = null;
                return;
            }
            dragging[0] = true;
            Pane currentPane = columnPanes.get(columnIndex);

            currentPane.toFront();
            recHintEnd.toFront();
            recHintStart.toFront();
            enableCardFollowMouse(e, dragOffset, columnIndex, cardIndex);
        });

        imv.setOnMouseReleased(e -> {
            Sonidos.reproducir("carta.mp3");
            if (dragging[0]) {
                Pane paneTarget = findIntersectingPane(imv, columnIndex, cardIndex);
                if (paneTarget != null) {
                    int targetColumn = columnPanes.indexOf(paneTarget);
                    transferCardsToColumn(targetColumn, columnIndex, cardIndex);
                } else {               
                    restoreInitialPositions(originalPos[0], originalPos[1], columnIndex, cardIndex);
                }
                
            } else {
                handleCardClick(card, columnIndex, cardIndex);
            }
        });
    }

    private void handleCardClick(Card card, int columnIndex, int cardIndex) {
        if (!canMoveSequence(columnIndex, cardIndex)) {
            System.out.println("Movimiento invalido: la secuencia no esta ordenada");
            selectedCardValue = null;
            return;
        }

        if (selectedCardValue == null) {
            Integer cardValue = cardValues.get(card.getValue());
            selectedColumnIndex = columnIndex;
            selectedCardValue = cardValue;
            selectedCardIndex = cardIndex;
            recSelectedCard.setVisible(true);

            Bounds columnBoundsInScene = columnPanes.get(columnIndex).localToScene(columnPanes.get(columnIndex).getBoundsInLocal());
            Bounds deckBoundsInLayer = animationLayer.sceneToLocal(columnBoundsInScene);

            recSelectedCard.setLayoutX(deckBoundsInLayer.getMinX());
            recSelectedCard.setLayoutY(deckBoundsInLayer.getMinY() + OFFSET_Y * cardIndex);
            recSelectedCard.setWidth(deckBoundsInLayer.getWidth());
            System.out.println("Click en carta: "
                    + card.getSuit() + " " + card.getValue()
                    + " (columna " + columnIndex
                    + ", posicion " + cardIndex + ")");
        } else {
            recSelectedCard.setVisible(false);

            List<Card> targetColumn = cardsInBoard.get(columnIndex);

            
            if (!targetColumn.isEmpty()) {
                int lastIndexDestino = targetColumn.size() - 1;
                if (cardIndex != lastIndexDestino) {
                    System.out.println("Debes hacer click sobre la última carta de la columna destino.");
                    selectedCardValue = null;
                    return;
                }
            }


            if (selectedColumnIndex != columnIndex) {
                if (!targetColumn.isEmpty()) {
                    String lastCardValueString = targetColumn.get(targetColumn.size() - 1).getValue();
                    Integer lastCardValueInt = cardValues.get(lastCardValueString);
                    if (lastCardValueInt - 1 != selectedCardValue) {
                        System.out.println("No se puede mover: valores no coinciden.");
                        selectedCardValue = null;
                        return;
                    }
                }
               
                transferCardsToColumn(columnIndex, selectedColumnIndex, selectedCardIndex);
            }

            selectedCardValue = null;
        }
    }


    private boolean canMoveSequence(int columnIndex, int cardIndex) {
        List<Card> column = cardsInBoard.get(columnIndex);
        int columSize = column.size();
        if (cardIndex == columSize - 1) {
            return true;
        }
        Card previousCard = column.get(cardIndex);
        String cardSuit = previousCard.getSuit();
        int previousValue = cardValues.get(previousCard.getValue());
        for (int i = cardIndex + 1; i < columSize; i++) {
            Card currentCard = column.get(i);
            int currentValue = cardValues.get(currentCard.getValue());
            if (!currentCard.getSuit().equals(cardSuit)
                    || currentValue != previousValue - 1) {
                return false;
            }
            previousValue = currentValue;
        }
        return true;
    }


    private void enableCardFollowMouse(MouseEvent e, double[] dragOffset, int columnIndex, int cardIndex) {
        List<Card> column = cardsInBoard.get(columnIndex);
        double baseX = e.getSceneX() - dragOffset[0];
        double baseY = e.getSceneY() - dragOffset[1];
        for (int i = cardIndex; i < column.size(); i++) {
            ImageView currentCard = (ImageView) columnPanes.get(columnIndex).getChildren().get(i);
            int offsetIndex = i - cardIndex;
            currentCard.setLayoutX(baseX);
            currentCard.setLayoutY(baseY + offsetIndex * OFFSET_Y);
        }
    }

    
    private void restoreInitialPositions(double originalXPosition, double originalYposition,
            int columnIndex, int cardIndex) {
        List<Card> column = cardsInBoard.get(columnIndex);
        for (int i = cardIndex; i < column.size(); i++) {
            ImageView currentCard = (ImageView) columnPanes.get(columnIndex).getChildren().get(i);
            int offsetIndex = i - cardIndex;
            currentCard.setLayoutX(originalXPosition);
            currentCard.setLayoutY(originalYposition + offsetIndex * OFFSET_Y);
        }
    }

    //comentar
    private Pane findIntersectingPane(ImageView card, int columnIndex, int cardIndex) {
        Card cardToMove = cardsInBoard.get(columnIndex).get(cardIndex);
        int cardToMoveValue = cardValues.get(cardToMove.getValue());

        Bounds cardBounds = card.localToScene(card.getBoundsInLocal());

        for (int i = 0; i < columnPanes.size(); i++) {
            Pane pane = columnPanes.get(i);
            List<Card> targetColumn = cardsInBoard.get(i);
            if (targetColumn.isEmpty()) {
                Bounds paneBounds = pane.localToScene(pane.getBoundsInLocal());
                if (paneBounds.intersects(cardBounds)) {
                    return pane;
                }
                continue;
            }
            int lastCardPosition = targetColumn.size() - 1;
            Card lastTargetCard = targetColumn.get(lastCardPosition);
            int lastTargetCardValue = cardValues.get(lastTargetCard.getValue());

            Bounds paneBounds = pane.localToScene(pane.getBoundsInLocal());
            if (paneBounds.intersects(cardBounds) && lastTargetCardValue - 1 == cardToMoveValue) {
                return pane;
            }
        }
        return null;
    }

    private void transferCardsToColumn(int numberTargetColumn, int columnIndex, int cardIndex) {
        List<Card> currentColumn = cardsInBoard.get(columnIndex);
        List<Card> targetColumn = cardsInBoard.get(numberTargetColumn);
        List<Card> cardsToMove = new ArrayList<>();
        int numberCardsToTransfer = currentColumn.size() - cardIndex;
        for (int i = 0; i < numberCardsToTransfer; i++) {
            int lastCard = currentColumn.size() - 1;
            cardsToMove.add(currentColumn.get(lastCard));
            currentColumn.remove(lastCard);
        }
        for (int i = cardsToMove.size() - 1; i >= 0; i--) {
            targetColumn.add(cardsToMove.get(i));
        }
        renderizarColumnas();
        assignDragAndClickEventsToEachCard();
        Pane currentColumnPane = columnPanes.get(columnIndex);
        Pane targetColumPane = columnPanes.get(numberTargetColumn);
        // Revisa si la ultima carta esta volteada, si es asi, entonces la desvoltea
        if (!currentColumn.isEmpty() && !currentColumn.get(currentColumn.size() - 1).isIsFlip()) {
            Card cardToFlip = currentColumn.get(currentColumn.size() - 1);
            int lastIndex = currentColumnPane.getChildren().size() - 1;
            ImageView cardImageView = (ImageView) currentColumnPane.getChildren().get(lastIndex);
            SequentialTransition flipAnimation = animationService.flipCard(cardImageView, cardToFlip, this);
            flipAnimation.setOnFinished(flipEvt -> {
                removeCompleteStackInColumn(numberTargetColumn);
                assignDragAndClickEventsToEachCard();
            });

            flipAnimation.play();
        } else {
            removeCompleteStackInColumn(numberTargetColumn);
            assignDragAndClickEventsToEachCard();
        }
        actualizarPosicionPista();
        puntuacionActual = Math.max(0, puntuacionActual - 1);
        lbPuntuacion.setText(String.valueOf(puntuacionActual));
    }


    private void removeCompleteStackInColumn(int colIndex) {
        renderizarColumnas();
        List<Card> column = cardsInBoard.get(colIndex);
        int columnSize = column.size();
        Pane sourcePane = columnPanes.get(colIndex);

        if (column.size() < 13 || sourcePane.getChildren().size() < 13) {
            return;
        }       
        int totalCards = column.size();
        int startIndex = totalCards - 13;
        for (int i = 0; i < 12; i++) {
            Card upperCard = column.get(startIndex + i); 
            Card lowerCard = column.get(startIndex + i + 1); 

            if (!upperCard.getSuit().equals(lowerCard.getSuit())) {
                return;
            }

            int upperCardValue = cardValues.get(upperCard.getValue());
            int lowerCardValue = cardValues.get(lowerCard.getValue());
            if (upperCardValue != lowerCardValue + 1) {
                return;
            }
        }

        SequentialTransition seq = new SequentialTransition();
        Pane targetPane = getFirstAvailableFoundationPane();

        boolean stack = false;
       
        for (int cardIndex = 1; cardIndex <= 13; cardIndex++) {
   
            ImageView cardImageView = (ImageView) sourcePane.getChildren().get(columnSize - cardIndex);
         
            final Card toRemove = column.get(columnSize - cardIndex);

            TranslateTransition tt = animationService.moveCardToPane(sourcePane,
                    targetPane, cardImageView, stack, animationLayer);
 
            tt.setOnFinished(evt -> {
                transferCardToFoundation(toRemove);
                column.remove(toRemove);              
            });

            seq.getChildren().add(tt);
        }

        seq.setOnFinished(evt -> {

            if (column.size() >= 1 && !column.get(column.size() - 1).isIsFlip()) {
                Card cardToFlip = column.get(column.size() - 1);

                renderizarColumnas();

                Pane columnPane = columnPanes.get(colIndex);
                ImageView cardImageView = (ImageView) columnPane.getChildren().get(columnPane.getChildren().size() - 1);

                SequentialTransition flipAnimation = animationService.flipCard(cardImageView, cardToFlip, this);

                flipAnimation.setOnFinished(flipEvt -> {
                    animationLayer.getChildren().removeIf(node -> node instanceof ImageView);
                    renderizarColumnas();
                    renderFoundations();
                    puntuacionActual += 100;
                    lbPuntuacion.setText(String.valueOf(puntuacionActual));
                    assignDragAndClickEventsToEachCard();
                    if (foundationPanes.indexOf(targetPane) == 7) {
                        gameWon.set(true);
                        gameWonAction();
                    }
                });

                flipAnimation.play();
            } else {
                animationLayer.getChildren().removeIf(node -> node instanceof ImageView);
                renderizarColumnas();
                renderFoundations();
                puntuacionActual += 100;
                lbPuntuacion.setText(String.valueOf(puntuacionActual));
                assignDragAndClickEventsToEachCard();
                if (foundationPanes.indexOf(targetPane) == 0) {
                    gameWon.set(true);
                    gameWonAction();
                }
            }
        });
        seq.play();

    }
    private Pane getFirstAvailableFoundationPane() {
        for (int foundationIndex = 0; foundationIndex < foundationPanes.size(); foundationIndex++) {
            Pane pane = foundationPanes.get(foundationIndex);
            if (pane.getChildren().isEmpty()) {
                return pane;
            }
        }
        return foundationPanes.get(0);
    }

    private void transferCardToFoundation(Card cardToTransfer) {
        if (cardsInFoundations.isEmpty()) {
            cardsInFoundations.add(new ArrayList<>());
        }
        List<Card> targetFoundation = null;
        for (List<Card> foundation : cardsInFoundations) {
            if (foundation.size() < 13) {
                targetFoundation = foundation;
                break;
            }
        }
        if (targetFoundation == null) {
            targetFoundation = new ArrayList<>();
            cardsInFoundations.add(targetFoundation);
        }
        targetFoundation.add(cardToTransfer);
    }

    public AnchorPane getAnimationLayer() {
        return animationLayer;
    }

    public Button getDeckPane() {
        return btnDeck;
    }

    private void gameWonAction() {
        
    }
}
