package com.example.simonrace;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class UserLogin extends Application {
    private final static String inputText = "-fx-font: 12px Tahoma; -fx-pref-height: 30px; -fx-pref-width: 200px;-fx-border-radius: 4px;-fx-prompt-text-fill: #ccc;";
    private final static String inputLabel = "-fx-font: 8px Tahoma;-fx-pref-height: 30px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: #8a8686; -fx-stroke: #8a8686;";
    private final static String textStyle = "-fx-font: 40px Tahoma; -fx-stroke: black; -fx-stroke-width: 2;";
    private final static String startBtnStyle = "-fx-font: 8px Tahoma;-fx-background-color: #fff;-fx-border-radius: 100;-fx-pref-width: 200px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: #8a8686;-fx-stroke: #8a8686;";
    private final static String scoreBtnStyle = "-fx-font: 6px Tahoma;-fx-background-color: transaprent;-fx-pref-width: 200px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: #000;";
    public static final int SQUARE_SIZE = 50;
    public static final double WIDTH = 9, HEIGHT = 12;
    static Scene loginScene;
    static Stage tmpStage;

    static Button scoreBtn;

    /**
     * This method is used to set UserLogin view
     * */
    @Override
    public void start(Stage stage) throws Exception {
        /*set UI components*/
        Label title = new Label("Simon’s Race");
        title.setLayoutX(350);
        title.setLayoutY(100);
        title.setStyle(textStyle);

        Label label1 = new Label("Player1:");
        label1.setLayoutX(280);
        label1.setLayoutY(210);
        label1.setStyle(inputLabel);

        TextField name1 = new TextField();
        name1.setLayoutX(370);
        name1.setLayoutY(210);
        name1.setStyle(inputText);
        name1.setPromptText("Please input player1's name");

        Label label2 = new Label("Player2:");
        label2.setLayoutX(280);
        label2.setLayoutY(270);
        label2.setStyle(inputLabel);

        TextField name2 = new TextField();
        name2.setLayoutX(370);
        name2.setLayoutY(270);
        name2.setStyle(inputText);
        name2.setPromptText("Please input player2's name");

        Button loginBtn = new Button("Start");
        loginBtn.setLayoutX(370);
        loginBtn.setLayoutY(350);
        loginBtn.setStyle(startBtnStyle);

        scoreBtn = new Button("Score Board");
        scoreBtn.setLayoutX(370);
        scoreBtn.setLayoutY(400);
        scoreBtn.setStyle(scoreBtnStyle);

        AnchorPane loginPane = new AnchorPane();
        loginPane.getChildren().addAll(title, label1, name1, label2, name2, loginBtn, scoreBtn);

        loginScene = new Scene(loginPane, WIDTH * SQUARE_SIZE * 2, HEIGHT * SQUARE_SIZE);
        loginPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#f8d6a8"), null, null)));

        loginBtn.setOnAction(event -> {
            if (name1.getText().trim().length() == 0 || name2.getText().trim().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                DiceGame.showDialog(alert, "please input players' name", "information dialog", null);
                return;
            }
            GameBoard.player1Name = name1.getText();
            GameBoard.player2Name = name2.getText();
            DiceGame.player1Name = name1.getText();
            DiceGame.player2Name = name2.getText();

            /*update players number of plays*/
            try {
                ScoreBoard.updatePlayer(name1.getText(), false);
                ScoreBoard.updatePlayer(name2.getText(), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(GameBoard.getGameBoard());
        });

        scoreBtn.setOnAction(event -> {
            goToScoreBoard();
        });

        stage.setScene(loginScene);
        stage.setTitle("Simon's Race");
        tmpStage = stage;
        stage.show();
    }

    public static void backToUserLogin() {
        tmpStage.setScene(loginScene);
    }

    public static void goToScoreBoard() {
        try {
            tmpStage.setScene(ScoreBoard.getScoreBoard());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
