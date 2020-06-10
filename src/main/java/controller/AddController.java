package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import movierandomizer.App;

public class AddController {

    /**
     * Instance of the main running app.
     */
    App instance;

    /**
     * Instance of the main controller of the program.
     */
    MainController controllerInstance;

    /**
     * Stage of the add movie screen, stored so stage.close() can be called here.
     */
    Stage stage;

    @FXML
    private Button addButton;

    @FXML
    private TextField textField;

    /**
     * Movie added to listview and arraylist.
     */
    @FXML
    private void add(){
        String movieName = textField.getText().trim();
        if(!movieName.isEmpty()){
            this.instance.toWatch.add(movieName);
            this.controllerInstance.addItem(movieName);
        }

        this.stage.close();
    }

    @FXML
    private void initialize(){
        textField.setOnKeyPressed(event -> {            
            if(event.getCode() == KeyCode.ENTER) {                 
                add();             
            }           
        });
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setAppInstance(App instance){
        this.instance = instance;
    }

    public void setControllerInstance(MainController controllerInstance){
        this.controllerInstance = controllerInstance;
    }
    
}