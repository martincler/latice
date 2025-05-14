package latice.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import latice.game.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFX extends Application {

    private static final int TILE_SIZE = 50;
    private static final int BOARD_SIZE = 9;

    @Override
    public void start(Stage primaryStage) {
        
    	    // Génération des tuiles
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = new ArrayList<>(tileSet.getTiles());
        Collections.shuffle(allTiles);

        // Création de deux pioches de 36 tuiles
        List<Tile> tilesForP1 = new ArrayList<>(allTiles.subList(0, 36));
        List<Tile> tilesForP2 = new ArrayList<>(allTiles.subList(36, 72));

        Pool pool1 = new Pool(tilesForP1);
        Pool pool2 = new Pool(tilesForP2);

        Player player1 = new Player("Joueur1", pool1);
        Player player2 = new Player("Joueur2", pool2);

        // Choix aléatoire du joueur qui commence
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;

        // Conteneur principal
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Plateau
        GridPane board = createBoard();
        root.getChildren().add(board);

        // Affichage du rack uniquement du joueur choisi
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

    private GridPane createBoard() {
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
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                }

                else if (isSunstone(row, col)) {
                    label.setText("☀");
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                }

                cell.getChildren().add(label);
                grid.add(cell, col, row);
            }
        }

        return grid;
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

    private VBox createRackDisplay(String playerName, Rack rack) {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(playerName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox rackBox = new HBox(5);
        rackBox.setAlignment(Pos.CENTER);

        for (Tile tile : rack.getTiles()) {
            String shapeSymbol = getShapeSymbol(tile.getShape());
            Label tileLabel = new Label(shapeSymbol);
            tileLabel.setFont(Font.font("Segoe UI Emoji", 20));
            tileLabel.setTextFill(mapColorToFX(tile.getColor()));
            tileLabel.setStyle("-fx-border-color: black; -fx-padding: 4; -fx-background-color: white;");
            rackBox.getChildren().add(tileLabel);
        }

        vbox.getChildren().addAll(nameLabel, rackBox);
        return vbox;
    }


    private Color mapColorToFX(latice.game.Color color) {
        switch (color) {
            case YELLOW: return Color.YELLOW;
            case MAGENTA: return Color.MAGENTA;
            case NAVY: return Color.NAVY;
            case RED: return Color.RED;
            case GREEN: return Color.GREEN;
            case TEAL: return Color.TEAL;
            default: return Color.BLACK;
        }
    }

    private String getShapeSymbol(Shape shape) {
        switch (shape) {
            case FEATHER: return "🪶";
            case BIRD: return "🐦";
            case TURTLE: return "🐢";
            case FLOWER: return "🌸";
            case GECKO: return "🦎";
            case DOLPHIN: return "🐬";
            default: return "?";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
