package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTrackParser {
    public static Track loadTrackFromFile(String fileName) {
        //nella prima riga del file c'è scritta la direzione da seguire alla partenza, il numero di righe
        //e il numero di colonne della griglia separati da spazio
        //leggo tracciato da file : '#' sta per outOfTrack, '.' sta per inTrack, '_' sta per startingLine
        Track track = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] infoLine;
            //List<String> lines = new ArrayList<>();
            if((line = br.readLine()) != null){
                infoLine = line.split(" ");
                Direction direction = Direction.valueOf(infoLine[0].trim().toUpperCase());
                System.out.println("Starting direction : " + direction);
                int cols = Integer.parseInt(infoLine[1]);//coordinata x
                int rows = Integer.parseInt(infoLine[2]);//coordinata y
                track = new Track(cols, rows, direction);
                int y = 0;
                while ((line = br.readLine()) != null) {
                    for (int x = 0; x < cols; x++) {
                        track.setCell(x, y, line.charAt(x));
                        if (line.charAt(x) == '_')
                            track.addStartingPoint(x, y);
                    }
                    y++;
                }
            }
            //System.out.println("Grid dimensions : x = " + cols + ", y = " + rows);

            //for (int y = 1; y <= rows; y++) {//scorro le righe quindi y
            //    for (int x = 0; x < cols; x++) {//scorro le colonne quindi x
            //        track.setCell(x, y-1, lines.get(y).charAt(x));
            //        if(lines.get(y).charAt(x) == '_')
            //            track.addStartingPoint(x, y-1);
            //    }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }
}
