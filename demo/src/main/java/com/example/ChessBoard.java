package com.example;

import java.util.ArrayList;

// chessboard with dynamic size, will be imported to the main app

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class QueenRectangle extends Rectangle {

    // feel with image of queen from resources/com/example/wqueen.png

    // fill the rectangle with the queen image
    public QueenRectangle() {
        super(50, 50);
        Image image = new Image(getClass().getResource("images/wqueen.png").toString());
        setFill(new ImagePattern(image));
    }
}

class Square extends Rectangle {

    public Square(Color color) {
        super(50, 50);
        setFill(color);
    }
}

public class ChessBoard extends GridPane {
    private int size;

    public ChessBoard(int size) {
        // create a hashset of queen positions
        setSize(size);
    }

    public void addQueens(ArrayList<Integer[]> queenPositions) {
        for (Integer[] queenPosition : queenPositions) {
            add(new QueenRectangle(), queenPosition[0], queenPosition[1]);
        }
    }

    public void setSize(int size) {
        getChildren().clear();
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 == 0) {
                    add(new Square(Color.WHITE), i, j);
                } else {
                    add(new Square(Color.BLACK), i, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}