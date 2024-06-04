package classes;

public enum Direction {
    TOP(1, 8, 2),
    TOP_RIGHT(2, 1, 3),
    RIGHT(3, 2, 4),
    DOWN_RIGHT(4, 3, 5),
    DOWN(5, 4, 6),
    DOWN_LEFT(6, 5, 7),
    LEFT(7, 6, 8),
    TOP_LEFT(8, 7, 1);

    public final int id;
    public final int prevDirId;
    public final int nextDirId;

    Direction(int id, int prevDirId, int nextDirId){
        this.id = id;
        this.prevDirId = prevDirId;
        this.nextDirId = nextDirId;
    }
}
