package classes.Controllers;

import classes.*;
import classes.Parser.FilePlayersParser;
import classes.Player.BotPlayer;
import classes.Player.HumanPlayer;
import classes.Player.Player;

import java.util.*;

import static classes.Parser.FilePlayersParser.playersSetup;
import static classes.Parser.FileTrackParser.loadTrackFromFile;

public class SetupController {

    private final String playerSetupFile = "C:\\Users\\alegi\\MdP\\bozzaProgettoMdP\\app\\src\\main\\resources\\PlayerSetup.txt";

    private Track track;
    private List<Player> players = new ArrayList<>();

    public Track getTrack(){
        return this.track;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void addPlayer(Player player){ players.add(player); }

    public void gameSetup(){
        this.loadTrack();
        this.humanPlayersSetup();
        this.BotPlayersSetup();
    }

    public void loadTrack(){
        //faccio scegliere all'utente il tracciato da caricare tra quelli disponibili
        String trackFile = "";
        String input = "";
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);
        while(!validInput){
            System.out.println("Select the size of the track.\nS for small size, M for medium size or L for large size:");
            input = scanner.nextLine().trim().toUpperCase();
            if(!input.equals("S") && !input.equals("M") && !input.equals("L"))
                System.out.println("Please type one of the available options");
            else validInput = true;
        }
        if(input.equals("S")) trackFile = "C:\\Users\\alegi\\MdP\\bozzaProgettoMdP\\app\\src\\main\\resources\\SmallTrack.txt";
        if(input.equals("M")) trackFile = "C:\\Users\\alegi\\MdP\\bozzaProgettoMdP\\app\\src\\main\\resources\\MediumTrack.txt";
        if(input.equals("L")) trackFile = "C:\\Users\\alegi\\MdP\\bozzaProgettoMdP\\app\\src\\main\\resources\\LargeTrack.txt";
        this.track = loadTrackFromFile(trackFile);
    }

    public void humanPlayersSetup(){
        //carico i giocatori umani da caricare in partita dal parser player setup
        //mi restituisce i giocatori che devo aggiungere in partita
        List<Player> tempPlayers = playersSetup(playerSetupFile);
        //li mescolo
        Collections.shuffle(tempPlayers);
        //li aggiungo in posizioni random nella linea di partenza
        for(Player player : tempPlayers){
            Random random = new Random();
            int randomStartingPoint;
            //prendo un player e una posizione random nella linea di partenza
            while(true){
                randomStartingPoint = random.nextInt(this.track.getStartingLinePoints().size());
                //controllo se tra i player in game ce n'è uno che occupa la posizione
                if(this.track.getStartingLinePoints().get(randomStartingPoint).isFree(this.players)){
                    this.players.add(new HumanPlayer(player.getId(), player.getName(),
                            new Car(this.track.getStartingLinePoints().get(randomStartingPoint))));
                    System.out.println("A player was added to the game: " + player.getName() + ", starting position: [" +
                            this.track.getStartingLinePoints().get(randomStartingPoint).getX() + ", " +
                            this.track.getStartingLinePoints().get(randomStartingPoint).getY() + "]");
                    break;
                }
            }
        }
    }

    public void BotPlayersSetup(){
        int botPlayers = FilePlayersParser.botPlayersSetup(playerSetupFile);
        int maxPlayers = track.getStartingLinePoints().size() - this.players.size();
        if(botPlayers > maxPlayers) botPlayers = maxPlayers;
        if(botPlayers < 0) botPlayers = 0;
        //posiziono i bot sulla linea di partenza man mano che li creo
        int idBot = 0;
        boolean freePosition = false;
        for(int i = 0; i < botPlayers;){
            //cerco punto di partenza libero
            for(Position p : this.track.getStartingLinePoints()){
                freePosition = true;//true = posizione libera
                for(Player player : this.players){
                    if(player.getCar().getActualPosition().equals(p)){
                        freePosition = false;//posizione occupata
                        break;
                    }
                }
                if(freePosition){
                    char idBotC = (char) (++idBot + '0');
                    this.addPlayer(new BotPlayer(idBotC,"Bot"+(idBot), new Car(p)));
                    System.out.println("New Bot" + (idBot) +" player added at (" + p.getX() + ", " + p.getY() + ")");
                    i++;
                    break;
                }
            }
        }
    }
}