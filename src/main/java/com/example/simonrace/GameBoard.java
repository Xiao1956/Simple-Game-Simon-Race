package com.example.simonrace;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.Optional;

public class GameBoard {
    private static boolean playerTurn = true;
    static StackPane player1 = new StackPane();
    static StackPane player2 = new StackPane();
    static ImageView swapObstacle;
    static ImageView backObstacle;
    static ImageView fenceObstacle1;
    static ImageView fenceObstacle2;
    static ImageView fenceObstacle3;
    static ImageView fenceObstacle4;
    static ImageView fenceObstacle5;

    public static final double WIDTH = com.example.simonrace.UserLogin.WIDTH;
    public static final double HEIGHT = com.example.simonrace.UserLogin.HEIGHT;
    public static final double SQUARE_SIZE = UserLogin.SQUARE_SIZE;
    private static Canvas canvas = new Canvas(WIDTH * UserLogin.SQUARE_SIZE, HEIGHT * UserLogin.SQUARE_SIZE);
    private static GraphicsContext finishArea = canvas.getGraphicsContext2D();
    private static GraphicsContext startArea = canvas.getGraphicsContext2D();

    static String player1Name;
    static String player2Name;
    static VBox area;
    static AnchorPane pane = new AnchorPane();
    static Image swap, back, fence;

    static String tmpDialogContent;
    static double tmpDialogNewY;
    static int tmpDialogLeftSquares;

    static boolean tmpIsSimpleDialog;

    /**
     * This method is used to set GameBoard view and return a Scene
     * */
    public static Scene getGameBoard() {
        beforeInit();
        initBoard();
        initArea();

        pane.getChildren().setAll(canvas, area, player1, player2, swapObstacle, backObstacle, fenceObstacle1, fenceObstacle2, fenceObstacle3, fenceObstacle4, fenceObstacle5);
        Scene gameScene = new Scene(pane, WIDTH * SQUARE_SIZE * 2, HEIGHT * SQUARE_SIZE);
        return gameScene;
    }

    public static void beforeInit() {
        swap = new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/images/swap.png");
        back = new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/images/back.png");
        fence = new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/images/fence.png");
    }

    public static void initArea() {
        area = DiceGame.setDice();
        pane.setLeftAnchor(area, SQUARE_SIZE * WIDTH + 50);
        pane.setTopAnchor(area, 30.0);
    }

    /**
     * This method is used to init all ui elements and view
     * */
    public static void initBoard() {
        drawBoard();
        drawObstructions();
        drawPlayers();

        pane.setTopAnchor(player1, SQUARE_SIZE * (HEIGHT - 1));
        pane.setLeftAnchor(player1, SQUARE_SIZE * 2.0);
        pane.setTopAnchor(player2, SQUARE_SIZE * (HEIGHT - 1));
        pane.setLeftAnchor(player2, SQUARE_SIZE * 6.0);
        // swap obstruction
        pane.setTopAnchor(swapObstacle, SQUARE_SIZE * (HEIGHT - 9));
        pane.setLeftAnchor(swapObstacle, SQUARE_SIZE * 5.0);
        // back obstruction
        pane.setTopAnchor(backObstacle, SQUARE_SIZE * (HEIGHT - 11));
        pane.setLeftAnchor(backObstacle, SQUARE_SIZE * 4.0);
        // fence obstruction
        pane.setTopAnchor(fenceObstacle1, SQUARE_SIZE * (HEIGHT - 6));
        pane.setLeftAnchor(fenceObstacle1, SQUARE_SIZE * 3.0);
        pane.setTopAnchor(fenceObstacle2, SQUARE_SIZE * (HEIGHT - 5));
        pane.setLeftAnchor(fenceObstacle2, SQUARE_SIZE * 6.0);
        pane.setTopAnchor(fenceObstacle3, SQUARE_SIZE * (HEIGHT - 8));
        pane.setLeftAnchor(fenceObstacle3, SQUARE_SIZE * 2.0);
        pane.setTopAnchor(fenceObstacle4, SQUARE_SIZE * (HEIGHT - 4));
        pane.setLeftAnchor(fenceObstacle4, SQUARE_SIZE * 1.0);
        pane.setTopAnchor(fenceObstacle5, SQUARE_SIZE * (HEIGHT - 7));
        pane.setLeftAnchor(fenceObstacle5, SQUARE_SIZE * 8.0);
    }

