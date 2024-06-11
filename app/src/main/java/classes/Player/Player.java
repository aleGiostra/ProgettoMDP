package classes.Player;

import classes.Car;
import classes.Track;

public interface Player {

    public char getId();

    public String getName();

    public Car getCar();

    public String move(Track track, int round);
}
