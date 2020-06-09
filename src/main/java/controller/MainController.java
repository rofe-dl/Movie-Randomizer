package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import movierandomizer.App;
import util.DialogBox;

public class MainController {

    /**
     * Instance of the main running app.
     */
    App instance;

    /**
     * Randomindex generated and stored in from randomize().
     * Default -1 meaning randomize was never pressed.
     */
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

    /**
     * Brings up the add movie screen and calls its controller.
     */
    @FXML
    private void add(){
        this.instance.showAddMovie();
    }

    /**
     * Item selected from listview is deleted from arraylist and listview.
     * selectedIndex == -1 if nothing is selected.
     */
    @FXML
    private void delete(){
        int selectedIndex = listView.getSelectionModel().getSelectedIndex(); //index of the item selected
        if(selectedIndex != -1){
            boolean answer = DialogBox.showConfirmation("Are you sure you want to delete " + this.instance.toWatch.get(selectedIndex) + "?", "Delete Confirmation");
            if(answer){

                listView.getItems().remove(selectedIndex);
                this.instance.toWatch.remove(selectedIndex);
            }
        }else{
            DialogBox.showDialog("Select a movie first", "Not Selected");
        }
    }

    /**
     * Random integer is generated and element at that index is shown.
     * This value is also saved in global variable to be used by confirmRemove().
     */
    @FXML
    private void randomize(){
        int generatedIndex = this.instance.getRandomInt();
        this.textField.setText(this.instance.toWatch.get(generatedIndex));
        this.randomIndex = generatedIndex;
    }

    /**
     * If randomIndex == 1, randomize was never pressed so this button does nothing
     * otherwise the element chosen by randomize is then removed from listview
     * and the arraylist.
     */
    @FXML
    private void confirmRemove(){
        if(this.randomIndex != -1){
            listView.getItems().remove(randomIndex);
            this.instance.toWatch.remove(randomIndex);
            this.textField.setText(null);
        }
    }

    /**
     * Adds an item to the list view.
     * @param item      item to be added
     */
    public void addItem(String item){
        listView.getItems().add(item);
    }

    /**
     * Sets the instance of the main App object.
     * @param instance     instance of the running program
     */
    public void setAppInstance(App instance){
        this.instance = instance;
    }

    /**
     * Fills up the list view on the screen with items from the arraylist in App object.
     */
    public void fillUpList(){
        listView.getItems().addAll(instance.toWatch);
    }

}