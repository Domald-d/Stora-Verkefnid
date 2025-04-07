package vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Main klasin okkar Bokanir
 * þetta er klasin sem sér um allar
 * get/set aðferðir fyrir gögnin okkar
 * úr gagnagrunni
 * geymum allt í string/int properties
 */
public class Bokanir {
    private final IntegerProperty id;
    private final StringProperty nafn;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty bilNumer;
    private final StringProperty ath;

    /**
     * Smiður fyrir gögninn okkar
     * @param id tökum inn id af bók
     * @param nafn tökum inn nafn
     * @param date tökum inn dagsetningu
     * @param time tökum inn tímasetningu
     * @param bilNumer tökum inn bílnúmer
     * @param ath tökum inn athugasemdir
     */
    public Bokanir(int id,String nafn,String date,String time,String bilNumer,String ath){
        this.id = new SimpleIntegerProperty(id);
        this.nafn = new SimpleStringProperty(nafn);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.bilNumer = new SimpleStringProperty(bilNumer);
        this.ath = new SimpleStringProperty(ath);
    }
    /**
     * get aðferð fyrir id
     * @return skilum id af bók
     */
    public int getId(){
        return id.get();
    }
    /**
     * get aðferð fyrir nafn
     * @return skilum nafni
     */
    public String getNafn(){
        return nafn.get();
    }
    /**
     * get aðferð fyrir dagsetingu
     * @return skilum dagsetningu
     */
    public String getDate(){
        return date.get();
    }
    /**
     * get aðferð fyrir tíma
     * @return skilum tíma
     */
    public String getTime(){
        return time.get();
    }
    /**
     * get aðferð fyrir bílnúmer
     * @return skilum bílnúmeri
     */
    public String getBilNumer(){
        return bilNumer.get();
    }
    /**
     * get aðferð fyrir athugasemdir
     * @return skilum athugasemdum
     */
    public String getAth(){
        return ath.get();
    }
    /**
     * Property aðferð
     * @return skilum id
     */
    public IntegerProperty idProperty(){
        return id;
    }
    /**
     * Property aðferð
     * @return skilum nafni
     */
    public StringProperty nafnProperty(){
        return nafn;
    }
    /**
     * Property aðferð
     * @return skilum date
     */
    public StringProperty dateProperty(){
        return date;
    }
    /**
     * Property aðferð
     * @return skilum tíma
     */
    public StringProperty timeProperty(){
        return time;
    }
    /**
     * Property aðferð
     * @return skilum bílnúmeri
     */
    public StringProperty bilNumerProperty(){
        return bilNumer;
    }
    /**
     * Property aðferð
     * @return skilum athugasemd
     */
    public StringProperty athProperty(){
        return ath;
    }
    /**
     * Set aðferð fyrir nafn
     * @param nafn tökum inn nafn
     */
    public void setNafn(String nafn){
        this.nafn.set(nafn);
    }
    /**
     * Set aðferð fyrir dagsetningu
     * @param date tökum inn date
     */
    public void setDate(String date){
        this.date.set(date);
    }
    /**
     * Set aðferð fyrir tímasetningu
     * @param time tökum inn tíma
     */
    public void setTime(String time){
        this.date.set(time);
    }
    /**
     * Set aðferð fyrir bílnúmer
     * @param bilNumer tökum inn bílnúmer
     */
    public void setBilNumer(String bilNumer){
        this.bilNumer.set(bilNumer);
    }
    /**
     * Set aðferð fyrir athugasemd
     * @param ath tökum inn athugasemd
     */
    public void setAth(String ath){
        this.ath.set(ath);
    }
    /**
     * main aðferð ekki notuð
     * @param args ekki notað
     */
    public static void main(String[] args) {

    }
}
