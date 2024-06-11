package classes;

import classes.Controllers.CarsController;
import classes.Player.BotPlayer;
import classes.Player.Player;

import java.util.Collections;
import java.util.List;

public class GameEngine {
    private final Track track;
    private List<Player> players;
    private boolean raceOn = true;

    public GameEngine(Track track, List<Player> players) {
        this.track = track;
        this.players = players;
    }

    public void startRace() {

        //randomizza ordine di gioco
        Collections.shuffle(players);
        //stampo ordine di gioco
        gameOrderPrint();
        //stampa il tracciato con i giocatori sulla linea di partenza
        track.printTrack(players);

        int round = 0;
        while (raceOn) {
            StringBuilder message = new StringBuilder();
            System.out.println("\n\nStart of round " + (round+1) + "\n");
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
            //controllo macchine fuori pista, incidenti, scontri
            CarsController carsController = new CarsController(players);
            message.append(carsController.checkCars(track));
            //controllo se almeno un player ha vinto
            if(round >= track.getMinMovesToFinish())
                message.append(checkForWinners());
            //stampo riepilogo del round
            System.out.println("\nRound " + (round+1) + " moves:\n" + message);
            //controllo se c'è ancora almeno un player in gara
            stillRacing();
            track.printTrack(players);
            round++;
        }
    }

    public void gameOrderPrint(){
        StringBuilder print = new StringBuilder();
        boolean firstPlayer = true;
        print.append("Game Order: ");
        for(Player player : players){
            if(!firstPlayer) print.append("-> ");
            else firstPlayer = false;
            print.append(player.getName());
        }
        System.out.println(print + "\n");
    }

    public String checkForWinners(){
        //controllo se il giocatore è arrivato al traguardo
        StringBuilder message = new StringBuilder();
        int haveWon = 0;//number of players who have arrived at the finish line on the same round
        String[] winners = new String[players.size()];//string array with the names of every player who won
        for(Player player : players){
            //works only for circular tracks
            int prevX = player.getCar().getPreviousPosition().getX();
            int prevY = player.getCar().getPreviousPosition().getY();
            int x = player.getCar().getActualPosition().getX();
            int y = player.getCar().getActualPosition().getY();
            int firstFinishPointX = track.getFinishLinePoints().get(0).getX();
            int firstFinishPointY = track.getFinishLinePoints().get(0).getY();
            int lastFinishPointX = track.getFinishLinePoints().get(track.getFinishLinePoints().size()-1).getX();
            int lastFinishPointY = track.getFinishLinePoints().get(track.getFinishLinePoints().size()-1).getY();
            //switch cases track firstMoveDirection
            switch(track.getFirstMoveDirection()){
                case TOP -> {
                    if (prevX > firstFinishPointX && prevX < lastFinishPointX &&
                            x > firstFinishPointX && x < lastFinishPointX &&
                            prevY > firstFinishPointY && y < firstFinishPointY) {
                        winners[haveWon++] = player.getName();
                    }
                }
                case DOWN -> {
                    if (prevX > firstFinishPointX && prevX < lastFinishPointX &&
                            x > firstFinishPointX && x < lastFinishPointX &&
                            prevY < firstFinishPointY && y > firstFinishPointY)
                        winners[haveWon++] = player.getName();
                }
                case RIGHT -> {
                    if (prevY > firstFinishPointY && prevY < lastFinishPointY &&
                            y > firstFinishPointY && y < lastFinishPointY &&
                            prevX < firstFinishPointX && x > firstFinishPointX)
                        winners[haveWon++] = player.getName();
                }
                case LEFT -> {
                    if (prevY < firstFinishPointY && prevY > lastFinishPointY &&
                            y < firstFinishPointY && y > lastFinishPointY &&
                            prevX > firstFinishPointX && x < firstFinishPointX)
                        winners[haveWon++] = player.getName();
                }
            }
                //if p1x between startFinishLinex and endFinishLinex and
                //if p1y < finishLiney and p2y > finishLine then arrived
            //do the same with every direction (main 4)

        }
        if(haveWon == 1){
            message.append(winners[0]).append(" wins the race!\n");
            raceOn = false;
        }else if(haveWon > 1){
            for(int i = 0; i < winners.length; i++){
                message.append(winners[i]);
                if(i != winners.length - 1)
                    message.append(" and ");
            }
            raceOn = false;
            message.append(" have won the race together!!!");
        }
        return message.toString();
    }

    public void stillRacing(){
        //controllo se c'è ancora almeno un player in gara
        boolean flagRunning = false;
        for(Player playerRacing : players)
            if(playerRacing.getCar().isRunning()){
                flagRunning = true;
                break;
            }
        if(!flagRunning){
            System.out.println("Every player got eliminated from the race.\nRACE IS OVER WITH NO WINNER");
            raceOn = false;
        }
    }
}
