package classes;

import java.util.ArrayList;

public class Car {

    //private Position actualPosition;
    //private Position previousPosition;

    //starts with starting position, with every move the new position occupied will be added
    //last element is actual position
    private ArrayList<Position> positionLog;
    private boolean running;

    public Car(){}

    public Car(Position actualPosition){
        //this.actualPosition = actualPosition;
        //this.previousPosition = actualPosition;
        this.positionLog = new ArrayList<Position>();
        this.positionLog.add(actualPosition);
        this.running = true;
    }

    public Position getActualPosition(){ return this.positionLog.get(this.positionLog.size()-1); }
    public Position getPreviousPosition(){
        if(this.positionLog.size() > 1)
            return this.positionLog.get(this.positionLog.size()-2);
        else
            return getActualPosition();
    }
    public boolean isRunning(){
        return this.running;
    }

    //public void setActualPosition(Position position){ this.actualPosition = position; }
    //public void setPreviousPosition(Position position){ this.previousPosition = position; }
    public void addPosition(Position position){
        this.positionLog.add(position);
    }
    public void setRunning(boolean running){
        this.running = running;
    }

}
