package classes.Parser;

import classes.Player.HumanPlayer;
import classes.Player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePlayersParser {
    public static int botPlayersSetup(String fileName){
        //leggo il numero di bot da inserire
        int nBot = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = br.readLine().trim();
            nBot = Integer.parseInt(line);
        }catch (IOException | NumberFormatException e){
            e.printStackTrace();
        }
        return nBot;
    }
    public static List<Player> playersSetup(String fileName){
        //leggo il file di setup dei giocatori
        //il file ha forma : 1 riga = 1 player -> id name
        List<Player> players = new ArrayList<Player>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                char id = line.toUpperCase().charAt(0);
                String name = line.substring(2);
                players.add(new HumanPlayer(id, name, null));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return players;
    }
}
