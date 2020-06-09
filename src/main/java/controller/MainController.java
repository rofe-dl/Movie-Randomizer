package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import movierandomizer.App;

public class MainController {

    App instance;
    private int randomIndex = -1;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button randomizeButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button confirmRemoveButton;

    @FXML
    private TextField textField;

    @FXML
    private void initialize(){
        textField.setEditable(false);
    }

    @FXML
    private void add(){
        this.instance.showAddMovie();
    }

    @FXML
    private void delete(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if(selectedIndex != -1){
            listView.getItems().remove(selectedIndex);
            this.instance.toWatch.remove(selectedIndex);
        }
    }

    @FXML
    private void randomize(){
        int generatedIndex = this.instance.getRandomInt();
        this.listView.getSelectionModel().select(generatedIndex);
        this.textField.setText(this.instance.toWatch.get(generatedIndex));
        this.randomIndex = generatedIndex;
    }

    @FXML
    private void confirmRemove(){
        if(this.randomIndex != -1){
            listView.getItems().remove(randomIndex);
            this.instance.toWatch.remove(randomIndex);
            this.textField.setText(null);
        }
    }

    public void addItem(String item){
        listView.getItems().add(item);
    }

    public void setAppInstance(App instance){
        this.instance = instance;
        
    }

    public void fillList(){
        listView.getItems().addAll(instance.toWatch);
    }

}