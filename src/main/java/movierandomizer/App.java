package movierandomizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import controller.AddController;
import controller.MainController;


/**
 * JavaFX App
 */
public class App extends Application {

    public ArrayList<String> toWatch = new ArrayList<>();
    public Image icon = new Image("/view/icon.png");
    public FXMLLoader loader;
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
            parent = loader.load(); //this also runs the initialise method of the fxml's controller
        }catch (IOException e){
            e.printStackTrace(); //should never happen
        }
    
        return parent;
    }

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

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane pane = (AnchorPane) loadFXML("/view/UI.fxml");
        this.controllerInstance = loader.getController();

        stage.setTitle("Movie Randomizer");
        stage.setResizable(false);
        stage.setScene(new Scene(pane));
        stage.getIcons().add(this.icon);

        this.controllerInstance.setAppInstance(this);
        this.controllerInstance.fillList();

        stage.show();
    }

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

    public int getRandomInt(){
        Random rand = new Random();
        return rand.nextInt(this.toWatch.size());
    }

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