package com.example.simonrace;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

public class DiceGame {
    private final static String buttonStyle = "-fx-font: 20px Serif; -fx-padding: 10; -fx-background-color: #f8d6a8; -fx-pref-width: 250px";
    private final static String textStyle = "-fx-font: 26px Tahoma; -fx-stroke: black; -fx-stroke-width: 1;";
    private final static String turnStyle = "-fx-font: 40px Tahoma; -fx-stroke: black; -fx-fill: #f8d6a8;-fx-stroke-width: 1;";
    private final static String scoreBtnStyle = "-fx-font: 8px Tahoma;-fx-background-color: #f8d6a8;-fx-border-radius: 100;-fx-pref-width: 250px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: #000;-fx-stroke: #000;";
    private static Text squaresTitle, squaresTxt = new Text(), directionTitle, directionTxt = new Text(), playerTurnText;
    private static int squaresDice, directionDice;
    static String[] direction = {"forward", "forward", "backward", "miss a turn"};
    private static Random random;
    private static boolean isRollDirClicked = false;
    private static boolean isRollSquClicked = false;
    private static Button squaresRoll, directionRoll, move;
    private static VBox area;
    private static HBox playerBox, squaresBox, directionBox;
    static String player1Name;
    static String player2Name;
    static Button scoreBtn;

    /**
     * This method is used to set DiceBoard view and return a VBox
     * */
    public static VBox setDice() {
        /*set UI components**/
        squaresRoll = new Button("Roll Dice for Squares");
        squaresRoll.setStyle(buttonStyle);
        squaresRoll.setOnAction(event -> rollForSquares());

        directionRoll = new Button("Roll Dice for Direction");
        directionRoll.setStyle(buttonStyle);
        directionRoll.setOnAction(event -> rollForDirection());

        move = new Button("Move");
        move.setStyle(buttonStyle);
        move.setOnAction(event -> movePlayer());

        squaresTitle = new Text("Move Squares: ");
        squaresTitle.setStyle(textStyle);
        squaresTxt.setStyle(textStyle);

        directionTitle = new Text("Move Direction: ");
        directionTitle.setStyle(textStyle);
        directionTxt.setStyle(textStyle);

        playerTurnText = new Text(GameBoard.getPlayerTurn() ? player1Name + "'s turn" : player2Name + "'s turn");
        playerTurnText.setStyle(turnStyle);

        playerBox = new HBox(playerTurnText);

        squaresBox = new HBox(squaresTitle, squaresTxt);
        directionBox = new HBox(directionTitle, directionTxt);
        scoreBtn = new Button("Score Board");
        scoreBtn.setLayoutX(370);
        scoreBtn.setLayoutY(420);
        scoreBtn.setStyle(scoreBtnStyle);
        scoreBtn.setOnAction(event -> {
            UserLogin.goToScoreBoard();
        });
        area = new VBox(playerBox, squaresBox, directionBox, squaresRoll, directionRoll, move);
        area.setSpacing(30.0);
        return area;
    }

    public static int getDirectionDice() {
        return directionDice;
    }

    public static int getSquaresDice() {
        return squaresDice;
    }

    /**
     * This method is used to roll a 4-sided dice to determine how many squares a player can move: 1, 2, 3, or 4 squares,
     * */
    static void rollForSquares() {
        if (isRollSquClicked) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            showDialog(alert, "you can only click once", "information dialog", null);
            return;
        }
        isRollSquClicked = true;
        if (random == null) {
            random = new Random();
        }
        squaresDice = random.nextInt(4) + 1;
        squaresTxt.setText(Integer.toString(squaresDice));
    }

    /**
     * This method is used to roll another 4-sided dice determines in what direction, forward, forward, backward, miss a turn
     * */
    static void rollForDirection() {
        if (isRollDirClicked) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            showDialog(alert, "you can only click once", "information dialog", null);
            return;
        }
        isRollDirClicked = true;
        if (random == null) {
            random = new Random();
        }
        directionDice = random.nextInt(4);
        directionTxt.setText(direction[directionDice]);
    }

    public static void setForNextTurn(String t) {
        getPlayerTurnText().setText(t);
        clearRollState();
        squaresTxt.setText("");
        directionTxt.setText("");
    }

    /**
     * This method is used to update UI component and update players data in file when one player already win the game
     * @param t A parameter which indicates the name of current player
     * */
    public static void setForWin(String t) {
        getPlayerTurnText().setText(t + " win!!");
        clearRollState();
        squaresTxt.setText("");
        area.getChildren().remove(squaresRoll);
        area.getChildren().remove(directionRoll);
        area.getChildren().remove(move);
        area.getChildren().remove(squaresBox);
        area.getChildren().remove(directionBox);
        area.getChildren().add(scoreBtn);
        try {
            ScoreBoard.updatePlayer(t, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Text getPlayerTurnText() {
        return playerTurnText;
    }

    public static void movePlayer() {
        if (squaresTxt.getText() == null || squaresTxt.getText() == "" || directionTxt.getText() == null || directionTxt.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            showDialog(alert, "please roll for direction and squares", "information dialog", null);
            return;
        }
        GameBoard.move(directionDice, squaresDice);
    }

    public static void showDialog(Alert alert, String content, String title, String header) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void clearRollState() {
        isRollDirClicked = false;
        isRollSquClicked = false;
    }
}
