package vidmot.simplebooks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vinnsla.Bokanir;
import vinnsla.DataManager;

import java.io.IOException;

public class simpleBooksController {
    @FXML
    private TableView<Bokanir> fxBokun;
    @FXML
    TableColumn<Bokanir,Integer> idCol;
    @FXML
    TableColumn<Bokanir,String> nafnCol;
    @FXML
    TableColumn<Bokanir,String> dateCol;
    @FXML
    TableColumn<Bokanir,String> timeCol;
    @FXML
    TableColumn<Bokanir,String> bilCol;
    @FXML
    TableColumn<Bokanir,String> athCol;
    private ObservableList<Bokanir> bokanaListi = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        nafnCol.setCellValueFactory(cell -> cell.getValue().nafnProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
        timeCol.setCellValueFactory(cell -> cell.getValue().timeProperty());
        bilCol.setCellValueFactory(cell -> cell.getValue().bilNumerProperty());
        athCol.setCellValueFactory(cell -> cell.getValue().athProperty());

        synaBokanir();
    }
    private void synaBokanir(){
        bokanaListi.clear();
        bokanaListi.addAll(DataManager.getBokun());
        fxBokun.setItems(bokanaListi);
    }

    @FXML
    private void BokunarGluggi(){
        nyrGluggi("NyBok-view.fxml","Bóka Tíma",null);
    }

    @FXML
    private void uppfaeraGluggi(){
        Bokanir valinBokun = fxBokun.getSelectionModel().getSelectedItem();
        if(valinBokun == null){
            showAlert("Villa","þú hefur ekki valið neina bókun til að uppfæra");
            return;
        }
        nyrGluggi("uppfaera-view.fxml","Breyta Bókun",valinBokun);
    }

    private void nyrGluggi(String fxmlSkra,String title,Bokanir bokanir){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlSkra));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load(),320,240));

            nyrGluggiController controller = loader.getController();
            if(bokanir != null){
                controller.setBokunarGogn(bokanir);
            }
            stage.showAndWait();
            synaBokanir();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void eyda(){
        Bokanir valinBok = fxBokun.getSelectionModel().getSelectedItem();
        if(valinBok == null){
            showAlert("Villa","enginn bók valin");
            return;
        }
        boolean sucess = DataManager.eydaBokun(valinBok.getId());
        if(sucess){
            synaBokanir();
        }
        else {
            showAlert("Villa","gat ekki eytt bókun");
        }
    }

    private void showAlert(String title,String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
