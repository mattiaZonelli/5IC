import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe d'utilita per il database
 * Created by pool on 14/11/16.
 */
public class DBUtility {
    Connection c;

    /**
     * Effettua la connessione
     */
    public DBUtility(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./NegozioScarpeSpecialistiche.sqlite");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Mostra il catalogo del database
     * @return Array con gli oggetti risultanti dalla query
     */
    public synchronized Object[] showCatalog(){
        ArrayList<String> result = new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select model,brand,cost from Catalog");
            Statement brandStm = c.createStatement();
            while(rs.next()){
                result.add(rs.getString("model"));
                brandStm = c.createStatement();
                ResultSet brandResult = brandStm.executeQuery("select name from Brand where id="+rs.getInt("brand"));
                result.set(result.size()-1,result.get(result.size()-1)+"\t"+brandResult.getString("name")+"\t"+rs.getString("cost"));
            }
        }catch(SQLException e){
                e.printStackTrace();
        }

        return result.toArray();
    }

    /**
     * Ritorna gli oggetti di un determinato brand
     * @param brand Brand da cercare
     * @return Oggetti risultanti dalla query
     */

    public synchronized Object[] showByBrand(String brand) {
        ArrayList<String> result = new ArrayList();
        try {
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select id from Brand where name=\""+brand+"\"");
            int brandId = rs.getInt("id");
            Statement stm1 = c.createStatement();
            rs = stm1.executeQuery("select model,cost from Catalog where brand="+brandId);
            while(rs.next()){
                result.add(rs.getString("model")+ "\t"+brand+"\t"+rs.getString("cost"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result.toArray();
    }

}
