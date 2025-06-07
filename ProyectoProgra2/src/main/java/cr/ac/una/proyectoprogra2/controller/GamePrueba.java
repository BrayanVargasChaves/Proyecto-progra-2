
package cr.ac.una.spider.solitaire.controller;

import cr.ac.una.proyectoprogra2.model.Card;
import cr.ac.una.proyectoprogra2.util.CardFactory;
import cr.ac.una.spider.solitaire.model.Card;
import cr.ac.una.spider.solitaire.services.AnimationService;
import cr.ac.una.spider.solitaire.util.CardFactory;
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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BoardViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane animationLayer;
    @FXML
    private MFXButton btnDeck;
    @FXML
    private Label lbPoints;
    @FXML
    private Label lbTime;
    @FXML
    private Pane pnColumn1;
    @FXML
    private Pane pnColumn2;
    @FXML
    private Pane pnColumn3;
    @FXML
    private Pane pnColumn4;
    @FXML
    private Pane pnColumn5;
    @FXML
    private Pane pnColumn6;
    @FXML
    private Pane pnColumn7;
    @FXML
    private Pane pnColumn8;
    @FXML
    private Pane pnColumn9;
    @FXML
    private Pane pnColumn10;
    @FXML
    private Pane pnDeck;
    @FXML
    private Pane pnFoundation1;
    @FXML
    private Pane pnFoundation2;
    @FXML
    private Pane pnFoundation3;
    @FXML
    private Pane pnFoundation4;
    @FXML
    private Pane pnFoundation5;
    @FXML
    private Pane pnFoundation6;
    @FXML
    private Pane pnFoundation7;
    @FXML
    private Pane pnFoundation8;
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
    //private AnimationService animationService;
    private BooleanProperty gameWon;
    private BooleanProperty gameFailed;
    private int hintSourceColumnIndex;
    private int hintCardIndex;
    private int hintTargetColumnIndex;
    private int lastHintTargetColumnIndex;

    @FXML
    void onActionBtnDeck(ActionEvent event) {
        // Si el mazo esta vacio, entonces no hay nada que hacer
        if (cardsInDeck.isEmpty()) {
            return;
        }
        int deckSize = cardsInDeck.size() - 1;

        // Primer se reparten 10 cartas, una por columna
        SequentialTransition seq = new SequentialTransition();
        for (int col = 0; col < 10; col++) {
            final int targetColumn = col;
            //Se obtiene el imv asociado a la carta
            ImageView cardImageView = (ImageView) pnDeck.getChildren().get(deckSize - col);
            //Se obtiene la carta
            Card card = cardsInDeck.get(deckSize - col);
            boolean stack = true;
            //Se crea la animacion para mover las cartas
            TranslateTransition tt = animationService.moveCardToPane(pnDeck,
                    columnPanes.get(col), cardImageView, stack, animationLayer);
            // Se crea la animacion para voltear las cartas (se hace cuando la otra termina)
            ScaleTransition flipTransition = animationService.flipCard(cardImageView, card, this);
            /*cuando una animacion individual termina, se agrega la carta a la
              columna y se borra del mazo*/
            tt.setOnFinished(evt -> {
                cardsInBoard.get(targetColumn).add(card);
                cardsInDeck.remove(deckSize - targetColumn);
            });
            seq.getChildren().add(tt);
            seq.getChildren().add(flipTransition);
        }
        // Cuando las 10 animaciones acaban se renderiza todo para no dejar rastro
        seq.setOnFinished(evt -> {
            /* En la animacion, la carta se mueve por el anchor pane, entonces
               se deben borrar para no dejar objetos duplicados*/

            animationLayer.getChildren().removeIf(node -> node instanceof ImageView);

            // Se renderiza todo de nuevo y se les asignan los eventos del mouse
            renderDeck();
            renderColumns();
            assignDragAndClickEventsToEachCard();
            updateHintPositions();
        });
        seq.play();
    }

    @FXML
    void onActionLbEndGame(ActionEvent event) {
        gameFailed.set(true);
    }

    @FXML
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
    public void initialize(URL url, ResourceBundle rb) {
        recHintEnd.setVisible(false);
        recHintStart.setVisible(false);
        recSelectedCard.setVisible(false);
        lastHintTargetColumnIndex = -1;
        gameWon = new SimpleBooleanProperty(false);
        gameFailed = new SimpleBooleanProperty(false);
        attachGameWonListener();
        attachGameFailedListener();
        // Se llena el mapa con las posibles combinaciones de carta y su valor
        fillCardsValues();
        animationService = new AnimationService();
        // Se crea la distribucion inicial (no aparece en la version final)
        CardFactory.getInstance().createDistribution(1);
        // Se obtienen las cartas que van en las columnas
        cardsInBoard = CardFactory.getInstance().getCardsInColumns();
        // Se obtienen las cartas que van en el mazo
        cardsInDeck = CardFactory.getInstance().getCardsInDeck();
        cardsInFoundations = new ArrayList<>();
        columnPanes = Arrays.asList(
                pnColumn1, pnColumn2, pnColumn3, pnColumn4, pnColumn5,
                pnColumn6, pnColumn7, pnColumn8, pnColumn9, pnColumn10
        );
        foundationPanes = Arrays.asList(
                pnFoundation1, pnFoundation2, pnFoundation3,
                pnFoundation4, pnFoundation5, pnFoundation6,
                pnFoundation7, pnFoundation8);
        renderDeck();
        // En la animacion inicial, se hacen los renders necesarios
        animationService.initialAnimation(cardsInBoard, cardsInDeck, columnPanes, pnDeck, this);

    }

    @Override
    public void initialize() {

    }

    public void attachGameWonListener() {
        gameWon.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                animationService.playVictoryAnimation(foundationPanes, animationLayer);
            }
        });
    }

    public void attachGameFailedListener() {
        gameFailed.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                animationService.defeatAnimation(columnPanes, pnDeck, foundationPanes);
            }
        });
    }

    // Metodo para asociar el valor de una carta con su simbolo
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

    // Metodo para crear las imv de las cartas que van en las columnas
    public void renderColumns() {
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

    // Metodo para crear las imv de las cartas que van en el mazo
    public void renderDeck() {
        pnDeck.getChildren().clear();
        for (Card card : cardsInDeck) {
            ImageView imvCard = createCardImageView(card);
            pnDeck.getChildren().add(imvCard);
        }
    }

    // Metodo para crear las imv de las cartas que van en las pilas
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

    // Metodo para obtener la imagen de la carta sabiendo su valor
    public ImageView createCardImageView(Card card) {
        ImageView imv = new ImageView(loadCardImage(card, card.isIsFlip()));
        imv.setFitWidth(80);
        imv.setPreserveRatio(true);
        return imv;
    }

    // Metodo para obtener el path de la carta
    //NOTA: CUANDO SE TENGA LA OPCION 2 DEL FRENTE, SE DEBE MODIFICAR ESTO
    public Image loadCardImage(Card card, boolean isFlip) {
        String cardValue = card.getValue();
        String cardSuit = card.getSuit().toLowerCase();
        String cardName = cardSuit + "_" + cardValue + ".png";
        String backName = "back_blue.png";
        String url = "/cr/ac/una/spider/solitaire/resources/cards/";
        if (isFlip) {
            url += cardName;
        } else {
            url += backName;
        }
        URL imageUrl = getClass().getResource(url);
        Image img = new Image(imageUrl.toExternalForm());
        return img;
    }

    /*Asigna a cada carta de forma indivual un evento para arrastrar con el click
      sostenido o un evento con el click individual*/
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

    //Metodo que asigna cada metodo a cada carta correspondiente
    private void addDragAndClickHandlers(ImageView imv, Card card, int columnIndex, int cardIndex) {
        // Flag mutable para saber si el usuario arrastró la carta
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
            ScaleTransition flipAnimation = animationService.flipCard(cardImageView, cardToFlip, this);
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
                ScaleTransition flipAnimation = animationService.flipCard(cardImageView, cardToFlip, this);

                flipAnimation.setOnFinished(flipEvt -> {
                    // Después del flip, usar los renders
                    animationLayer.getChildren().removeIf(node -> node instanceof ImageView);
                    renderColumns();
                    renderFoundations();
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

    public MFXButton getBtnDeck() {
        return btnDeck;
    }

}
