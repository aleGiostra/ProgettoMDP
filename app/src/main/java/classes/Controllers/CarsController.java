package classes.Controllers;

import classes.Player.Player;
import classes.Position;
import classes.Track;

import java.util.ArrayList;
import java.util.List;

public class CarsController {
    private StringBuilder message = new StringBuilder();
    private List<Player> players = new ArrayList<>();

    public CarsController(List<Player> players){
        this.players = players;
    }

    //this class will have the methods used for checking each player's car position
    public String checkCars(Track track){
        for(Player player : players){
            String checkEsit = "";
            if(player.getCar().isRunning()){
                //controllo se il giocatore è uscito dal tracciato
                checkEsit = this.checkPosition(track, player);
                if(!checkEsit.isEmpty()){
                    message.append(checkEsit);
                }else {
                    //controllo se ci sono incidenti
                    checkEsit = this.checkCollision(track, player);
                    if(!checkEsit.isEmpty())
                        message.append(checkEsit);
                    else{//controllo se il player è crashato su un ostacolo
                        checkEsit = this.checkCollisionWithObstacle(track, player);
                        message.append(checkEsit);
                    }
                }
            }
        }
        return message.toString();
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player è uscito dal tracciato
    public String checkPosition(Track track, Player player) throws ArrayIndexOutOfBoundsException{
        try{
            if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == '#'){
                player.getCar().setRunning(false);
                return player.getName() + "'s car went out of track!\n";
            }
        }catch (ArrayIndexOutOfBoundsException e){
            if(player.getCar().getActualPosition().getX() < 0) player.getCar().getActualPosition().setX(0);
            if(player.getCar().getActualPosition().getY() < 0) player.getCar().getActualPosition().setY(0);
            if(player.getCar().getActualPosition().getX() > track.getXs()) player.getCar().getActualPosition().setX(track.getXs()-1);
            if(player.getCar().getActualPosition().getY() > track.getYs()) player.getCar().getActualPosition().setY(track.getYs()-1);
            checkPosition(track, player);
        }
        return "";
    }

    //il metodo viene chiamato dopo ogni mossa, controlla se il player che ha fatto la mossa è andato a sbattere con altri player
    public String checkCollision(Track track, Player player){
        Position playerPosition = player.getCar().getActualPosition();
        for(Player player2 : players){
            Position player2Position = player2.getCar().getActualPosition();
            if(player.getId() != player2.getId() && (playerPosition.equals(player2Position)) && player2.getCar().isRunning()){
                player.getCar().setRunning(false);
                player2.getCar().setRunning(false);
                track.setCell(playerPosition.getX(), playerPosition.getY(), 'x');
                return "Player " + player.getName() + " and player " + player2.getName() +
                        " were eliminated due to collision\n";
            }
        }
        return "";
    }

    public String checkCollisionWithObstacle(Track track, Player player){
        if(track.getCell(player.getCar().getActualPosition().getX(), player.getCar().getActualPosition().getY()) == 'x'){
            player.getCar().setRunning(false);
            return player.getName() + " has crashed into an obstacle\n";
        }
        return "";
    }
}
