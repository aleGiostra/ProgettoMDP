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
            StringBuilder message = new StringBuilder();
            System.out.println("\n\nStart of round " + (round+1)+"\n");
            for (Player player : players) {
                if(player.getCar().isRunning()){
                    if(player instanceof BotPlayer)
                        System.out.println(player.getName() + " has made his move");
                    else
                        System.out.println(player.getName() + "'s turn: ");

                    //un giocatore alla volta in base all'ordine random stabilito fa la sua mossa
                    message.append(player.move(track, round));

                }
            }//fine round
            //effettuo i vari controlli
            String checkEsit = "";
            for(Player player : players){
                if(player.getCar().isRunning()){
                    //controllo se il giocatore è uscito dal tracciato
                    checkEsit = this.checkPosition(player);
                    if(!checkEsit.isEmpty()){
                        message.append(checkEsit);
                    }else {
                        //controllo se ci sono incidenti
                        checkEsit = this.checkCollision(player);
                        if(!checkEsit.isEmpty())
                            message.append(checkEsit);
                        else{//controllo se il player è crashato su un ostacolo
                            checkEsit = this.checkCollisionWithObstacle(player);
                            message.append(checkEsit);
                        }
                    }
                }
            }

            //controllo se il giocatore è arrivato al traguardo
            int haveWon = 0;//number of players who have arrived at the finish line on the same round
            String[] winners = new String[players.size()];//idea: string with the names of every player who won
            for(Player player : players){
                if (track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == '_'
                        && round > (this.getTrack().getXs()+this.getTrack().getYs())/2) {
                    //format the string to print based on who and how many won
                    winners[haveWon++] = player.getName();
                }
            }
            if(haveWon == 1){
                message.append(winners[0]).append(" wins the race!\n");
                break;
            }else if(haveWon > 1){
                for(int i = 0; i < winners.length; i++){
                    if(i != winners.length - 1)
                        message.append(winners[i] + " and ");
                    else message.append(winners[i]);
                }
                message.append(" have won the race together!!!");
                break;
            }

            System.out.println("\nRound " + (round+1) + " moves:\n" + message);
            //controllo se c'è ancora almeno un player in gara
            boolean flagRunning = false;
            for(Player playerRacing : players)
                if(playerRacing.getCar().isRunning()) flagRunning = true;
            if(!flagRunning){
                System.out.println("Every player got eliminated from the race.\nRACE IS OVER WITH NO WINNER");
                raceOn = false;
            }
            track.printTrack(players);
            //displayRaceProgress();
            round++;
        }
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player è uscito dal tracciato
    public String checkPosition(Player player) throws ArrayIndexOutOfBoundsException{
        try{
            if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == '#'){
                player.getCar().setRunning(false);
                return player.getName() + "'s car went out of track!\n";
            }
            return "";
        }catch (ArrayIndexOutOfBoundsException e){
            if(player.getCar().getActualPosition().getX() < 0) player.getCar().getActualPosition().setX(0);
            if(player.getCar().getActualPosition().getY() < 0) player.getCar().getActualPosition().setY(0);
            if(player.getCar().getActualPosition().getX() > track.getXs()) player.getCar().getActualPosition().setX(track.getXs()-1);
            if(player.getCar().getActualPosition().getY() > track.getYs()) player.getCar().getActualPosition().setY(track.getYs()-1);
            checkPosition(player);
            return player.getName() + "'s car went out of track!\n";
        }
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player che ha fatto la mossa è andato a sbattere con altri player
    public String checkCollision(Player player){
        Position playerPosition = player.getCar().getActualPosition();
        for(Player player2 : players){
            Position player2Position = player2.getCar().getActualPosition();
            if(player.getId() != player2.getId() && (playerPosition.equals(player2Position)) && player2.getCar().isRunning()){
                player.getCar().setRunning(false);
                player2.getCar().setRunning(false);
                this.track.setCell(playerPosition.getX(), playerPosition.getY(), 'x');
                return "Player " + player.getName() + " and player " + player2.getName() +
                        " were eliminated due to collision\n";
            }
        }
        return "";
    }

    public String checkCollisionWithObstacle(Player player){
        if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == 'x'){
            player.getCar().setRunning(false);
            return player.getName() + " has crashed into an obstacle\n";
        }
        return "";
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
