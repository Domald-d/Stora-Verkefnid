package vidmot.simplebooks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vinnsla.Bokanir;
import vinnsla.DataManager;

import java.io.IOException;
/**
 * Main klasinn okkar
 * hérna höldum við utan um Table view og hvernig bókanir eru sýndar
 */
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
    private FilteredList<Bokanir> filteredList;
    /**
     * init aðferð
     * hérna setjum við gildi inn í table view töflu
     * erum líka með filtered list fyrir  search aðferð
     */
    @FXML
    public void initialize(){
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        nafnCol.setCellValueFactory(cell -> cell.getValue().nafnProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
        timeCol.setCellValueFactory(cell -> cell.getValue().timeProperty());
        bilCol.setCellValueFactory(cell -> cell.getValue().bilNumerProperty());
        athCol.setCellValueFactory(cell -> cell.getValue().athProperty());

        synaBokanir();

        filteredList = new FilteredList<>(bokanaListi, p -> true);
        fxBokun.setItems(filteredList);

    }
    /**
     * aðferð til að sýna bókanir
     */
    private void synaBokanir(){
        bokanaListi.clear();
        bokanaListi.addAll(DataManager.getBokun());
    }
    /**
     * aðferð sem sýnir bókunar glugga
     * köllum á aðferð með fxml skrá
     */
    @FXML
    private void BokunarGluggi(){
        nyrGluggi("NyBok-view.fxml","Bóka Tíma",null);
    }
    /**
     * aðferð fyrir uppfærslu glugga
     * við notum selection aðferð þar sem við smellum á bók sem á að uppfæra
     * köllum á glugga aðferð með fxml skrá
     */
    @FXML
    private void uppfaeraGluggi(){
        Bokanir valinBokun = fxBokun.getSelectionModel().getSelectedItem();
        if(valinBokun == null){
            showAlert("Villa","þú hefur ekki valið neina bókun til að uppfæra");
            return;
        }
        nyrGluggi("uppfaera-view.fxml","Breyta Bókun",valinBokun);
    }
    /**
     * aðferð sem býr til glugga fyrir nýjar bókanir/uppfærslu
     * @param fxmlSkra tekur inn nafn á fxml skrá
     * @param title tökum inn titil á glugga
     * @param bokanir tökum inn gögn
     *
     */
    private void nyrGluggi(String fxmlSkra,String title,Bokanir bokanir){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlSkra));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load(),260,290));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.jpg")));

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
    /**
     * eyða aðferð
     * notað til að eyða ákveðnri bók
     * í ui
     */
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
    /**
     * aðferð fyrir villu meðhöndlanir
     * @param title tekur inn titil á glugga
     * @param msg tekur inn skilaboð til að sýna notanda
     */
    private void showAlert(String title,String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    /**
     * hætta aðferð fyrir forrit
     * slekkur á forriti
     */
    @FXML
    private void Haetta(){
        System.exit(0);
    }
    /**
     * About aðferð
     * hérna sýnum við notanda um forrit
     */
    @FXML
    private void About(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Um forrit");
        alert.setHeight(600);
        alert.setHeaderText("Simple Booking");
        alert.setContentText("Þetta er einfalt Bókunar Forrit.\n\n" +
                "ýttu á Hætta undir Skrá í menu til að loka forriti\n\n"+
                "til að getað Bókað nýjan viðskiptavin farðu í Bókanir glugga og smelltu á Bóka tíma\n\n"+
                "til að uppfæra bókun klikkaðu á bókun í Töflu og smelltu á Bókanir í menu og smelltu svo á uppfæra Bókun\n\n"+
                "Til að eyða bókun smelltu á Bókun í töflu sem á að eyða og farðu í Bókanir í menu og smelltu á Eyða Bókun\n\n"+
                "Til að leita af ákveðnri bók skaltu smella á search og ýta á Search\n\n"+
                "til að hreinsa síu og fá listan til baka ýttu á Hreinsa undir search");
        alert.showAndWait();
    }
    /**
     * Leita aðferð
     * hérna getum við leitað af ákveðnri bók
     * með nafni eða bílnúmeri
     * notum javafx filtered list
     * var auðveldara en að nota SQL aðferð
     */
    @FXML
    private void Leita(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Leita");
        dialog.setHeaderText("Leitaðu eftir Bílnúmeri eða nafni");
        dialog.setContentText("Leitarorð:");
        dialog.showAndWait().ifPresent(input ->{
            String filter = input.toLowerCase();
            filteredList.setPredicate(bokun -> bokun.getNafn().toLowerCase().contains(filter)
            || bokun.getBilNumer().toLowerCase().contains(filter));

            if(filteredList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ekkert fannst");
                alert.setHeaderText(null);
                alert.setContentText("Engar niðurstöður fundust fyrir: " + input);
                alert.showAndWait();
                filteredList.setPredicate(b->true);
            }
        });
    }
    /**
     * Hreinsa aðferð
     * notað til að hreinsa leitar orð í search function
     * svo að við getum aftur séð original lista
     * án þess að loka forriti
     */
    @FXML
    private void Hreinsa(){
        filteredList.setPredicate(b->true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leit hreinsuð");
        alert.setHeaderText(null);
        alert.setContentText("Sía er hreinsuð");
        alert.showAndWait();
    }
}
