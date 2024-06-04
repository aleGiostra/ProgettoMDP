package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameEngine {
    private Track track;
    //lista dei giocatori, la uso anche per l'ordine di gioco
    private ArrayList<Player> players;

    public GameEngine(Track track) {
        this.track = track;
        this.players = new ArrayList<>();
    }

    public Track getTrack(){ return this.track; }
    public ArrayList<Player> getPlayers(){ return this.players; }

    //aggiunge giocatori alla partita
    public void addPlayer(Player player){ players.add(player); }
    //determina randomicamente l'ordine di gioco mescolando i giocatori inseriti in partita
    public void setRandomOrder(){ Collections.shuffle(players); }

    public void startRace() {

        boolean raceOn = true;
        //randomizza ordine di gioco
        setRandomOrder();
        //stampo ordine di gioco
        System.out.print("Game Order ");
        for(Player playerNames : players){
            System.out.print("-> ");
            System.out.print(playerNames.getName());
        }
        System.out.println();

        //stampa il tracciato con i giocatori sulla linea di partenza
        track.printTrack(players);
        int round = 0;
        while (raceOn) {
            for (Player player : players) {
                if(player.getCar().isRunning()){
                    System.out.println(player.getName() + "'s turn: ");
                    //un giocatore alla volta in base all'ordine random stabilito fa la sua mossa
                    player.move(track, round);

                    //controllo se il giocatore è uscito dal tracciato
                    if(!this.checkPosition(player))
                        //controllo se ci sono incidenti
                        if(!this.checkCollision(player))
                            //controllo se il player è crashato su un ostacolo
                            this.checkCollisionWithObstacle(player);

                    //controllo se il giocatore è arrivato al traguardo
                    if (track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == '_'
                            && round > (this.getTrack().getXs()+this.getTrack().getYs())/2) {
                        System.out.println(player.getName() + " wins the race!");
                        raceOn = false;
                        break;
                    }
                    track.printTrack(players);
                }
                //controllo se c'è ancora almeno un player in gara
                boolean flagRunning = false;
                for(Player playerRacing : players)
                    if(playerRacing.getCar().isRunning()) flagRunning = true;
                if(!flagRunning){
                    System.out.println("Every player got eliminated from the race so RACE IS OVER WITH NO WINNER");
                    raceOn = false;
                    break;
                }
            }
            displayRaceProgress();
            round++;
        }
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player è uscito dal tracciato
    public boolean checkPosition(Player player) throws ArrayIndexOutOfBoundsException{
        try{
            if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == '#'){
                System.out.println("The car went out of track!");
                player.getCar().setRunning(false);
                return true;
            }
            return false;
        }catch (ArrayIndexOutOfBoundsException e){
            if(player.getCar().getActualPosition().getX() < 0) player.getCar().getActualPosition().setX(0);
            if(player.getCar().getActualPosition().getY() < 0) player.getCar().getActualPosition().setY(0);
            if(player.getCar().getActualPosition().getX() > track.getXs()) player.getCar().getActualPosition().setX(track.getXs()-1);
            if(player.getCar().getActualPosition().getY() > track.getYs()) player.getCar().getActualPosition().setY(track.getYs()-1);
            checkPosition(player);
            return true;
        }
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player che ha fatto la mossa è andato a sbattere con altri player
    public boolean checkCollision(Player player){
        int playerX = player.getCar().getActualPosition().getX();
        int playerY = player.getCar().getActualPosition().getY();
        for(Player player2 : players){
            int player2X = player2.getCar().getActualPosition().getX();
            int player2Y = player2.getCar().getActualPosition().getY();
            if(player.getId() != player2.getId() && (playerX == player2X && playerY == player2Y) && player2.getCar().isRunning()){
                player.getCar().setRunning(false);
                player2.getCar().setRunning(false);
                System.out.println("Player " + player.getName() + " and player " + player2.getName() +
                        " were eliminated due to collision");
                this.track.setCell(playerX, playerY, 'x');
                return true;
            }
        }
        return false;
    }

    public void checkCollisionWithObstacle(Player player){
        if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == 'x'){
            player.getCar().setRunning(false);
            System.out.println(player.getName() + " has crashed into an obstacle");
        }
    }

    public void displayRaceProgress() {
        //track.printTrack();
        for (Player player : players) {
            int playerX = player.getCar().getActualPosition().getX();
            int playerY = player.getCar().getActualPosition().getY();
            boolean r = player.getCar().isRunning();
            if(r)
                System.out.println(player.getName() + " is at (" + playerX + ", " + playerY + ")");
            else
                System.out.println(player.getName() + " crashed at (" + playerX + ", " + playerY + ")");
        }
    }
}
