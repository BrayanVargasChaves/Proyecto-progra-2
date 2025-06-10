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
    private Map<Integer, Card> imageCache = new HashMap<>();
    private Integer selectedCardValue;
    private int selectedColumnIndex; //columna seleccionada para mover con click
    private int selectedCardIndex; //indice seleccionado para mover con click
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
        attachGameWonListener();
        attachGameFailedListener();

        buildCardValues();
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
        renderColumns();
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
            renderColumns();
            assignDragAndClickEventsToEachCard();
            updateHintPositions();
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
        updateHintPositions();
         onActionLbHint(e);
    }

    private void attachGameWonListener() {
        gameWon.addListener((o, oldVal, newVal) -> {
            if (newVal) {
                animationService.playVictoryAnimation(
                        foundationPanes, animationLayer
                );
            }
        });
    }

    private void attachGameFailedListener() {
        gameFailed.addListener((o, oldVal, newVal) -> {
            if (newVal) {
                animationService.defeatAnimation(
                        columnPanes, btnDeck, foundationPanes
                );
            }
        });
    }

    private void buildCardValues() {
        cardValues = new HashMap<>();
        List<String> ranks = Arrays.asList(
                "A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K"
        );
        for (int i = 0; i < ranks.size(); i++) {
            cardValues.put(ranks.get(i), i + 1);
        }
    }

    public void renderColumns() {
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
        // Verificar si algún valor es -1 (significa que hay que clickear el deck)
        if (hintSourceColumnIndex == -1 || hintCardIndex == -1 || hintTargetColumnIndex == -1) {
            // Solo mostrar el rectángulo de hint en el deck
            recHintStart.setVisible(true);
            recHintEnd.setVisible(false); // No mostrar el destino

            // Obtener los bounds del deck pane en coordenadas de escena
            Bounds deckBoundsInScene = pnDeck.localToScene(pnDeck.getBoundsInLocal());
            // Convertir esos bounds al sistema de 'animationLayer'
            Bounds deckBoundsInLayer = animationLayer.sceneToLocal(deckBoundsInScene);

            // Colocar recHintStart sobre el deck
            recHintStart.setLayoutX(deckBoundsInLayer.getMinX());
            recHintStart.setLayoutY(deckBoundsInLayer.getMinY());
            recHintStart.setWidth(deckBoundsInLayer.getWidth());
            recHintStart.setHeight(deckBoundsInLayer.getHeight());

            return; // Salir del método
        }

        // Validar que los índices sean válidos para movimientos entre columnas
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

        // Si llegamos aquí, todos los índices son válidos para movimiento entre columnas
        // Mostrar ambos rectángulos de pistas
        recHintEnd.setVisible(true);
        recHintStart.setVisible(true);

        // Ahora se obtiene el imv de la carte de inicio
        ImageView startView = (ImageView) sourcePane.getChildren().get(hintCardIndex);

        // 1.a) tomo bounds en escena de la carta de origen
        Bounds startBoundsInScene = startView.localToScene(startView.getBoundsInLocal());
        // 1.b) convierto esos bounds al sistema de 'animationLayer'
        Bounds startBoundsInLayer = animationLayer.sceneToLocal(startBoundsInScene);
        // 1.c) coloco recHintStart con el mismo tamaño y posición que la carta
        recHintStart.setLayoutX(startBoundsInLayer.getMinX());
        recHintStart.setLayoutY(startBoundsInLayer.getMinY());
        recHintStart.setWidth(startBoundsInLayer.getWidth());
        recHintStart.setHeight(startBoundsInLayer.getHeight() + OFFSET_Y * (sourcePane.getChildren().size() - hintCardIndex - 1));

        // --- POSICIONAR recHintEnd SOBRE LA CARTA (o hueco) DE DESTINO ---
        Pane targetPane = columnPanes.get(hintTargetColumnIndex);
        if (targetPane.getChildren().isEmpty()) {
            // 4.a) Columna destino VACÍA: simulamos un hueco del tamaño de la carta
            double cardW = startBoundsInLayer.getWidth();
            double cardH = startBoundsInLayer.getHeight();
            // 4.b) Obtener la esquina superior izquierda de targetPane en coordenadas de escena
            Point2D topLeftScene = targetPane.localToScene(0, 0);
            // 4.c) Convertir ese punto al sistema de animationLayer
            Point2D topLeftInLayer = animationLayer.sceneToLocal(topLeftScene);
            // 4.d) Ubicar recHintEnd en esa posición con el tamaño de carta
            recHintEnd.setLayoutX(topLeftInLayer.getX());
            recHintEnd.setLayoutY(topLeftInLayer.getY());
            recHintEnd.setWidth(cardW);
            recHintEnd.setHeight(cardH);
        } else {
            // Columna destino NO vacía: tomo la última carta
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

    public void updateHintPositions() {
        // Se recorre cada columna
        for (int sourceColIndex = 0; sourceColIndex < cardsInBoard.size(); sourceColIndex++) {
            // Se obtiene la columna a trabajar
            List<Card> sourceColumn = cardsInBoard.get(sourceColIndex);
            // Se recorre la columna carta por carta
            for (int cardIndex = 0; cardIndex < sourceColumn.size(); cardIndex++) {
                // Verificar si la carta no se puede mover, en dado caso, no sirve
                if (!canMoveSequence(sourceColIndex, cardIndex)) {
                    continue;
                }
                // Se obtiene la carta y sus datos por separado
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
                // Ahora se recorre otra vez las columnas para ver si se puede mover ahi
                for (int targetColIndex = 0; targetColIndex < cardsInBoard.size(); targetColIndex++) {
                    // No se evalua cuando las columnas son iguales
                    if (targetColIndex == sourceColIndex) {
                        continue;
                    }
                    // Se obtiene la columna target
                    List<Card> targetColumn = cardsInBoard.get(targetColIndex);
                    // Si la columna esta vacia, quiere decir que se puede hacer un movimiento
                    if (targetColumn.isEmpty()) {
                        hintCardIndex = cardIndex;
                        hintSourceColumnIndex = sourceColIndex;
                        lastHintTargetColumnIndex = hintTargetColumnIndex;
                        hintTargetColumnIndex = targetColIndex;
                        return;
                    }
                    // Se obtiene la ultima carta y los datos de la columna para ver si es apta
                    Card targetCard = targetColumn.get(targetColumn.size() - 1);
                    String targetCardSuit = targetCard.getSuit();
                    int targetCardValue = cardValues.get(targetCard.getValue());
                    // Si se pueden combiar, entonces sirve como pista y se termina el metodo
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

    public void renderDeck() {
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

    private void addDragAndClickHandlers(ImageView imv, Card card, int columnIndex, int cardIndex) {
        // Flag mutable para saber si el usuario arrastró la carta
        final boolean[] dragging = {false};
        // Offset entre la posición del cursor y la esquina superior de la imagen
        final double[] dragOffset = new double[2];
        final double[] originalPos = new double[2];

        // Primer evento: Cuando presiona el ratón sobre la carta
        imv.setOnMousePressed(e -> {
            Sonidos.reproducir("carta.mp3");
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
            if (!canMoveSequence(columnIndex, cardIndex)) {
                System.out.println("Movimiento invalido: la secuencia no esta ordenada");
                selectedCardValue = null;
                return;
            }
            dragging[0] = true;
            Pane currentPane = columnPanes.get(columnIndex);
            // Se coloca el pane actual para que las cartas al moverse, esten al frente

            currentPane.toFront();
            recHintEnd.toFront();
            recHintStart.toFront();
            enableCardFollowMouse(e, dragOffset, columnIndex, cardIndex);
        });

        // Tercer evento: Al soltar el ratón o soltar arrastre o ejecutar click
        imv.setOnMouseReleased(e -> {
            Sonidos.reproducir("carta.mp3");
            //si el dragging es true, entonces restaura la posicion inicial de la carta
            if (dragging[0]) {
                // Se revisa si el usuario puso cartas en otra columna
                Pane paneTarget = findIntersectingPane(imv, columnIndex, cardIndex);
                if (paneTarget != null) {
                    int targetColumn = columnPanes.indexOf(paneTarget);
                    transferCardsToColumn(targetColumn, columnIndex, cardIndex);
                } else {
                    // no intersecta ninguna columna, entonces se restauran posiciones iniciales
                    restoreInitialPositions(originalPos[0], originalPos[1], columnIndex, cardIndex);
                }
                //agregar la logica para cuando se intersecta una carta
                //tambien se ocupa la logica para seleccionar una cadena de cartas
            } else {
                // Aquí el click rápido
                handleCardClick(card, columnIndex, cardIndex);
            }
        });
    }

    //Metodo para mover cartas con el click
    private void handleCardClick(Card card, int columnIndex, int cardIndex) {
        if (!canMoveSequence(columnIndex, cardIndex)) {
            System.out.println("Movimiento invalido: la secuencia no esta ordenada");
            selectedCardValue = null;
            return;
        }

        /* Si selectedCardValue es nulo, quiere decir que no hay ninguna seleccionada
           entonces se actualizan todos los valores necesarios para mover la carta*/
        if (selectedCardValue == null) {
            Integer cardValue = cardValues.get(card.getValue());
            selectedColumnIndex = columnIndex;
            selectedCardValue = cardValue;
            selectedCardIndex = cardIndex;
            recSelectedCard.setVisible(true);

            Bounds columnBoundsInScene = columnPanes.get(columnIndex).localToScene(columnPanes.get(columnIndex).getBoundsInLocal());
            // Convertir esos bounds al sistema de 'animationLayer'
            Bounds deckBoundsInLayer = animationLayer.sceneToLocal(columnBoundsInScene);

            recSelectedCard.setLayoutX(deckBoundsInLayer.getMinX());
            recSelectedCard.setLayoutY(deckBoundsInLayer.getMinY() + OFFSET_Y * cardIndex);
            recSelectedCard.setWidth(deckBoundsInLayer.getWidth());
//            recSelectedCard.setHeight(recSelectedCard);
            System.out.println("Click en carta: "
                    + card.getSuit() + " " + card.getValue()
                    + " (columna " + columnIndex
                    + ", posicion " + cardIndex + ")");
        } else {
            recSelectedCard.setVisible(false);

            List<Card> targetColumn = cardsInBoard.get(columnIndex);

            // Si la columna destino no esta vacia, se verificar que el click fue en la última carta
            if (!targetColumn.isEmpty()) {
                int lastIndexDestino = targetColumn.size() - 1;
                if (cardIndex != lastIndexDestino) {
                    System.out.println("Debes hacer click sobre la última carta de la columna destino.");
                    selectedCardValue = null;
                    return;
                }
            }

            // Comprobar que no sea la misma columna y que los valores encajen
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
                // Mover la carta seleccioanda a la columna
                transferCardsToColumn(columnIndex, selectedColumnIndex, selectedCardIndex);
            }

            // Se quita la seleccion para volver a empezar el ciclo
            selectedCardValue = null;
        }
    }

    //Metodo para saber si se puede mover una carta
    private boolean canMoveSequence(int columnIndex, int cardIndex) {
        List<Card> column = cardsInBoard.get(columnIndex);
        int columSize = column.size();
        //Si la carta es la ultima, siempre se va a poder mover
        if (cardIndex == columSize - 1) {
            return true;
        }
        /*Si la carta esta en medio, entonces se revisa si hay una escalera del
          mismo palo*/
        //Se crea una carta previa para comparar
        Card previousCard = column.get(cardIndex);
        String cardSuit = previousCard.getSuit();
        int previousValue = cardValues.get(previousCard.getValue());
        for (int i = cardIndex + 1; i < columSize; i++) {
            //se crea una carta actual
            Card currentCard = column.get(i);
            int currentValue = cardValues.get(currentCard.getValue());
            /*Si la carta actual es de un diferente palo o si el valor no es uno
              menor a la carta previa, quiere decir que no hay escalera*/
            if (!currentCard.getSuit().equals(cardSuit)
                    || currentValue != previousValue - 1) {
                return false;
            }
            previousValue = currentValue;
        }
        return true;
    }

    // Metodo para hacer que las cartas sigan al mouse
    //comentar
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

    // Metodo para regresar a las posiciones iniciales a cartas que no se combinaron en otra columna
    //comentar
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
            // Se verifica si la columna esta vacia
            if (targetColumn.isEmpty()) {
                Bounds paneBounds = pane.localToScene(pane.getBoundsInLocal());
                // Se revisa si el imv de la carta choca con la columna target
                if (paneBounds.intersects(cardBounds)) {
                    return pane;
                }
                // Si no choca, se continua el ciclo
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

    // Metodo para transferir cartas de una columna a otra
    //comentar
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
        renderColumns();
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
        updateHintPositions();
        puntuacionActual = Math.max(0, puntuacionActual - 1);
        lbPuntuacion.setText(String.valueOf(puntuacionActual));
    }

    // Metodo para borrar una pila completa de una columna
    //cambiar nombres
    //comentar
    private void removeCompleteStackInColumn(int colIndex) {
        renderColumns();
        // Obtenemos la columna y el pane correspodiente
        List<Card> column = cardsInBoard.get(colIndex);
        int columnSize = column.size();
        Pane sourcePane = columnPanes.get(colIndex);

        // Si hay menos de 13 cartas, entonces no hay pila
        if (column.size() < 13 || sourcePane.getChildren().size() < 13) {
            return;
        }
        // Verificar que las últimas 13 cartas formen una escalera del mismo palo
        int totalCards = column.size();
        int startIndex = totalCards - 13;
        // Vamos del 0 al 11 para saber si hay cartas consecutivas
        for (int i = 0; i < 12; i++) {
            Card upperCard = column.get(startIndex + i); // carta arriba
            Card lowerCard = column.get(startIndex + i + 1); // carta abajo
            // Si no son del mismo palo, no hay pila
            if (!upperCard.getSuit().equals(lowerCard.getSuit())) {
                return;
            }
            // Las cartas deben formar una escalera con sus valores
            int upperCardValue = cardValues.get(upperCard.getValue());
            int lowerCardValue = cardValues.get(lowerCard.getValue());
            if (upperCardValue != lowerCardValue + 1) {
                return;
            }
        }
        // Buscamos el pane de la pila y preparamos la animacion
        SequentialTransition seq = new SequentialTransition();
        Pane targetPane = getFirstAvailableFoundationPane();
        /* stack sirve para saber si las cartas se deben mover una detras de
           otra o forma de escalera*/
        boolean stack = false;
        // Hacemos 13 animaciones, movimiento la ultima en cada ciclo
        for (int cardIndex = 1; cardIndex <= 13; cardIndex++) {
            // Obtenemos el imv de la ultima carta
            ImageView cardImageView = (ImageView) sourcePane.getChildren().get(columnSize - cardIndex);
            // Obtenemos la carta a mover (la ultima)
            final Card toRemove = column.get(columnSize - cardIndex);
            // Se crea la animacion para mover la carta a la pila
            TranslateTransition tt = animationService.moveCardToPane(sourcePane,
                    targetPane, cardImageView, stack, animationLayer);
            /* Cuando termine la animacion movemos la carta a la pila y la
               quitamos de la columna*/
            tt.setOnFinished(evt -> {
                transferCardToFoundation(toRemove);
                column.remove(toRemove);              
            });
            // Agregamos la animacion a la sequencia para que se vea fluido
            seq.getChildren().add(tt);
        }
        /* Al finalizar las 13 animaciones, se comprueba primero si hay que girar
           la ultima carta que queda para luego hacer render, si no, simplemente
           se llaman los renders normalmente*/
        seq.setOnFinished(evt -> {
            /* Comprobacion si hay que girar una carta*/
            if (column.size() >= 1 && !column.get(column.size() - 1).isIsFlip()) {
                Card cardToFlip = column.get(column.size() - 1);

                // Primero renderiza para tener las ImageViews actualizadas
                renderColumns();

                // Encuentra la ImageView de la carta a voltear (será la última visible)
                Pane columnPane = columnPanes.get(colIndex);
                ImageView cardImageView = (ImageView) columnPane.getChildren().get(columnPane.getChildren().size() - 1);

                // se llama la animacion para girar la carta
                SequentialTransition flipAnimation = animationService.flipCard(cardImageView, cardToFlip, this);

                flipAnimation.setOnFinished(flipEvt -> {
                    // Después del flip, usar los renders
                    animationLayer.getChildren().removeIf(node -> node instanceof ImageView);
                    renderColumns();
                    renderFoundations();
                    puntuacionActual += 100;
                    lbPuntuacion.setText(String.valueOf(puntuacionActual));
                    assignDragAndClickEventsToEachCard();
                    if (foundationPanes.indexOf(targetPane) == 7) {//cambiar por size - 1
                        gameWon.set(true);
                    }
                });

                flipAnimation.play();
            } else {
                // Si no hay carta para voltear, usar los renders normalmente
                animationLayer.getChildren().removeIf(node -> node instanceof ImageView);
                renderColumns();
                renderFoundations();
                puntuacionActual += 100;
                lbPuntuacion.setText(String.valueOf(puntuacionActual));
                assignDragAndClickEventsToEachCard();
                if (foundationPanes.indexOf(targetPane) == 0) {
                    gameWon.set(true);
                }
            }
        });
        seq.play();

    }

    //comentar
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
        // 1) Asegurarnos de que al menos exista una fundación
        if (cardsInFoundations.isEmpty()) {
            cardsInFoundations.add(new ArrayList<>());
        }

        // 2) Buscar la primera fundación con menos de 13 cartas
        List<Card> targetFoundation = null;
        for (List<Card> foundation : cardsInFoundations) {
            if (foundation.size() < 13) {
                targetFoundation = foundation;
                break;
            }
        }

        // 3) Si todas estaban llenas, creamos otra
        if (targetFoundation == null) {
            targetFoundation = new ArrayList<>();
            cardsInFoundations.add(targetFoundation);
        }

        // 4) Añadir la carta a la fundación escogida
        targetFoundation.add(cardToTransfer);
    }

    public AnchorPane getAnimationLayer() {
        return animationLayer;
    }

    public Button getDeckPane() {
        return btnDeck;
    }
}
