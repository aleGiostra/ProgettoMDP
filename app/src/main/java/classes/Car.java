package classes;

public class Car {

    private Position actualPosition;
    private Position previousPosition;
    private boolean running;

    public Car(){}

    public Car(Position actualPosition){
        this.actualPosition = actualPosition;
        this.previousPosition = actualPosition;
        this.running = true;
    }

    public Position getActualPosition(){ return actualPosition; }
    public Position getPreviousPosition(){ return previousPosition; }
    public boolean isRunning(){ return this.running; }

    public void setActualPosition(Position position){ this.actualPosition = position; }
    public void setPreviousPosition(Position position){ this.previousPosition = position; }
    public void setRunning(boolean running){ this.running = running; }

}
