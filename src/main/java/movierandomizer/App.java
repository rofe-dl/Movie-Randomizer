package movierandomizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DialogBox;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import controller.AddController;
import controller.MainController;


/**
 * Takes a .txt file of movie names and picks a random 
 * one out of it to watch and removes it from the file upon confirmation.
 * 
 * @author Rafidul Islam
 * @version 0.1
 * @since 09-06-2020
 * 
 */
public class App extends Application {

    /**
     * ArrayList of movie names made from the text file.
     */
    public ArrayList<String> toWatch = new ArrayList<>();
    
    /**
     * Icon
     */
    public Image icon = new Image("/view/icon.png");

    /**
     * Loader used to load fxml files.
     */
    public FXMLLoader loader;

    /**
     * Instance of the main controller of the program.
     */
    public MainController controllerInstance;

    /**
     * Loads the UI from the .fxml file into the global variable loader
     * @param fxmlDir  the directory of the .fxml file to load from
     * @return         the layout pane obtain from .load() as a Parent, to be downcasted from caller
     */
    public Parent loadFXML(String fxmlDir){
        Parent parent = null;
        try{
            loader = new FXMLLoader(getClass().getResource(fxmlDir));
            parent = loader.load(); //this also runs the initialize method of the fxml's controller
        }catch (IOException e){
            e.printStackTrace(); //should never happen
        }
    
        return parent;
    }

    /**
     * Method that runs before UI is shown
     */
    @Override
    public void init(){

        Scanner reader = null;

        try{
           reader = new Scanner(new File("towatch.txt"));
        }catch(Exception e){
            e.printStackTrace();
        }

        while(reader.hasNextLine()){
            toWatch.add(reader.nextLine());
        }

        reader.close();
    }

    /**
     * UI starts here.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try{

            AnchorPane pane = (AnchorPane) loadFXML("/view/UI.fxml");
            this.controllerInstance = loader.getController(); //sets global instance of main controller

            stage.setTitle("Movie Randomizer");
            stage.setScene(new Scene(pane));
            stage.getIcons().add(this.icon);
            stage.setMinHeight(480);
            stage.setMinWidth(480);

            this.controllerInstance.setAppInstance(this);
            this.controllerInstance.fillUpList();

            stage.show();
        }catch (Exception e){
            DialogBox.showError("File 'towatch.txt' not found in the same directory.", "File Not Found");
        }
    }

    /**
     * Shows box to add movie to the list if needed.
     */
    public void showAddMovie(){
        Stage stage = new Stage();
        AnchorPane pane = (AnchorPane) loadFXML("/view/AddUI.fxml");
        AddController controller = loader.getController();

        stage.setTitle("Movie Randomizer");
        stage.setResizable(false);
        stage.setScene(new Scene(pane));
        stage.getIcons().add(this.icon);
        stage.initModality(Modality.APPLICATION_MODAL);

        controller.setAppInstance(this);
        controller.setControllerInstance(controllerInstance);
        controller.setStage(stage);

        stage.showAndWait();
    }

    /**
     * 
     * @return  Returns random integer from 0 till number of movies.
     */
    public int getRandomInt(){
        Random rand = new Random();
        return rand.nextInt(this.toWatch.size());
    }

    /**
     * Method that runs when program is closed.
     * Movie names from arraylist is saved into the txt filet
     */
    @Override
    public void stop() throws Exception{
        String list = "";

        for(String movie : this.toWatch){
            list += movie + System.lineSeparator();
        }

        FileWriter writer = new FileWriter(new File("towatch.txt"));
        writer.write(list);

        writer.close();
    }

    public static void main(String[] args){
        launch(args);
    }



}