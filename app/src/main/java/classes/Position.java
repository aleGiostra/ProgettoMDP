package classes;

import classes.Player.Player;

import java.util.List;

public class Position {

    private int x;//colonna, ascissa
    private int y;//riga, ordinata

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){ return x; }//indice ascissa
    public void setX(int x){ this.x = x; }
    public int getY(){ return y; }//indice ordinata
    public void setY(int y){ this.y = y; }

    public Position dirModifier(int i){
        return switch (i) {
            case 0 ->//x+0,y+0 -> CENTER
                    this;
            case 1 ->//x+0,y-1 -> TOP
                    new Position(this.getX(), this.getY() - 1);
            case 2 ->//x+1,y-1 -> TOP_RIGHT
                    new Position(this.getX() + 1, this.getY() - 1);
            case 3 ->//x+1,y+0 -> RIGHT
                    new Position(this.getX() + 1, this.getY());
            case 4 ->//x+1,y+1 -> DOWN_RIGHT
                    new Position(this.getX() + 1, this.getY() + 1);
            case 5 ->//x+0,y+1 -> DOWN
                    new Position(this.getX(), this.getY() + 1);
            case 6 ->//x-1,y+1 -> DOWN_LEFT
                    new Position(this.getX() - 1, this.getY() + 1);
            case 7 ->//x-1,y+0 -> LEFT
                    new Position(this.getX() - 1, this.getY());
            case 8 ->//x-1,y-1 -> TOP_LEFT
                    new Position(this.getX() - 1, this.getY() - 1);
            default -> null;
        };
    }

    public boolean isFree(List<Player> players){
        for(Player player : players)
            if(this.equals(player.getCar().getActualPosition()))
                return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position)
            return ((Position) obj).getX() == this.getX() && ((Position) obj).getY() == this.getY();
        else return false;
    }
}
