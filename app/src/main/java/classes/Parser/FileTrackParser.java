package classes.Parser;

import classes.Direction;
import classes.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTrackParser {
    public static Track loadTrackFromFile(String fileName) {
        //nella prima riga del file c'è scritta la direzione da seguire alla partenza, il numero di righe
        //e il numero di colonne della griglia separati da spazio
        //leggo tracciato da file : '#' sta per outOfTrack, '.' sta per inTrack, '_' sta per startingLine
        Track track = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] infoLine;
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
                        if(line.charAt(x) == '*')
                            track.addFinishLinePoint(x, y);
                    }
                    y++;
                }
            }
            if(track.getFinishLinePoints().isEmpty())
                track.setFinishLinePoints(track.getStartingLinePoints());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }
}
