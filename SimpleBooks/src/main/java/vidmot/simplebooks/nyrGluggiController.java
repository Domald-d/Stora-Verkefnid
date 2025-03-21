package vidmot.simplebooks;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import vinnsla.Bokanir;
import vinnsla.DataManager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public void setBokunarGogn(Bokanir bokanir){
        this.bokanaUppfaera = bokanir;
        fxNafn.setText(bokanir.getNafn());
        fxDate.setValue(java.time.LocalDate.parse(bokanir.getDate()));
        fxTime.setValue(LocalTime.parse(bokanir.getTime(),timi));
        fxBilNumer.setText(bokanir.getBilNumer());
        fxAth.setText(bokanir.getAth());
        button.setOnAction(e -> uppfaera());
    }

    @FXML
    private void buaTilBok(){
        if(fxDate.getValue() == null || fxTime.getValue() == null){
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
    }
    @FXML
    private void uppfaera(){
        if(bokanaUppfaera == null){
            return;
        }
        if(fxDate.getValue() == null || fxTime.getValue() == null){
            return;
        }
        boolean sucess = DataManager.uppfaeraBokun(bokanaUppfaera.getId(),fxNafn.getText(),fxDate.getValue().toString(),fxTime.getValue().format(timi),fxBilNumer.getText(),fxAth.getText());
        if(sucess){
            button.getScene().getWindow().hide();
        }
    }

    public static void main(String[] args) {

    }
}
