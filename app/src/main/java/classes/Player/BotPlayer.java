package classes.Player;

import classes.Car;
import classes.Position;
import classes.Track;

import java.util.Random;

public class BotPlayer implements Player {

    private char id;
    private Car car;
    private String name;

    public BotPlayer(char id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public char getId(){ return id; }
    public String getName(){ return name; }
    public Car getCar(){ return car; }

    @Override
    public String move(Track track, int round) {
        try{
            //calcolo e replico il movimento effettuato nel turno precedente
            int lastXMovement = this.getCar().getActualPosition().getX() - this.getCar().getPreviousPosition().getX();
            int lastYMovement = this.getCar().getActualPosition().getY() - this.getCar().getPreviousPosition().getY();
            int nextX = this.getCar().getActualPosition().getX() + lastXMovement;
            int nextY = this.getCar().getActualPosition().getY() + lastYMovement;
            //this.getCar().setPreviousPosition(this.getCar().getActualPosition());
            Position nextBasePosition = new Position(nextX, nextY);
            //seleziono randomicamente uno degli otto vicini della prossima posizione prevista
            Random random = new Random();
            int dir = 0;
            if(round == 0) dir = track.getFirstMoveDirection().id;
            else dir = random.nextInt(9);
            this.getCar().addPosition(nextBasePosition.dirModifier(dir));
        }catch(ArrayIndexOutOfBoundsException e){
            if(this.getCar().getActualPosition().getX() < 0) this.getCar().getActualPosition().setX(0);
            if(this.getCar().getActualPosition().getY() < 0) this.getCar().getActualPosition().setY(0);
            if(this.getCar().getActualPosition().getX() > track.getXs()) this.getCar().getActualPosition().setX(track.getXs()-1);
            if(this.getCar().getActualPosition().getY() > track.getYs()) this.getCar().getActualPosition().setY(track.getYs()-1);
            return this.getName() + "'s car went out of track!\n";
        }
        return (this.getName() + " has moved from [" +
                this.getCar().getPreviousPosition().getX() + ", " +
                this.getCar().getPreviousPosition().getY() + "] to [" +
                this.getCar().getActualPosition().getX() + ", " +
                this.getCar().getActualPosition().getY() + "]\n");
    }
}
