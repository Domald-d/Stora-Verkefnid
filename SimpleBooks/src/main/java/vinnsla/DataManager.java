package vinnsla;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DB = "bokanir.db";
    private static final String Path = "vidmot/simplebooks/db/bokanir.db";
    private static final String Local_Path = System.getProperty("user.home") + File.separator + DB;
    static {
        initializeDatabase();
    }

    private static void initializeDatabase(){
        File dbSkra = new File(Local_Path);
        if(!dbSkra.exists()){
            copyDatabase();
        }
        String ProjectPath = "src/main/resources/vidmot/simplebooks/db/bokanir.db";
        buatilTable(Local_Path);
        buatilTable(ProjectPath);
    }

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

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:sqlite:" + Local_Path);
    }

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
    public static void main(String[] args) {

    }
}
