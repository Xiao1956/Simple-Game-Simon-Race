package com.example.simonrace;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;


public class GameTest {

    @Test
    public void testDice() {
        DiceGame.rollForSquares();
        assertEquals(true, DiceGame.getSquaresDice() == 1 || DiceGame.getSquaresDice() == 2 || DiceGame.getSquaresDice() == 3 || DiceGame.getSquaresDice() == 4);

        DiceGame.rollForDirection();
        assertEquals(true, DiceGame.direction[DiceGame.getDirectionDice()] == "forward" || DiceGame.direction[DiceGame.getDirectionDice()] == "backward" || DiceGame.direction[DiceGame.getDirectionDice()] == "miss a turn");

    }

    @Test
    public void testGame() {
        GameBoard.initBoard();
        boolean test = GameBoard.isMeetObstruction(100.0, 500.0, 1, 2, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test);

        boolean test1 = GameBoard.isMeetObstruction(100.0, 500.0, 3, 2, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test1);

        /*test fence1*/
        boolean test2 = GameBoard.isMeetObstruction(150.0, 250.0, 2, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(true, test2);
        boolean test2_1 = GameBoard.isMeetObstruction(150.0, 250.0, 3, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test2_1);
        boolean test2_2 = GameBoard.isMeetObstruction(150.0, 250.0, 1, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test2_2);
        boolean test2_3 = GameBoard.isMeetObstruction(150.0, 250.0, 2, 3, GameBoard.player2, GameBoard.player1);
        assertEquals(true, test2_3);

        /*test fence2*/
        boolean test3 = GameBoard.isMeetObstruction(300.0, 550.0, 0, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(true, test3);
        boolean test3_1 = GameBoard.isMeetObstruction(300.0, 550.0, 2, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test3_1);
        boolean test3_2 = GameBoard.isMeetObstruction(300.0, 550.0, 3, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test3_2);
        boolean test3_3 = GameBoard.isMeetObstruction(300.0, 550.0, 0, 2, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test3_3);

        /*test fence3*/
        boolean test4 = GameBoard.isMeetObstruction(100.0, 300.0, 1, 4, GameBoard.player1, GameBoard.player2);
        assertEquals(true, test4);
        boolean test4_1 = GameBoard.isMeetObstruction(100.0, 300.0, 3, 4, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test4_1);
        boolean test4_2 = GameBoard.isMeetObstruction(100.0, 300.0, 2, 4, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test4_2);
        boolean test4_3 = GameBoard.isMeetObstruction(100.0, 300.0, 1, 1, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test4_3);

        /*test fence4*/
        boolean test5 = GameBoard.isMeetObstruction(50.0, 350.0, 2, 1, GameBoard.player1, GameBoard.player2);
        assertEquals(true, test5);
        boolean test5_1 = GameBoard.isMeetObstruction(50.0, 350.0, 1, 1, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test5_1);
        boolean test5_2 = GameBoard.isMeetObstruction(50.0, 350.0, 3, 1, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test5_2);

        /*test fence5*/
        boolean test6 = GameBoard.isMeetObstruction(400.0, 300.0, 0, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(true, test6);
        boolean test6_1 = GameBoard.isMeetObstruction(400.0, 300.0, 2, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test6_1);
        boolean test6_2 = GameBoard.isMeetObstruction(400.0, 300.0, 3, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test6_2);

        /*test back obstruction*/
        boolean test7 = GameBoard.isMeetObstruction(200.0, 150.0, 1, 4, GameBoard.player2, GameBoard.player1);
        assertEquals(false, test7);

        GameBoard.pane.setLeftAnchor(GameBoard.player1, 200.0);
        GameBoard.pane.setTopAnchor(GameBoard.player1, 150.0);
        boolean test7_2 = GameBoard.isMeetObstruction(200.0, 150.0, 1, 2, GameBoard.player1, GameBoard.player2);
        assertEquals(true, test7_2);
        assertEquals(true, GameBoard.pane.getLeftAnchor(GameBoard.player1) == 100.0);
        assertEquals(true, GameBoard.pane.getTopAnchor(GameBoard.player1) == 550.0);

        /*test swap obstruction*/
        boolean test8 = GameBoard.isMeetObstruction(250.0, 200.0, 1, 3, GameBoard.player1, GameBoard.player2);
        assertEquals(false, test8);

        GameBoard.pane.setLeftAnchor(GameBoard.player1, 100.0);
        GameBoard.pane.setTopAnchor(GameBoard.player1, 550.0);
        GameBoard.pane.setLeftAnchor(GameBoard.player2, 250.0);
        GameBoard.pane.setTopAnchor(GameBoard.player2, 200.0);
        boolean test8_1 = GameBoard.isMeetObstruction(250.0, 200.0, 1, 1, GameBoard.player2, GameBoard.player1);
        assertEquals(true, test8_1);
        assertEquals(true, GameBoard.pane.getLeftAnchor(GameBoard.player1) == 250.0);
        assertEquals(true, GameBoard.pane.getTopAnchor(GameBoard.player1) == 200.0);
        assertEquals(true, GameBoard.pane.getLeftAnchor(GameBoard.player2) == 100.0);
        assertEquals(true, GameBoard.pane.getTopAnchor(GameBoard.player2) == 550.0);
    }

    @Test
    public void testPlayerList() throws IOException, ClassNotFoundException {
        PlayerInfo player = new PlayerInfo("testPlayerList66", 0, 0);
        ArrayList<PlayerInfo> playerList= PlayerInfo.read();
        for (PlayerInfo p: playerList) {
            assertEquals(true, p instanceof PlayerInfo);
        }

        int oldLength = playerList.size();
        PlayerInfo.write(player, playerList);
        ArrayList<PlayerInfo> newPlayerList= PlayerInfo.read();
        assertEquals(1,  newPlayerList.size() - oldLength);

        boolean isExist = false;
        for(PlayerInfo p: playerList) {
            if (p.getName().equals(player.getName()) && p.getWinNum()== player.getWinNum() && p.getPlayNum() == player.getPlayNum()) {
                isExist = true;
            }
        }
        assertEquals(true, isExist);
    }

    @Test
    public void testScoreBoard() throws IOException, ClassNotFoundException {
        /*update a new player*/
        ScoreBoard.updatePlayer("testScoreBoard77", false);
        ArrayList<PlayerInfo> playerList= PlayerInfo.read();
        assertEquals(0,  playerList.get(playerList.size() - 1).getWinNum());
        assertEquals(1,  playerList.get(playerList.size() - 1).getPlayNum());

        ScoreBoard.updatePlayer("testScoreBoard77", true);
        ArrayList<PlayerInfo> newPlayerList= PlayerInfo.read();
        assertEquals(1,  newPlayerList.get(newPlayerList.size() - 1).getWinNum());
        assertEquals(1,  newPlayerList.get(newPlayerList.size() - 1).getPlayNum());
    }

}

