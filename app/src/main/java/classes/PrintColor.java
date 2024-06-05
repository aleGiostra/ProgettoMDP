package classes;

public enum PrintColor {
    //enum with colors
    //assign every player with a color (human player might choose, bot players whatever)
    //store in car object the list of positions ever occupied during the race
    //when printing the track, on every position check the abovementioned list and print the dot
    //colored with "the last player who has stepped on that position"'s color
    BLACK("\\033[0;30m"),
    RED("\\033[0;31m"),
    GREEN("\\033[0;32m"),
    YELLOW("\\033[0;33m"),
    BLUE("\\033[0;34m"),
    PURPLE("\\033[0;35m"),
    CYAN("\\033[0;36m"),
    WHITE("\\033[0;37m"),
    BRIGHT_BLACK("\\033[1;30m"),
    BRIGHT_RED("\\033[1;31m"),
    BRIGHT_GREEN("\\033[1;32m"),
    BRIGHT_YELLOW("\\033[1;33m"),
    BRIGHT_BLUE("\\033[1;34m"),
    BRIGHT_PURPLE("\\033[1;35m"),
    BRIGHT_CYAN("\\033[1;36m"),
    BRIGHT_WHITE("\\033[1;37m");

    public final String code;

    PrintColor(String code){
        this.code = code;
    }
}
