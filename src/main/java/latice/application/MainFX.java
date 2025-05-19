package latice.application;

import javafx.application.Application;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage primaryStage) {
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = new ArrayList<>(tileSet.getTiles());
        Collections.shuffle(allTiles);

        Pool pool1 = new Pool(allTiles.subList(0, 36));
        Pool pool2 = new Pool(allTiles.subList(36, 72));
        Player player1 = new Player("Joueur1", pool1);
        Player player2 = new Player("Joueur2", pool2);
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        GridPane boardGrid = createBoardGrid();
        root.getChildren().add(boardGrid);

        HBox racks = new HBox(50);
        racks.setAlignment(Pos.CENTER);
        VBox rackDisplay = createRackDisplay(currentPlayer.getName() + " à ton tour de jouer :", currentPlayer.getRack());
        racks.getChildren().add(rackDisplay);
        root.getChildren().add(racks);

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
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightblue;");

                Label label = new Label();

                if (row == 4 && col == 4) {
                    label.setText("🌙");
                } else if (isSunstone(row, col)) {
                    label.setText("☀");
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
                            BoardCell boardCell = boardModel.getCell(r, c);
                            if (boardCell.isEmpty()) {
                                Label newLabel = createTileLabel(draggedTile);
                                cell.getChildren().clear();
                                cell.getChildren().add(newLabel);
                                boardCell.placeTile(draggedTile);
                                sourceLabel.setVisible(false);
                                event.setDropCompleted(true);
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

    private VBox createRackDisplay(String playerName, Rack rack) {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(playerName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

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

        vbox.getChildren().addAll(nameLabel, rackBox);
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

