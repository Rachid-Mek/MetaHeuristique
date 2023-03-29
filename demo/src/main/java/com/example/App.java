package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * JavaFX App
 */

class SizeInputForm extends GridPane {
    TextField sizeInput = new TextField();
    Label sizeInputLabel;
    Button sizeInputButton;
    int size;

    public SizeInputForm(Function<Integer, Void> onChange) {
        sizeInput.setMaxWidth(50);
        sizeInputLabel = new Label("Enter size of chessboard");
        sizeInputButton = new Button("Submit");
        // add event listener to button
        sizeInputButton.setOnAction(e -> {
            size = Integer.parseInt(sizeInput.getText());
            onChange.apply(size);
        });
        addComponents();
    }

    public void addComponents() {
        add(sizeInputLabel, 0, 0);
        add(sizeInput, 1, 0);
        add(sizeInputButton, 2, 0);
        this.setHgap(10);
    }

    public int getSize() {
        return size;
    }
}

class SearchButton extends Button {
    public SearchButton(String text) {
        super(text);
        this.setMinWidth(150);
    }
}

public class App extends Application {
    Insets insets = new Insets(10);
    Button button;
    SizeInputForm sizeInputForm;
    Button dfsButton = new SearchButton("DFS");
    Button bfsButton = new SearchButton("BFS");
    Button heuristic1 = new SearchButton("Heuristic 1");
    Button heuristic2 = new SearchButton("Heuristic 2");
    ChessBoard chessBoard;
    private static Scene scene;
    private int size = 8;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("N Queens");

        GridPane mainPane = new GridPane();
        chessBoard = new ChessBoard(size);
        sizeInputForm = new SizeInputForm(size -> {
            chessBoard.setSize(size);
            this.size = size;
            return null;
        });

        dfsButton.setOnAction(e -> {
            NQueens nQueens = new NQueens(size);
            nQueens.solveDFS();
            handleButtonClick(nQueens);
        });

        bfsButton.setOnAction(e -> {
            NQueens nQueens = new NQueens(size);
            nQueens.solveBFS();
            handleButtonClick(nQueens);
        });

        heuristic1.setOnAction(e -> {
            NQueens nQueens = new NQueens(size);
            nQueens.solveH1();
            handleButtonClick(nQueens);
        });

        heuristic2.setOnAction(e -> {
            NQueens nQueens = new NQueens(size);
            nQueens.solveH2();
            handleButtonClick(nQueens);
        });

        // collect all buttons in a gridpane and add to borderpane, give them the same
        // width and separate them with a space
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.add(dfsButton, 0, 0);
        gridPane.add(bfsButton, 0, 1);
        gridPane.add(heuristic1, 0, 2);
        gridPane.add(heuristic2, 0, 3);

        mainPane.setHgap(20);
        mainPane.setVgap(20);
        mainPane.add(sizeInputForm, 0, 0);
        mainPane.add(gridPane, 0, 1);
        mainPane.add(chessBoard, 1, 1);
        scene = new Scene(mainPane, 700, 700);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private ArrayList<Integer[]> getQueenPositions(List<Integer> solution) {
        ArrayList<Integer[]> queenPositions = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            queenPositions.add(new Integer[] { i, solution.get(i) });
        }
        return queenPositions;
    }

    // generic method to handle button click, get a function as parameter
    private void handleButtonClick(NQueens nQueens) {
        List<Integer> solution = nQueens.solutions.get(0);
        chessBoard.setSize(size);
        chessBoard.addQueens(getQueenPositions(solution));
    }
}