    public static void drawBoard() {
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        finishArea.setFill(Color.valueOf("#848484"));
        finishArea.fillRect(0, 0, WIDTH * SQUARE_SIZE, SQUARE_SIZE);

        /*draw checkerboard*/
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                GraphicsContext tmp = canvas.getGraphicsContext2D();
                if ((x + y) % 2 == 0) {
                    tmp.setFill(Color.valueOf("#f8d6a8"));
                } else {
                    tmp.setFill(Color.valueOf("#000"));
                }
                tmp.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        startArea.setFill(Color.valueOf("#848484"));
        startArea.fillRect(0, (HEIGHT - 1) * SQUARE_SIZE, WIDTH * SQUARE_SIZE, SQUARE_SIZE);

    }

    /**
     * This method is used to draw obstructions
     * */
    public static void drawObstructions() {
        swapObstacle = new ImageView();
        swapObstacle.setImage(swap);

        backObstacle = new ImageView();
        backObstacle.setImage(back);

        fenceObstacle1 = new ImageView();
        fenceObstacle1.setImage(fence);
        fenceObstacle2 = new ImageView();
        fenceObstacle2.setImage(fence);
        fenceObstacle3 = new ImageView();
        fenceObstacle3.setImage(fence);
        fenceObstacle4 = new ImageView();
        fenceObstacle4.setImage(fence);
        fenceObstacle5 = new ImageView();
        fenceObstacle5.setImage(fence);
    }

    /**
     * This method is used to draw players
     * */
    public static void drawPlayers() {
        Circle p1 = new Circle();
        p1.setCenterX(250);
        p1.setCenterY(250);
        p1.setRadius(SQUARE_SIZE / 2);
        p1.setFill(Color.valueOf("#e80200"));
        Text text1 = new Text(player1Name);
        text1.setBoundsType(TextBoundsType.VISUAL);
        player1.getChildren().addAll(p1, text1);

        Circle p2 = new Circle();
        p2.setCenterX(250);
        p2.setCenterY(250);
        p2.setRadius(SQUARE_SIZE / 2);
        p2.setFill(Color.valueOf("#c598f4"));
        Text text2 = new Text(player2Name);
        text2.setBoundsType(TextBoundsType.VISUAL);
        player2.getChildren().addAll(p2, text2);
    }

