package vinnsla;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main klasin okkar sem sér um allar Database tengingar
 * og CRUD  aðgerðir
 * og býr til töflu/sendir database yfir á annan stað svo hægt
 * sé að nota hann eftir að er búið að compila forrit
 */
public class DataManager {
    private static final String DB = "bokanir.db";
    private static final String Path = "vidmot/simplebooks/db/bokanir.db";
    private static final String Local_Path = System.getProperty("user.home") + File.separator + DB;
    static {
        initializeDatabase();
    }
    /**
     * Static aðferð til að upphafsetja gagnarunn
     * hérna bý ég til töflu (ekki notað nema í byrjun)
     * og köllum á copy aðferð til að copya database í annað directory
     */
    private static void initializeDatabase(){
        File dbSkra = new File(Local_Path);
        if(!dbSkra.exists()){
            copyDatabase();
        }
        String ProjectPath = "src/main/resources/vidmot/simplebooks/db/bokanir.db";
        buatilTable(Local_Path);
        buatilTable(ProjectPath);
    }
    /**
     * Static aðferð notuð til að copya gagnagrunn yfir í annað directory
     * svo hægt sé að nota crud aðgerðir eftir að búið er að
     * compila/jar forrit
     */
    private static void copyDatabase(){
        try(InputStream in = DataManager.class.getResourceAsStream(Path);
            OutputStream out = new FileOutputStream(Local_Path)){
            if(in == null){
                throw new IOException("Villa gagnagrunnur fannst ekki");
            }
            byte[] buffer = new byte[1024];
            int lengd;
            while((lengd = in.read(buffer)) > 0){
                out.write(buffer,0,lengd);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * static aðferð sem býr til töflu í database
     * SQL aðferðir
     * við notum þetta í byrjun en ef tafla er til nú þegar þá gerist ekkert
     * @param pathASkra breyta sem tekur við Pathinu á db skrá
     */
    private static void buatilTable(String pathASkra){
        String sql = "CREATE TABLE IF NOT EXISTS bookings (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    "nafn TEXT NOT NULL, " + "date TEXT NOT NULL, " + "time TEXT NOT NULL, " + "bilNumer TEXT NOT NULL, "+ "athugasemdir TEXT)";
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathASkra);
        Statement stm = conn.createStatement()){
            stm.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * static aðferð sem tengir forrit við gagnagrunn
     * sqlite connect aðferð
     * @return skilum connection
     */
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:" + Local_Path);
    }
    /**
     * Create aðferð búum til nýja bók hérna
     * notum SQL aðferð
     * @param nafn tökum inn Nafn
     * @param date tökum inn dagsetningu
     * @param time tökum inn tímasetningu
     * @param bilNumer tökum inn bílanúmer
     * @param ath tökum inn athugasemd
     * @return true skilum true ef aðgerð heppnaðist
     */
    public static boolean nyBokun(String nafn, String date, String time, String bilNumer, String ath){
        String sql = "INSERT INTO bookings (nafn,date,time,bilNumer,athugasemdir) VALUES(?,?,?,?,?)";
        try(Connection conn = connect();
            PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1,nafn);
            pst.setString(2,date);
            pst.setString(3,time);
            pst.setString(4,bilNumer);
            pst.setString(5,ath);
            pst.executeUpdate();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     * Lista aðferð
     * hérna náum við í gögn úr gagnagrunn og sýnum þær
     * í Tableview í javafx
     * @return skilum bókunum
     */
    public static List<Bokanir> getBokun() {
        List<Bokanir> bokanir = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try(Connection conn = connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql)){
            while(rs.next()){
                bokanir.add(new Bokanir(rs.getInt("id"),rs.getString("nafn"),
                        rs.getString("date"),rs.getString("time"),
                        rs.getString("bilNumer"),rs.getString("athugasemdir")));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return bokanir;
    }
    /**
     * Aðferð til að uppfæra bók/breyta bókun
     * @param id tökum inn ID af bókun
     * @param nafn tökum inn nafn
     * @param  date tökum inn dagsetningu
     * @param time  tökum inn tímasetningu
     * @param bilNumer tökum inn bílnúmer
     * @param ath tökum inn athugasemd
     * notum SQL aðferð til að breyta bókun
     * @return true skilum true ef aðferð heppnaðist
     */
    public static boolean uppfaeraBokun(int id,String nafn,String date,String time,String bilNumer,String ath){
        String sql = "UPDATE bookings SET nafn=?,date=?,time=?,bilNumer=?,athugasemdir=? WHERE id=?";
        try(Connection conn = connect();
        PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setString(1,nafn);
            pst.setString(2,date);
            pst.setString(3,time);
            pst.setString(4,bilNumer);
            pst.setString(5,ath);
            pst.setInt(6,id);
            pst.executeUpdate();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     * Eyða bók aðferð
     * notum hana til að eyða bók úr gagnagrunni
     * @param id tökum inn id af bók
     * @return true skilum true ef við náum að eyða bók
     */
    public static boolean eydaBokun(int id){
        String sql = "DELETE FROM bookings WHERE id = ?";
        try(Connection conn = connect();
        PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setInt(1,id);
            pst.executeUpdate();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     * main aðferð ekki notuð
     * @param args ekki notað
     */
    public static void main(String[] args) {

    }
}
