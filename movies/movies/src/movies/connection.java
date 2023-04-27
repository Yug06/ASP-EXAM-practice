package movies;
import java.sql.*;

public class connection {
    protected Connection con;
    protected PreparedStatement prst;
    protected ResultSet rs;
    
    public connection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "jaydev", "jaydev");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    } 
}
