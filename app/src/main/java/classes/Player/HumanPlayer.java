package classes.Player;

import classes.Car;
import classes.Position;
import classes.Track;

import java.util.Scanner;

public class HumanPlayer implements Player {

    private char id;
    private Car car;
    private String name;

    public HumanPlayer(char id, String name, Car car) {
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
            //faccio scegliere all'utente la posizione dell'ottetto che vuole occupare
            boolean flag = false;
            int option = 0;
            Scanner scanner = new Scanner(System.in);
            while(!flag){
                System.out.println("Select on which point your car will move:");
                System.out.print("Options are: ");
                if(round == 0){
                    int dirId = track.getFirstMoveDirection().id;
                    int nextDirId = track.getFirstMoveDirection().nextDirId;
                    int prevDirId = track.getFirstMoveDirection().prevDirId;
                    int i = prevDirId;
                    do {
                        Position p = nextBasePosition.dirModifier(i);
                        System.out.print(i + " for [" + p.getX() + ", " + p.getY() + "]");
                        if(i != nextDirId) System.out.print(", ");
                        if(dirId == 8 && i == dirId) i = 1;
                        else if(dirId == 1 && i == prevDirId) i = 1;
                        else i++;
                    }while(i != nextDirId+1);
                    option = scanner.nextInt();
                    scanner.nextLine();
                    if(option != dirId && option > prevDirId && option != nextDirId)
                        System.out.println("Please select one of the available options");
                    else flag = true;
                }else {
                    for(int i = 0; i < 9; i++){
                        Position p = nextBasePosition.dirModifier(i);
                        System.out.print(i + " for [" + p.getX() + ", " + p.getY() + "]");
                        if(i < 8) System.out.print(", ");
                    }
                    option = scanner.nextInt();
                    scanner.nextLine();
                    if(option < 0 || option > 8)
                        System.out.println("Please select one of the available options");
                    else flag = true;
                }
            }
            this.getCar().addPosition(nextBasePosition.dirModifier(option));
        }catch(ArrayIndexOutOfBoundsException e){
            if(this.getCar().getActualPosition().getX() < 0) this.getCar().getActualPosition().setX(0);
            if(this.getCar().getActualPosition().getY() < 0) this.getCar().getActualPosition().setY(0);
            if(this.getCar().getActualPosition().getX() > track.getXs()) this.getCar().getActualPosition().setX(track.getXs()-1);
            if(this.getCar().getActualPosition().getY() > track.getYs()) this.getCar().getActualPosition().setY(track.getYs()-1);
            this.getCar().setRunning(false);
            return this.getName() + "'s car went out of track!\n";
        }
        return (this.getName() + " has moved from [" +
                this.getCar().getPreviousPosition().getX() + ", " +
                this.getCar().getPreviousPosition().getY() + "] to [" +
                this.getCar().getActualPosition().getX() + ", " +
                this.getCar().getActualPosition().getY() + "]\n");
    }
}
