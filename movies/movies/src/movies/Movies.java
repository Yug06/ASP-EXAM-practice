package movies;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Movies implements Serializable{
    String movie_name;
    String movie_type;
    String movie_description;
    
    static class iAmDaemon extends Thread{
        
        public void run(){
            while(true){
                System.err.println("I Am Daemon Thread");
                try{
                Thread.sleep(10000);
                }catch(Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public Movies(String m_name,String m_type, String m_description) {
        movie_name = m_name;
        movie_type = m_type;
        movie_description = m_description;
    }
    
    public static void main(String[] args) {
        boolean programStart = true;
        int option;
        Scanner read = new Scanner(System.in);
        Movies m1,um1;
        crud c = new crud();
        iAmDaemon daemon = new iAmDaemon();
        daemon.setDaemon(true);
        daemon.start();
        
        while(programStart){
            System.err.println("--------------------------WelCome To Sinema Movies----------------------------");
            System.out.println("1.ADD MOVIE \n2.LIST MOVIE BY TYPE \n3.UPDATE MOVIE \n4.DELETE MOVIE \n5.List All Movies \n6.EXIT");
            System.out.println("Enter Your Option : ");
            option = read.nextInt();
            
            switch(option) {
                case 1:
                    System.out.println("Enter Name of Movie  : ");
                    String mn = read.next();
                    System.out.println("Enter Type Of Movie  :");
                    String mt = read.next();
                    System.out.println("Enter Description about Movie : ");
                    String md = read.next();
                    m1 = new Movies(mn, mt, md);
                    
//                   serialized
                    try{
                     FileOutputStream file = new FileOutputStream("movies.ser");
                     ObjectOutputStream out = new ObjectOutputStream(file);
                     
                     out.writeObject(m1);
                     
                     out.close();
                     file.close();
                     
                    }catch(IOException e){
                        System.err.println(e.getMessage());
                    }
            
                    c.addMovies(m1);
                    break;
                case 2 : 
                    System.out.println("Enter Movie Type To List : ");
                    String type = read.next();
                    c.listMovies(type);
                    c.readFile("movieType.txt");
                    break;
                case 3:
                    c.readFile("movie.txt");
                    System.out.println("Enter Id of Movie to update : ");
                    int id = read.nextInt();
                    System.out.println("Enter Name of Movie  : ");
                    String umn = read.next();
                    System.out.println("Enter Type Of Movie  :");
                    String umt = read.next();
                    System.out.println("Enter Description about Movie : ");
                    String umd = read.next();
                    um1 = new Movies(umn, umt, umd);
                    
                    try{
                     FileOutputStream file = new FileOutputStream("update_movies.ser");
                     ObjectOutputStream out = new ObjectOutputStream(file);
                     
                     out.writeObject(um1);
                     
                     out.close();
                     file.close();
                     
                    }catch(IOException e){
                        System.err.println(e.getMessage());
                    }
                    c.updateMovie(um1, id);
                    break;
                case 4:
                    c.readFile("movie.txt");
                    System.out.println("Enter Id of Movie to update : ");
                    int delId = read.nextInt();
                    c.deleteMovie(delId); 
                    break;
                case 5:
                    c.list();
                    c.readFile("movie.txt");
                    break;
                case 6:
                    programStart = false;
                    break;
                default:
                    System.err.println("Me kenda Bina Dimang Ke Adami Jitne option milen hain unmse dalna!!");
            }
        }
    }
       
}