    /**
     * This method is used to control the movement of players
     * @param direction This is the first parameter which indicates the movement direction
     * @param squares This is the second parameter which indicates the movement squares
     * */
    public static void move(int direction, int squares) {
        double oldX = playerTurn ? pane.getLeftAnchor(player1) : pane.getLeftAnchor(player2);
        double oldY = playerTurn ? pane.getTopAnchor(player1) : pane.getTopAnchor(player2);
        StackPane curPlayer = playerTurn ? player1 : player2;
        StackPane opPlayer = !playerTurn ? player1 : player2;

        switch (direction) {
            case 0:
            case 1:
                if (!isMeetObstruction(oldX, oldY, direction, squares, curPlayer, opPlayer)) {
                    double newY = oldY - squares * SQUARE_SIZE <= 0 ? 0 : oldY - squares * SQUARE_SIZE;
                    /*arrive at finish area, the player wins*/
                    if (newY <= 0) {
                        DiceGame.setForWin(playerTurn ? player1Name : player2Name);
                        pane.setTopAnchor(curPlayer, 0.0);
                        return;
                    }
                    pane.setTopAnchor(curPlayer, newY); /* forward in a normal situation or forward to finish area */
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (tmpIsSimpleDialog) {
                        DiceGame.showDialog(alert, tmpDialogContent, "information dialog", null);
                    } else {
                        showDirectionDialog(alert, tmpDialogContent, "information dialog", null, curPlayer, tmpDialogNewY, tmpDialogLeftSquares);
                    }
                }
                break;
            case 2:
                if (!isMeetObstruction(oldX, oldY, direction, squares, curPlayer, opPlayer)) {
                    double newY2 = (oldY + squares * SQUARE_SIZE >= SQUARE_SIZE * (HEIGHT - 1)) ?
                            SQUARE_SIZE * (HEIGHT - 1) :
                            oldY + squares * SQUARE_SIZE;
                    pane.setTopAnchor(curPlayer, newY2); /* back in a normal situation or back to start line */
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (tmpIsSimpleDialog) {
                        DiceGame.showDialog(alert, tmpDialogContent, "information dialog", null);
                    } else {
                        showDirectionDialog(alert, tmpDialogContent, "information dialog", null, curPlayer, tmpDialogNewY, tmpDialogLeftSquares);
                    }
                }
                break;
        }
        playerTurn = !playerTurn;
        DiceGame.setForNextTurn(playerTurn ? player1Name + "'s turn" : player2Name + "'s turn");
    }

