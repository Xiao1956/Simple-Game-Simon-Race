package com.example.simonrace;
import java.io.*;
import java.util.ArrayList;
public class PlayerInfo implements java.io.Serializable, Comparable<PlayerInfo> {
    private static final long serialVersionUID = -3291296424339574540L;
    private String name;
    private int winNum = 0;
    private int playNum = 0;

    public PlayerInfo(String name, int winNum, int playNum) {
        this.name = name;
        this.winNum = winNum;
        this.playNum = playNum;
    }

    public String getName() {
        return name;
    }

    public int getWinNum() {
        return winNum;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setWinNum(int winNum) {
        this.winNum = winNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    /**
     * This method is used to get players data from file
     * @return return the current players' data list
     * */
    public static ArrayList<PlayerInfo> read() throws IOException, ClassNotFoundException {
        ArrayList<PlayerInfo> playerList;
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/files/playerInfo.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        playerList = (ArrayList<PlayerInfo>) ois.readObject();
        ois.close();
        fis.close();
        return playerList;
    }

    /**
     * This method is used to write the player's data in the file
     * @param player This is the first parameter which indicates the player that is needed to be written
     * @param playerList This is the second parameter which indicates the current players' list
     * */
    public static void write(PlayerInfo player, ArrayList<PlayerInfo> playerList) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/files/playerInfo.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        int count = 0;

        /*if this player is not a new player, update his/her data*/
        for (PlayerInfo p : playerList) {
            if (p.getName().equals(player.getName())) {
                p.setWinNum(player.getWinNum());
                p.setPlayNum(player.getPlayNum());
                count++;
            }
        }
        /*if this player is a new player, add he/she in file*/
        if (count == 0) playerList.add(player);

        oos.reset();
        oos.writeObject(playerList);
        oos.close();
        fos.close();

    }

    /**
     * This method is used to sort playerList by winNum
     * */
    @Override
    public int compareTo(PlayerInfo o) {
        if (o.getWinNum() > getWinNum()) {
            return 1;
        } else if (o.getWinNum() < getWinNum()) {
            return -1;
        } else {
            return 0;
        }
    }
}
