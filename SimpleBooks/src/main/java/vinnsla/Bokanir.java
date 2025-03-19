package vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bokanir {
    private final IntegerProperty id;
    private final StringProperty nafn;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty bilNumer;
    private final StringProperty ath;

    public Bokanir(int id,String nafn,String date,String time,String bilNumer,String ath){
        this.id = new SimpleIntegerProperty(id);
        this.nafn = new SimpleStringProperty(nafn);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.bilNumer = new SimpleStringProperty(bilNumer);
        this.ath = new SimpleStringProperty(ath);
    }

    public int getId(){
        return id.get();
    }
    public String getNafn(){
        return nafn.get();
    }
    public String getDate(){
        return date.get();
    }
    public String getTime(){
        return time.get();
    }
    public String getBilNumer(){
        return bilNumer.get();
    }
    public String getAth(){
        return ath.get();
    }

    public IntegerProperty idProperty(){
        return id;
    }
    public StringProperty nafnProperty(){
        return nafn;
    }
    public StringProperty dateProperty(){
        return date;
    }
    public StringProperty timeProperty(){
        return time;
    }
    public StringProperty bilNumerProperty(){
        return bilNumer;
    }
    public StringProperty athProperty(){
        return ath;
    }

    public void setNafn(String nafn){
        this.nafn.set(nafn);
    }
    public void setDate(String date){
        this.date.set(date);
    }
    public void setTime(String time){
        this.date.set(time);
    }
    public void setBilNumer(String bilNumer){
        this.bilNumer.set(bilNumer);
    }
    public void setAth(String ath){
        this.ath.set(ath);
    }

    public static void main(String[] args) {

    }
}