    /**
     * This method is used to control the logic of players movement
     * @param x This is the first parameter which indicates the left anchor of the current player
     * @param y This is the second parameter which indicates the top anchor of the current player
     * @param direction This is the third parameter which indicates the movement direction
     * @param squares This is the fourth parameter which indicates the movement squares
     * @param curPlayer This is the fifth parameter which indicates the current player
     * @param opPlayer This is the sixth parameter which indicates the next turn player
     * @return return true/false to indicate if this player meet an obstruction
     * */
    public static boolean isMeetObstruction(double x, double y, int direction, int squares, StackPane curPlayer, StackPane opPlayer) {
        boolean r = false;
        tmpIsSimpleDialog = false;
        if ((x == pane.getLeftAnchor(fenceObstacle1) && (direction == 0 || direction == 1) && y > pane.getTopAnchor(fenceObstacle1) && y - squares * SQUARE_SIZE <= pane.getTopAnchor(fenceObstacle1)) ||
                (x == pane.getLeftAnchor(fenceObstacle1) && direction == 2 && y < pane.getTopAnchor(fenceObstacle1) && y + squares * SQUARE_SIZE >= pane.getTopAnchor(fenceObstacle1))) { /* obstruction is fence1 */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(fenceObstacle1) + SQUARE_SIZE : pane.getTopAnchor(fenceObstacle1) - SQUARE_SIZE;
            tmpDialogContent = "you meet a fence obstruction, please choose another direction to move";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(fenceObstacle1)) / SQUARE_SIZE);

        } else if ((x == pane.getLeftAnchor(fenceObstacle2) && (direction == 0 || direction == 1) && y > pane.getTopAnchor(fenceObstacle2) && y - squares * SQUARE_SIZE <= pane.getTopAnchor(fenceObstacle2)) ||
                (x == pane.getLeftAnchor(fenceObstacle2) && direction == 2 && y < pane.getTopAnchor(fenceObstacle2) && y + squares * SQUARE_SIZE >= pane.getTopAnchor(fenceObstacle2))) { /* obstruction is fence2 */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(fenceObstacle2) + SQUARE_SIZE : pane.getTopAnchor(fenceObstacle2) - SQUARE_SIZE;
            tmpDialogContent = "you meet a fence obstruction, please choose another direction to move";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(fenceObstacle2)) / SQUARE_SIZE);

        } else if ((x == pane.getLeftAnchor(fenceObstacle3) && (direction == 0 || direction == 1) && y > pane.getTopAnchor(fenceObstacle3) && y - squares * SQUARE_SIZE <= pane.getTopAnchor(fenceObstacle3)) ||
                (x == pane.getLeftAnchor(fenceObstacle3) && direction == 2 && y < pane.getTopAnchor(fenceObstacle3) && y + squares * SQUARE_SIZE >= pane.getTopAnchor(fenceObstacle3))) { /* obstruction is fence3 */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(fenceObstacle3) + SQUARE_SIZE : pane.getTopAnchor(fenceObstacle3) - SQUARE_SIZE;
            tmpDialogContent = "you meet a fence obstruction, please choose another direction to move";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(fenceObstacle3)) / SQUARE_SIZE);

        } else if ((x == pane.getLeftAnchor(fenceObstacle4) && (direction == 0 || direction == 1) && y > pane.getTopAnchor(fenceObstacle4) && y - squares * SQUARE_SIZE <= pane.getTopAnchor(fenceObstacle4)) ||
                (x == pane.getLeftAnchor(fenceObstacle4) && direction == 2 && y < pane.getTopAnchor(fenceObstacle4) && y + squares * SQUARE_SIZE >= pane.getTopAnchor(fenceObstacle4))) { /* obstruction is fence4 */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(fenceObstacle4) + SQUARE_SIZE : pane.getTopAnchor(fenceObstacle4) - SQUARE_SIZE;
            tmpDialogContent = "you meet a fence obstruction, please choose another direction to move";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(fenceObstacle4)) / SQUARE_SIZE);

        } else if ((x == pane.getLeftAnchor(fenceObstacle5) && (direction == 0 || direction == 1) && y > pane.getTopAnchor(fenceObstacle5) && y - squares * SQUARE_SIZE <= pane.getTopAnchor(fenceObstacle5)) ||
                (x == pane.getLeftAnchor(fenceObstacle5) && direction == 2 && y < pane.getTopAnchor(fenceObstacle5) && y + squares * SQUARE_SIZE >= pane.getTopAnchor(fenceObstacle5))) { /* obstruction is fence5 */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(fenceObstacle5) + SQUARE_SIZE : pane.getTopAnchor(fenceObstacle5) - SQUARE_SIZE;
            tmpDialogContent = "you meet a fence obstruction, please choose another direction to move";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(fenceObstacle5)) / SQUARE_SIZE);

        } else if (x == pane.getLeftAnchor(backObstacle) && y - squares * SQUARE_SIZE == pane.getTopAnchor(backObstacle)) { /*obstruction is back */
            r = true;
            tmpIsSimpleDialog = true;
            tmpDialogContent = "you meet an obstruction, you need to go back to the start";
            pane.setTopAnchor(curPlayer, SQUARE_SIZE * (HEIGHT - 1));
            pane.setLeftAnchor(curPlayer, playerTurn ? SQUARE_SIZE * 2.0 : SQUARE_SIZE * 6.0);
        } else if (x == pane.getLeftAnchor(swapObstacle) &&
                ((direction == 0 || direction == 1) && (y - squares * SQUARE_SIZE == pane.getTopAnchor(swapObstacle)) ||
                        direction == 2 && (y + squares * SQUARE_SIZE == pane.getTopAnchor(swapObstacle)))) { /*obstruction is swap */
            r = true;
            tmpIsSimpleDialog = true;
            tmpDialogContent = "you meet an obstruction, you will swap the position of opponent";
            /*swap two players position*/
            double tmpX = pane.getLeftAnchor(opPlayer);
            double tmpY = pane.getTopAnchor(opPlayer);
            pane.setTopAnchor(opPlayer, pane.getTopAnchor(curPlayer));
            pane.setLeftAnchor(opPlayer, pane.getLeftAnchor(curPlayer));
            pane.setTopAnchor(curPlayer, tmpY);
            pane.setLeftAnchor(curPlayer, tmpX);

        } else if (x == pane.getLeftAnchor(opPlayer) && Math.abs(y - pane.getTopAnchor(opPlayer)) / SQUARE_SIZE <= squares) { /* obstruction is opponent */
            r = true;
            double newY = (direction == 0 || direction == 1) ? pane.getTopAnchor(opPlayer) + SQUARE_SIZE : pane.getTopAnchor(opPlayer) - SQUARE_SIZE;
            tmpDialogContent = "you meet the opponent, please choose another direction to move ";
            tmpDialogNewY = newY;
            tmpDialogLeftSquares = squares + 1 - (int) (Math.abs(y - pane.getTopAnchor(opPlayer)) / SQUARE_SIZE);
        }
        return r;
    }

    /**
     * This method is used to control the logic of players meeting obstruction when they move vertically, then show a dialog
     * @param alert This is the first parameter which indicates a new alert box
     * @param content This is the second parameter which indicates the contents of dialog
     * @param title This is the third parameter which indicates the title of dialog
     * @param header This is the fourth parameter which indicates the header of dialog
     * @param player This is the fifth parameter which indicates the current player
     * @param newY This is the sixth parameter which indicates the new top anchor of the current player when finishing the movement
     * @param leftSquares This is the seventh parameter which indicates the remaining squares when meeting an obstruction
     * */
    public static void showDirectionDialog(Alert alert, String content, String title, String header, StackPane player, double newY, int leftSquares) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType leftBtn = new ButtonType("Left");
        ButtonType rightBtn = new ButtonType("Right");
        ButtonType endBtn = new ButtonType("End the turn");

        alert.getButtonTypes().setAll(leftBtn, rightBtn, endBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == leftBtn) {
            pane.setTopAnchor(player, newY);
            if (pane.getLeftAnchor(player) - leftSquares * SQUARE_SIZE < 0) {
                int leftLeftSquares = leftSquares - (int) (pane.getLeftAnchor(player) / SQUARE_SIZE);
                pane.setLeftAnchor(player, 0.0);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                showHorDialog(alert2,
                        "you meet the edge of the board, please choose another direction to move ",
                        "information dialog",
                        null,
                        leftLeftSquares);
                return;
            }
            pane.setLeftAnchor(player, pane.getLeftAnchor(player) - leftSquares * SQUARE_SIZE);

        } else if (result.get() == rightBtn) {
            pane.setTopAnchor(player, newY);
            if (pane.getLeftAnchor(player) + leftSquares * SQUARE_SIZE > (WIDTH - 1) * SQUARE_SIZE) {
                int leftLeftSquares = leftSquares - (int) (((WIDTH - 1) * SQUARE_SIZE - pane.getLeftAnchor(player)) / SQUARE_SIZE);
                pane.setLeftAnchor(player, (WIDTH - 1) * SQUARE_SIZE);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                showHorDialog(alert2,
                        "you meet the edge of the board, please choose another direction to move ",
                        "information dialog",
                        null,
                        leftLeftSquares);
                return;
            }
            pane.setLeftAnchor(player, pane.getLeftAnchor(player) + leftSquares * SQUARE_SIZE);

        } else if (result.get() == endBtn) {
            pane.setTopAnchor(player, newY);
        }

    }

    /**
     * This method is used to control the logic of players meeting obstruction when they move horizontally, then show a dialog
     * @param alert This is the first parameter which indicates a new alert box
     * @param content This is the second parameter which indicates the contents of dialog
     * @param title This is the third parameter which indicates the title of dialog
     * @param header This is the fourth parameter which indicates the header of dialog
     * @param leftSquares This is the fifth parameter which indicates the remaining squares when meeting an obstruction
     * */
    public static void showHorDialog(Alert alert, String content, String title, String header, int leftSquares) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType forBtn = new ButtonType("Forward");
        ButtonType bacBtn = new ButtonType("Backward");
        ButtonType endBtn = new ButtonType("End the turn");

        alert.getButtonTypes().setAll(forBtn, bacBtn, endBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == forBtn) {
            move(0, leftSquares);
        } else if (result.get() == bacBtn) {
            move(0, leftSquares);
        } else if (result.get() == endBtn) {

        }

    }

    public static boolean getPlayerTurn() {
        return playerTurn;
    }
}
