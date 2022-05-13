package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MetroController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> cmbArrivo;

    @FXML
    private ComboBox<Fermata> cmbPartenza;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private TableColumn<Fermata, String> colonnaFermata;
    
    @FXML
    private TableView<Fermata> tablePercorso;
    
    private Model model;
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Fermata> fermate = this.model.getFermate();
    	cmbPartenza.getItems().addAll(fermate);
    	cmbArrivo.getItems().addAll(fermate);
    }

    @FXML
    void handleCerca(ActionEvent event) {
    	Fermata partenza = cmbPartenza.getValue();
    	Fermata arrivo = cmbArrivo.getValue();
    	
    	if(partenza!=null && arrivo!=null && !partenza.equals(arrivo)) {
    		List<Fermata> percorso = model.calcolaPercorso(partenza, arrivo);
    		
    		tablePercorso.setItems(FXCollections.observableArrayList(percorso));
    		txtResult.setText("Percorso trovato con "+percorso.size()+" stazioni");
    	}else {
    		txtResult.setText("Errore");
    	}

    }

    @FXML
    void initialize() {
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        colonnaFermata.setCellValueFactory(new PropertyValueFactory<Fermata, String>("nome"));
    }

}
