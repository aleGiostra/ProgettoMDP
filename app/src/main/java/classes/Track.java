package classes;

import java.util.ArrayList;

public class Track {

    private char[][] grid;
    private ArrayList<Position> startingLine;
    private Direction firstMoveDirection;

    public Track(int x, int y, Direction direction) {
        this.grid = new char[x][y];
        startingLine = new ArrayList<Position>();
        this.firstMoveDirection = direction;
    }

    public char getCell(int x, int y){ return this.grid[x][y]; }
    public ArrayList<Position> getStartingLine(){ return this.startingLine; }
    public int getXs(){ return grid.length; }//ascissa (x)
    public int getYs(){ return grid[0].length; }//ordinata (y)
    public Direction getFirstMoveDirection(){ return this.firstMoveDirection; }

    public void setCell(int x, int y, char value){ this.grid[x][y] = value; }

    public void addStartingPoint(int x, int y){ this.startingLine.add(new Position(x, y)); }

    //stampa il tracciato con i player nelle loro posizioni attuali
    public void printTrack(ArrayList<Player> players) {//TODO fix print
        //stampo una riga per le coordinate x
        System.out.print("   ");
        //i indicizza le colonne quindi x
        for(int i = 0; i < this.getXs(); i++){
            if(i / 10 == 0) System.out.print(" " + i);
            else System.out.print("" + i);
        }
        System.out.println();
        //indicizza le righe quindi y
        for (int y = 0; y < this.getYs(); y++) {
            //ad inizio riga stampa la coordinata y
            if(y / 10 == 0) System.out.print(y + " : ");
            else System.out.print(y + ": ");

            //in ogni posizione controllo se c'è una macchina che la occupa
            //se sì stampo l'indice della macchina, se no stampo quello che ho letto dal file
            //indicizza le colonne quindi x
            for (int x = 0; x < this.getXs(); x++) {
                boolean flag = false;
                for(Player player : players) {
                    if (player.getCar().getActualPosition().getX() == x && player.getCar().getActualPosition().getY() == y){
                        if(player.getCar().isRunning()){
                            System.out.print(player.getId() + " ");
                            flag = true;
                        }else {
                            System.out.print("x ");
                            flag = true;
                            break;
                        }
                    }
                }
                if(!flag)
                    System.out.print(grid[x][y] + " ");
            }
            if(y / 10 == 0) System.out.println("  " + y);
            else System.out.println(" " + y);
        }
        //stampo una riga per le coordinate x
        System.out.print("   ");
        for(int x = 0; x < this.getXs(); x++){
            if(x / 10 == 0) System.out.print(" " + x);
            else System.out.print("" + x);
        }
        System.out.println();
    }
}
