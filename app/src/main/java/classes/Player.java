package classes;

public abstract class Player {
    protected char id;
    protected Car car;
    protected String name;

    public Player(char id, String name) {
        this.id = id;
        this.car = new Car();
        this.name = name;
    }

    public char getId(){ return id; }
    public String getName(){ return name; }
    public Car getCar(){ return car; }

    public abstract void move(Track track, int round);
}
