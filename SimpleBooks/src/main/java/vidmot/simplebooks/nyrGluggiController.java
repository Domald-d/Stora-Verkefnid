package vidmot.simplebooks;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import vinnsla.Bokanir;
import vinnsla.DataManager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/**
 * Main klasinn okkar
 * klasinn er notaður til að búa til nýja glugga
 * fyrir crud aðgerðir
 * geymum fxml breytur fyrir gögn sem fara í database
 */
public class nyrGluggiController {
    @FXML
    private TextField fxNafn;
    @FXML
    private DatePicker fxDate;
    @FXML
    private ComboBox<LocalTime> fxTime;
    @FXML
    private TextField fxBilNumer;
    @FXML
    private TextField fxAth;
    @FXML
    private Button button;

    private static final DateTimeFormatter timi = DateTimeFormatter.ofPattern("HH:mm");


    private Bokanir bokanaUppfaera;

    /**
     * initialize aðferð
     * notum hana fyrir tímasetningu
     */
    @FXML
    public void initialize(){
        fxTime.setItems(FXCollections.observableArrayList(IntStream.range(8,18).boxed().flatMap(hour -> Stream.of(0,5,10,15,20,25,30,35,40,45,50,55).map(minute -> LocalTime.of(hour,minute))).collect(Collectors.toList())));
        fxTime.setConverter(new StringConverter<LocalTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            public String toString(LocalTime localTime) {
                return localTime != null ? formatter.format(localTime) : "";
            }

            public LocalTime fromString(String s) {
                return LocalTime.parse(s,formatter);
            }
        });
    }
    /**
     * aðferð sem tekur inn bokanir klasa
     * @param bokanir tökum inn object af klasa
     */
    public void setBokunarGogn(Bokanir bokanir){
        this.bokanaUppfaera = bokanir;
        fxNafn.setText(bokanir.getNafn());
        fxDate.setValue(java.time.LocalDate.parse(bokanir.getDate()));
        fxTime.setValue(LocalTime.parse(bokanir.getTime(),timi));
        fxBilNumer.setText(bokanir.getBilNumer());
        fxAth.setText(bokanir.getAth());
        button.setOnAction(e -> uppfaera());
    }
    /**
     * Aðferð notum hana til að búa til nýja bók
     * notum alert aðferðir
     * til að tilkynna notanda um hvaða gögn þarf og fleirra
     * við búum til nýjar bókanir hérna og sendum þær í gagnagrunn
     */
    @FXML
    private void buaTilBok(){
        if(fxNafn.getText().isBlank() || fxDate.getValue() == null || fxTime.getValue() == null || fxBilNumer.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vantar Upplýsingar");
            alert.setHeaderText(null);
            alert.setContentText("Fylltu inn alla þá reiti sem eru merktir með *");
            alert.showAndWait();
            return;
        }
        String nafn = fxNafn.getText();
        String date = fxDate.getValue().toString();
        String time = fxTime.getValue().format(timi);
        String bilNumer = fxBilNumer.getText();
        String ath = fxAth.getText();
        boolean sucess = DataManager.nyBokun(nafn,date,time,bilNumer,ath);
        if(sucess){
            button.getScene().getWindow().hide();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagnagrunns Villa");
            alert.setHeaderText(null);
            alert.setContentText("Ekki tókst að búa til Bók reyndu aftur");
            alert.showAndWait();
        }
    }
    /**
     * uppfærslu aðferð
     * hérna uppfærum við eða breytum bókun
     * notum alert aðferðir eins og að ofan
     * sendum breyttu bók aftur í gagnagrunn
     */
    @FXML
    private void uppfaera(){
        if(bokanaUppfaera == null){
            return;
        }
        if(fxNafn.getText().isBlank() || fxDate.getValue() == null || fxTime.getValue() == null || fxBilNumer.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vantar Upplýsingar");
            alert.setHeaderText(null);
            alert.setContentText("Ekki hægt að uppfæra Bók með tómum upplýsingum");
            alert.showAndWait();
            return;
        }
        boolean sucess = DataManager.uppfaeraBokun(bokanaUppfaera.getId(),fxNafn.getText(),fxDate.getValue().toString(),fxTime.getValue().format(timi),fxBilNumer.getText(),fxAth.getText());
        if(sucess){
            button.getScene().getWindow().hide();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gagnagrunns Villa");
            alert.setHeaderText(null);
            alert.setContentText("Ekki tókst að uppfæra Bók reyndu aftur");
            alert.showAndWait();
        }
    }
    /**
     * aðferð til að loka glugga með cancel takka
     */
    @FXML
    private void loka(){
        fxNafn.getScene().getWindow().hide();
    }
    /**
     * main aðferð ekki notuð
     * @param args ekki notað
     */
    public static void main(String[] args) {

    }
}
