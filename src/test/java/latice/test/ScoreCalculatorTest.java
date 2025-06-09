package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import latice.application.Main;
import latice.game.*;

 	class ScoreCalculatorTest {

	    @Test
	    void testCalculateScore_noAdjacentTile_shouldReturn0() {
	        Board board = new Board();
	        Tile tile = new Tile(Color.RED, Shape.BIRD);
	        board.getCellPositionOnBoard(4, 4).setTile(tile); // Position centrale

	        Integer score = Main.calculateScore(board, 4, 4);

	        assertEquals(0, score);
	    }

	    @Test
	    void testCalculateScore_oneMatchingColor_shouldReturn1() {
	        Board board = new Board();

	        // Placer une tuile
	        Tile tile1 = new Tile(Color.RED, Shape.BIRD);
	        board.getCellPositionOnBoard(4, 4).setTile(tile1);

	        // Placer une tuile adjacente de même couleur
	        Tile tile2 = new Tile(Color.RED, Shape.FEATHER);
	        board.getCellPositionOnBoard(4, 5).setTile(tile2); // à droite

	        Integer score = Main.calculateScore(board, 4, 4);

	        assertEquals(1, score);
	    }

	    @Test
	    void testCalculateScore_matchingColorAndShape_shouldReturn2() {
	        Board board = new Board();

	        Tile tile = new Tile(Color.RED, Shape.BIRD);
	        board.getCellPositionOnBoard(4, 4).setTile(tile);

	        // Une tuile de même couleur à gauche
	        board.getCellPositionOnBoard(4, 3).setTile(new Tile(Color.RED, Shape.FEATHER));

	        // Une tuile de même forme en haut
	        board.getCellPositionOnBoard(3, 4).setTile(new Tile(Color.NAVY, Shape.BIRD));

	        Integer score = Main.calculateScore(board, 4, 4);

	        assertEquals(2, score);
	    }
	    
	   
	     
	}


