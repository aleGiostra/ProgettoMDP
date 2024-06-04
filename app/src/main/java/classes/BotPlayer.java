package classes;

import java.util.Random;

public class BotPlayer extends Player{

    public BotPlayer(char id, String name, Car car) {
        super(id, name);
        this.car = car;
    }

    @Override
    public void move(Track track, int round) {
        try{
            //calcolo e replico il movimento effettuato nel turno precedente
            int lastXMovement = this.getCar().getActualPosition().getX() - this.getCar().getPreviousPosition().getX();
            int lastYMovement = this.getCar().getActualPosition().getY() - this.getCar().getPreviousPosition().getY();
            int nextX = this.getCar().getActualPosition().getX() + lastXMovement;
            int nextY = this.getCar().getActualPosition().getY() + lastYMovement;
            this.getCar().setPreviousPosition(this.getCar().getActualPosition());
            Position nextBasePosition = new Position(nextX, nextY);
            //seleziono randomicamente uno degli otto vicini della prossima posizione prevista
            Random random = new Random();
            int dir = 0;
            if(round == 0) dir = track.getFirstMoveDirection().id;
            else dir = random.nextInt(9);
            this.getCar().setActualPosition(nextBasePosition.dirModifier(dir));
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("The car went out of track!");
            if(this.getCar().getActualPosition().getX() < 0) this.getCar().getActualPosition().setX(0);
            if(this.getCar().getActualPosition().getY() < 0) this.getCar().getActualPosition().setY(0);
            if(this.getCar().getActualPosition().getX() > track.getXs()) this.getCar().getActualPosition().setX(track.getXs()-1);
            if(this.getCar().getActualPosition().getY() > track.getYs()) this.getCar().getActualPosition().setY(track.getYs()-1);
        }
        System.out.println(this.getName() + " has moved from [" +
                this.getCar().getPreviousPosition().getX() + ", " +
                this.getCar().getPreviousPosition().getY() + "] to [" +
                this.getCar().getActualPosition().getX() + ", " +
                this.getCar().getActualPosition().getY() + "]");
    }
}
