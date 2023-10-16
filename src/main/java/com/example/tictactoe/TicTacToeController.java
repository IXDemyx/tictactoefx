package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TicTacToeController {
    @FXML
    private Button buttonTictactoe;
    @FXML
    private GridPane gridPlayfield;
    @FXML
    private Label labelInfo;
    @FXML
    private Label labelGame;
    private char currentPlayer = 'X';
    private final Button[][] board = new Button[3][3];
    private boolean gameOver = false;


    @FXML
    public void initTictactoe() {
        buttonTictactoe.setVisible(false);
        labelGame.setText("Tic-Tac-Toe");
        labelInfo.setVisible(true);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int r = row;
                final int c = col;
                Button btn = new Button();

                btn.setPrefSize(150, 150);
                btn.setStyle("-fx-font-size:44");
                btn.setOnAction(e -> onButtonClick(r,c));
                board[row][col] = btn;
                gridPlayfield.add(btn, col, row);
            }
        }
    }

    public void onButtonClick(int row, int col) {
        if (gameOver || board[row][col].getText().equals("X") || board[row][col].getText().equals("O")) {
            return;
        }

        board[row][col].setText(String.valueOf(currentPlayer));

        if (checkForWin(row, col)) {
            showWinner();
            return;
        }

        if (checkForDraw()) {
            showDraw();
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        labelInfo.setText("Es ist " + currentPlayer + "'s Zug!");
    }

    private boolean checkForWin(int row, int col) {
        String symbol = String.valueOf(currentPlayer);

        // Check row
        if (board[row][0].getText().equals(symbol) &&
                board[row][1].getText().equals(symbol) &&
                board[row][2].getText().equals(symbol)) {
            return true;
        }

        // Check column
        if (board[0][col].getText().equals(symbol) &&
                board[1][col].getText().equals(symbol) &&
                board[2][col].getText().equals(symbol)) {
            return true;
        }

        // Check diagonals
        if (row == col || row + col == 2) {
            if (board[0][0].getText().equals(symbol) &&
                    board[1][1].getText().equals(symbol) &&
                    board[2][2].getText().equals(symbol)) {
                return true;
            }
            return board[0][2].getText().equals(symbol) &&
                    board[1][1].getText().equals(symbol) &&
                    board[2][0].getText().equals(symbol);
        }

        return false;
    }

    private void showWinner() {
        gameOver = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Spieler " + currentPlayer + " hat gewonnen!");
        alert.setContentText("Spiel neustarten?");
        alert.setResizable(false);
        ButtonType restartButton = new ButtonType("Neustart");
        ButtonType changeGame = new ButtonType("Spiel wechseln");
        alert.getButtonTypes().setAll(restartButton, changeGame);
        labelInfo.setText(currentPlayer + " hat gewonnen!");
        alert.showAndWait().ifPresent(response -> {
            if (response == restartButton) {
                restartTicTacToe();
            } else if (response == changeGame) {
                openStartMenu();
            }
        });
    }

    private void openStartMenu() {
        System.out.println("Startmenu");
    }

    private void restartTicTacToe() {
        labelInfo.setText("Neues Spiel!");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameOver = false;
                board[row][col].setText("");
            }
        }
    }

    private boolean checkForDraw() {
        for (Button[] row : board) {
            for (Button button : row) {
                if (!button.getText().equals("X") && !button.getText().equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showDraw() {
        gameOver = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Unentschieden!");
        labelInfo.setText("Unentschieden!");
        alert.setContentText("Spiel neustarten?");
        alert.setResizable(false);
        ButtonType restartButton = new ButtonType("Neustart");
        alert.getButtonTypes().setAll(restartButton);
        labelInfo.setText(currentPlayer + " hat gewonnen!");
        alert.showAndWait().ifPresent(response -> {
            if (response == restartButton) {
                restartTicTacToe();
            }
        });
    }
}