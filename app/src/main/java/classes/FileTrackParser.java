package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTrackParser {
    public static Track loadTrackFromFile(String fileName) {
        //nella prima riga del file c'è scritta la direzione da seguire alla partenza
        //leggo tracciato da file : '#' sta per outOfTrack, '.' sta per inTrack, '_' sta per startingLine
        Track track = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            Direction direction = Direction.valueOf(lines.get(0).trim().toUpperCase());
            System.out.println("Starting direction : " + direction);
            int rows = lines.size()-1;//coordinata y
            int cols = lines.get(rows).length();//coordinata x
            //System.out.println("Grid dimensions : x = " + cols + ", y = " + rows);
            track = new Track(cols, rows, direction);
            for (int y = 1; y <= rows; y++) {//scorro le righe quindi y
                for (int x = 0; x < cols; x++) {//scorro le colonne quindi x
                    track.setCell(x, y-1, lines.get(y).charAt(x));
                    if(lines.get(y).charAt(x) == '_')
                        track.addStartingPoint(x, y-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }
}
