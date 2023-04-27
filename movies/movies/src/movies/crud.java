package movies;
import java.sql.SQLException;
import java.io.*;

public class crud extends connection {

    public crud() {
        super();
    }
    
    public int addMovies(Movies m){
        try{
            String preQuery = "insert into movies(movie_name, movie_type, description) values(?,?,?)";
            super.prst = super.con.prepareStatement(preQuery);
            super.prst.setString(1, m.movie_name);
            super.prst.setString(2, m.movie_type);
            super.prst.setString(3, m.movie_description);
            super.prst.executeUpdate();
            return 1;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return 0;
        }
        catch(Exception e){
             System.err.println(e.getMessage());
            return 0;
        }
    }
    
    public void readFile(String fName){
        try{
        FileReader movieReader = new FileReader(fName);
        int a;
        while((a = movieReader.read()) != -1){
            System.out.print((char)a);
        }
        }catch(FileNotFoundException e){
           System.err.println(e.getMessage()); 
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        
    }
    
    public int listMovies(String m_type){
        try{
            String preQuery = "select * from movies where movie_type like ?";
            super.prst = super.con.prepareStatement(preQuery);
            super.prst.setString(1, m_type);
            super.prst.executeQuery();
            String text = "Movie Name\t\t|\t\tType\t\t|\t\tDescription\n";
            File movieFile = new File("movieType.txt");
            movieFile.createNewFile();
            if(super.rs.next()){
                text+= super.rs.getString("movie_name")+"\t\t|\t\t"+super.rs.getString("movie_type")+"\t\t|\t\t"+super.rs.getString("description") + "\n";
                while(super.rs.next()){
                   text+= super.rs.getString("movie_name")+"\t\t|\t\t"+super.rs.getString("movie_type")+"\t\t|\t\t"+super.rs.getString("description") + "\n"; 
                }
                
                FileWriter movieWriter = new FileWriter("movieType.txt");
                movieWriter.write(text);
                movieWriter.close();
            }else{
                System.err.println("Abe Aj tu Mere Hath se petega, Pehle data to dal");
                return 0;
            }
            
            return 1;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return 0;
        }
        catch(Exception e){
             System.err.println(e.getMessage());
            return 0;
        }
    }
    
    public  int list(){
        try{
            String preQuery = "select * from movies";
            super.prst = super.con.prepareStatement(preQuery);
            super.rs = super.prst.executeQuery();
            String text = "Movie Name\t\t|\t\tType\t\t|\t\tDescription\n";
            File movieFile = new File("movie.txt");
            movieFile.createNewFile();
            if(super.rs.next()){
                text+= super.rs.getString("movie_name")+"\t\t|\t\t"+super.rs.getString("movie_type")+"\t\t|\t\t"+super.rs.getString("description") + "\n";
                while(super.rs.next()){
                   text+= super.rs.getString("movie_name")+"\t\t|\t\t"+super.rs.getString("movie_type")+"\t\t|\t\t"+super.rs.getString("description") + "\n"; 
                }
                
                FileWriter movieWriter = new FileWriter("movie.txt");
                movieWriter.write(text);
                movieWriter.close();
            }else{
                System.err.println("Abe Aj tu Mere Hath se petega, Pehle data to dal");
                return 0;
            }
            
            return 1;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return 0;
        }
        catch(Exception e){
             System.err.println(e.getMessage());
            return 0;
        }
    }
    
    public int updateMovie(Movies um, int id){
        try{
            String preQuery = "update movies set movie_name = ?, movie_type = ?, description = 1 where id = ?";
            super.prst = super.con.prepareStatement(preQuery);
            super.prst.setString(1, um.movie_name);
            super.prst.setString(2, um.movie_type);
            super.prst.setString(3, um.movie_description);
            super.prst.setInt(4, id);
            super.prst.executeUpdate();
            return 1;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return 0;
        }
        catch(Exception e){
             System.err.println(e.getMessage());
            return 0;
        }
    }
    
    public int deleteMovie(int id){
        try{
            String preQuery = "delete from movies where id = ?";
            super.prst = super.con.prepareStatement(preQuery);
            super.prst.setInt(1, id);
            super.prst.executeUpdate();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
    
    
}
