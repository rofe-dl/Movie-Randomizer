package movierandomizer;
/**
 * JAR files won't run with a main class that inherits a third
 * party class, JAR file runs from here that calls the actual main
 * class.
 */
public class Start {
    public static void main(String[] args){
        App.main(args);
    }
}