package latice.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import latice.game.*;
import java.util.*;

public class MainFX extends Application {

    private static final int TILE_SIZE = 50;
    private static final int BOARD_SIZE = 9;

    private Board boardModel = new Board();
    private Map<Label, Tile> tileMap = new HashMap<>();
    private Arbitre arbitre;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private VBox rackDisplay;
    private HBox racks;

    // Pour l'affichage des scores
    private VBox scoreBox;
    private Label scorePlayer1Label;
    private Label scorePlayer2Label;
    private Label infoLabel;

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    @SuppressWarnings("unused")
	@Override
    public void start(Stage primaryStage) {
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = new ArrayList<>(tileSet.getTiles());
        Collections.shuffle(allTiles);

        Pool pool1 = new Pool(allTiles.subList(0, 36));
        Pool pool2 = new Pool(allTiles.subList(36, 72));
        player1 = new Player("Paul", pool1);
        player2 = new Player("Jordan", pool2);
        currentPlayer = Math.random() < 0.5 ? player1 : player2;

        arbitre = new Arbitre(boardModel);

        BorderPane root = new BorderPane();

        GridPane boardGrid = createBoardGrid();
        root.setCenter(boardGrid);

        racks = new HBox(50);
        racks.setAlignment(Pos.CENTER);
        rackDisplay = createRackDisplay(currentPlayer.getName() + " à ton tour de jouer :", currentPlayer.getRack());
        racks.getChildren().add(rackDisplay);
        root.setBottom(racks);

        // Zone score à droite
        scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.TOP_CENTER);

        Label scoreTitle = new Label("Scores");
        scoreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        scorePlayer1Label = new Label(player1.getName() + " : 0");
        scorePlayer1Label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        scorePlayer2Label = new Label(player2.getName() + " : 0");
        scorePlayer2Label.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        
        infoLabel = new Label(currentPlayer.getName() + ", à ton tour !");
        infoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        infoLabel.setTextFill(Color.BLUE);

        scoreBox.getChildren().addAll(scoreTitle, scorePlayer1Label, scorePlayer2Label, infoLabel);
        root.setRight(scoreBox);
        
        // Boutons d'action
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button endTurnButton = new Button("Fin du tour");
        Button endGameButton = new Button("Fin de partie");

        endTurnButton.setOnAction(e -> switchPlayer());

        endGameButton.setOnAction(e -> {
            Stage stage = (Stage) endGameButton.getScene().getWindow();
            stage.close();
        });

        buttonBox.getChildren().addAll(endTurnButton, endGameButton);

        scoreBox.getChildren().add(buttonBox);

        
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(boardGrid, scoreBox);

        root.setCenter(centerBox);

