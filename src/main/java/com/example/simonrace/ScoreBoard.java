package com.example.simonrace;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoard {
    private final static String backBtnStyle = "-fx-font: 6px Tahoma;-fx-background-color: #fff;-fx-border-radius: 4;-fx-pref-width: 60px;-fx-pref-height: 20px;-fx-font-size: 20px;-fx-text-fill: #000;-fx-background-color: #f8d6a8;-fx-font-size: 14px;-fx-font-weight: bold;";
    static AnchorPane pane = new AnchorPane();
    static Scene scoreScene;
    /**
     * This method is used to set ScoreBoard view and return a Scene
     * */
    public static Scene getScoreBoard() throws IOException, ClassNotFoundException {
        if (scoreScene == null) {
            scoreScene = new Scene(pane, UserLogin.WIDTH * UserLogin.SQUARE_SIZE * 2, UserLogin.HEIGHT * UserLogin.SQUARE_SIZE);
        }

        /*get players data from file*/
        ArrayList<PlayerInfo> playerList = PlayerInfo.read();
        /*sort playerList by winNum*/
        Collections.sort(playerList);

        /*set UI component*/
        Label label = new Label("Top 10 Players");
        label.setLayoutX(380);
        label.setLayoutY(20);
        label.setFont(new Font(24));

        Button backBtn = new Button("Back");
        backBtn.setLayoutX(40);
        backBtn.setLayoutY(25);
        backBtn.setStyle(backBtnStyle);

        /*set top 10 winner list*/
        TableView<PlayerInfo> tableview = new TableView<>();
        TableColumn<PlayerInfo, String> name = new TableColumn<>("Player Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(UserLogin.WIDTH * UserLogin.SQUARE_SIZE * 2 / 3);
        name.setStyle("-fx-alignment: center;");
        TableColumn<PlayerInfo, Integer> winNum = new TableColumn<>("Number of Wins");
        winNum.setCellValueFactory(new PropertyValueFactory<>("winNum"));
        winNum.setPrefWidth(UserLogin.WIDTH * UserLogin.SQUARE_SIZE * 2 / 3);
        winNum.setStyle("-fx-alignment: center;");
        TableColumn<PlayerInfo, Integer> playNum = new TableColumn<>("Number of Plays");
        playNum.setCellValueFactory(new PropertyValueFactory<>("playNum"));
        playNum.setPrefWidth(UserLogin.WIDTH * UserLogin.SQUARE_SIZE * 2 / 3);
        playNum.setStyle("-fx-alignment: center;");
        tableview.getColumns().addAll(name, winNum, playNum);
        /*get top 10 players */
        ArrayList<PlayerInfo> showList = new ArrayList<PlayerInfo>(playerList.subList(0, 10));
        for (PlayerInfo p : showList) {
            tableview.getItems().add(p);
        }
        tableview.setPrefWidth(UserLogin.WIDTH * UserLogin.SQUARE_SIZE * 2);
        tableview.setPrefHeight(UserLogin.HEIGHT * UserLogin.SQUARE_SIZE - 60);
        tableview.setLayoutY(60);
        pane.getChildren().addAll(backBtn, label, tableview);

        backBtn.setOnAction(event -> {
            UserLogin.backToUserLogin();
        });

        return scoreScene;
    }

    /**
     * This method is used to update player's data in file
     * */
    public static void updatePlayer(String name, boolean isWin) throws IOException, ClassNotFoundException {
        ArrayList<PlayerInfo> playerList = PlayerInfo.read();
        PlayerInfo player = null;
        int count = 0;
        for (PlayerInfo p : playerList) {
            if (p.getName().equals(name)) {
                player = new PlayerInfo(name, isWin ? p.getWinNum() + 1 : p.getWinNum(), isWin ? p.getPlayNum() : p.getPlayNum() + 1);
                count++;
            }
        }
        if (count == 0) {
            player = new PlayerInfo(name, 0, 1);
        }
        PlayerInfo.write(player, playerList);
    }

}