        racks = new HBox(50);
        racks.setAlignment(Pos.CENTER);
        rackDisplay = createRackDisplay(currentPlayer.getName() + " à ton tour de jouer :", currentPlayer.getRack());
        racks.getChildren().add(rackDisplay);
        root.setBottom(racks);

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setTitle("Latice - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createBoardGrid() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(TILE_SIZE, TILE_SIZE);
                cell.setStyle("-fx-border-color: black; -fx-background-color: #00008B;");

                Label label = new Label();
                label.setFont(Font.font("Segoe UI Emoji", 20));

                if (row == 4 && col == 4) {
                    label.setText("🌙");
                    label.setTextFill(Color.SILVER);
                } else if (isSunstone(row, col)) {
                    label.setText("☀");
                    label.setTextFill(Color.YELLOW);
                }

                cell.getChildren().add(label);

                final int r = row;
                final int c = col;

                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                cell.setOnDragDropped(event -> {
                    if (event.getGestureSource() instanceof Label sourceLabel) {
                        Tile draggedTile = tileMap.get(sourceLabel);
                        if (draggedTile != null) {
                            if (arbitre.isMoveValid(draggedTile, r, c)) {
                                BoardCell boardCell = boardModel.getCell(r, c);
                                if (boardCell.isEmpty()) {
                                    Label newLabel = createTileLabel(draggedTile);
                                    cell.getChildren().clear();
                                    cell.getChildren().add(newLabel);
                                    boardCell.placeTile(draggedTile);
                                    sourceLabel.setVisible(false);
                                    event.setDropCompleted(true);

                                    // Retirer la tuile jouée du rack
                                    currentPlayer.getRack().removeTile(draggedTile);

                                    // Calculer les points pour ce placement
                                    int points = calculerPointsPlacement(draggedTile, r, c);
                                    ajouterPointsAuJoueur(currentPlayer, points);

                                    // Ajouter une nouvelle tuile si possible
                                    Tile newTile = currentPlayer.getPool().drawTile();
                                    if (newTile != null) {
                                        currentPlayer.getRack().getTiles().add(newTile);
                                    }

                                    // Vérifier fin de partie
                                    if (finDePartie()) {
                                        afficherVainqueur();
                                    }
                                }
                            } else {
                                System.out.println("Placement invalide !");
                                event.setDropCompleted(false);
                            }
                        }
                    }
                    event.consume();
                });

                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    private int calculerPointsPlacement(Tile tile, int row, int col) {
        // Récupérer les cellules adjacentes
        List<BoardCell> adjacents = new ArrayList<>();
        if (row > 0) adjacents.add(boardModel.getCell(row - 1, col));
        if (row < BOARD_SIZE - 1) adjacents.add(boardModel.getCell(row + 1, col));
        if (col > 0) adjacents.add(boardModel.getCell(row, col - 1));
        if (col < BOARD_SIZE - 1) adjacents.add(boardModel.getCell(row, col + 1));

        // Comptage des tuiles adjacentes compatibles (même forme ou couleur)
        int adjacentCompatibleCount = 0;
        Set<BoardCell> countedCells = new HashSet<>();

        for (BoardCell cell : adjacents) {
            if (!cell.isEmpty()) {
                Tile t = cell.getTile();
                if (t.getColor() == tile.getColor() || t.getShape() == tile.getShape()) {
                    adjacentCompatibleCount++;
                    countedCells.add(cell);
                }
            }
        }

        // Points = nombre de tuiles compatibles adjacentes
        int points = adjacentCompatibleCount;

        if (adjacentCompatibleCount > 1) {
            points = adjacentCompatibleCount;
        }

        // Bonus sunstone +1 si la tuile est posée sur une sunstone
        if (isSunstone(row, col)) {
            points += 1;
        }

        System.out.println(currentPlayer.getName() + " marque " + points + " point(s) !");
        return points;
    }

    private void ajouterPointsAuJoueur(Player player, int points) {
        if (player == player1) {
            scorePlayer1 += points;
            scorePlayer1Label.setText(player1.getName() + " : " + scorePlayer1);
        } else if (player == player2) {
            scorePlayer2 += points;
            scorePlayer2Label.setText(player2.getName() + " : " + scorePlayer2);
        }
    }

    private boolean finDePartie() {
        // Partie finie si les 2 racks sont vides
        boolean racksVides = player1.getRack().getTiles().isEmpty() && player2.getRack().getTiles().isEmpty();
        boolean poolsVides = player1.getPool().isEmpty() && player2.getPool().isEmpty();
        return racksVides && poolsVides;
    }

    private void afficherVainqueur() {
        String gagnant;
        if (scorePlayer1 > scorePlayer2) {
            gagnant = player1.getName();
        } else if (scorePlayer2 > scorePlayer1) {
            gagnant = player2.getName();
        } else {
            gagnant = "Égalité";
        }
        infoLabel.setText("Partie terminée ! Vainqueur : " + gagnant);
        infoLabel.setTextFill(Color.RED);
        racks.setDisable(true);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;

        racks.getChildren().clear();
        rackDisplay = createRackDisplay(currentPlayer.getName() + " à ton tour de jouer :", currentPlayer.getRack());
        racks.getChildren().add(rackDisplay);

        infoLabel.setText(currentPlayer.getName() + ", à ton tour !");
        infoLabel.setTextFill(Color.BLUE);
    }

    private VBox createRackDisplay(String playerName, Rack rack) {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);

        HBox rackBox = new HBox(5);
        rackBox.setAlignment(Pos.CENTER);

        for (Tile tile : rack.getTiles()) {
            Label tileLabel = createTileLabel(tile);

            tileLabel.setOnDragDetected(event -> {
                ClipboardContent content = new ClipboardContent();
                content.putString("tile");
                tileLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
                event.consume();
            });

            tileMap.put(tileLabel, tile);
            rackBox.getChildren().add(tileLabel);
        }

        vbox.getChildren().addAll(rackBox);
        return vbox;
    }

    private Label createTileLabel(Tile tile) {
        Label label = new Label(getShapeSymbol(tile.getShape()));
        label.setFont(Font.font("Segoe UI Emoji", 20));
        label.setTextFill(mapColorToFX(tile.getColor()));
        label.setStyle("-fx-border-color: black; -fx-padding: 4; -fx-background-color: white;");
        return label;
    }

    private Color mapColorToFX(latice.game.Color color) {
        return switch (color) {
            case YELLOW -> Color.YELLOW;
            case MAGENTA -> Color.MAGENTA;
            case NAVY -> Color.NAVY;
            case RED -> Color.RED;
            case GREEN -> Color.GREEN;
            case TEAL -> Color.TEAL;
            default -> Color.BLACK;
        };
    }

    private String getShapeSymbol(Shape shape) {
        return switch (shape) {
            case FEATHER -> "🪶";
            case BIRD -> "🐦";
            case TURTLE -> "🐢";
            case FLOWER -> "🌸";
            case GECKO -> "🦎";
            case DOLPHIN -> "🐬";
            default -> "?";
        };
    }

    private boolean isSunstone(int row, int col) {
        return (row == 0 && (col == 0 || col == 4 || col == 8)) ||
               (row == 1 && (col == 1 || col == 7)) ||
               (row == 2 && (col == 2 || col == 6)) ||
               (row == 4 && (col == 0 || col == 8)) ||
               (row == 6 && (col == 2 || col == 6)) ||
               (row == 7 && (col == 1 || col == 7)) ||
               (row == 8 && (col == 0 || col == 4 || col == 8));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
