/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jdbc;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Yug
 */

class ArrayListThread extends Thread{
    ArrayList<String> list=new ArrayList<>();
    
    public ArrayListThread(ArrayList<String> list) {
        this.list = list;
    }
    
    public void run() {
        int index = 0;
         for(int i=0;i<list.size();i++)  
           {  
            System.out.println("Hello "+list.get(i));   
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
           }  
    }
}

class Book implements Serializable{
    int Id;
    String name;
    String author;
    int price;
    int qty;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
}

public class Jdbc {
    ArrayList<String> list=new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jdbc j=new Jdbc();
        Scanner sc=new Scanner(System.in);
        int choice=0;
        while(choice!=7){
            System.out.println("==**==**==**==**==");
            System.out.println("1.Insert Record");
            System.out.println("2.Display Record");
            System.out.println("3.Update Record");
            System.out.println("4.Delete Record");
            System.out.println("5.Display Record By author");
            System.out.println("6.Exit");
            System.out.println("Enter Choice :");
            choice=sc.nextInt();
            switch(choice){
                case 1:
                    j.InsertRecord();
                    break;
                case 2:
                    j.DisplayRecord();
                    break;
                case 3:
                    j.UpdateRecord();
                    break;
                case 4:
                    j.DeleteRecord();
                    break;
                case 5:
                    ArrayListThread t = new ArrayListThread(j.AuthorByBook());
                    t.setDaemon(true);
                    t.start();
                    break;
            }
        }
    }
    
    public Connection getCon(){
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/libraby","root","");
            return con;
        } catch (Exception e) {
            System.out.println("Error :"+ e);
        }
        return null;
    }
    
    public void InsertRecord(){
        Scanner sc=new Scanner(System.in);
        Book b1=new Book();
        System.out.println("Enter Id:");
        b1.setId(sc.nextInt());
        System.out.println("Enter Name:");
        b1.setName(sc.next());
        System.out.println("Enter Author:");
        b1.setAuthor(sc.next());
        System.out.println("Enter Price:");
        b1.setPrice(sc.nextInt());
        System.out.println("Enter qty:");
        b1.setQty(sc.nextInt());
        try {
            Connection con=getCon();
            PreparedStatement pst=con.prepareStatement("Insert into bookTB (id,name,author,price,qty) values(?,?,?,?,?)");
            pst.setInt(1, b1.getId());
            pst.setString(2, b1.getName());
            pst.setString(3, b1.getAuthor());
            pst.setInt(4, b1.getPrice());
            pst.setInt(5, b1.getQty());
            
            int x=pst.executeUpdate();
            pst.close();
            con.close();
            if(x==1){
                System.out.println("Record Inserted");
            }else{
                System.out.println("Error Insert");
            }
        } catch (SQLException e) {
            System.err.println("Error :"+e);
        }
    }
    
    public void DisplayRecord(){
        try {
            Connection con=getCon();
            PreparedStatement pst=con.prepareStatement("select * from bookTB");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error :"+e);
        }
    }
    
    public void UpdateRecord(){
        Scanner sc=new Scanner(System.in);
        Book b1=new Book();
        System.out.println("Enter Id You Want to Update:");
        b1.setId(sc.nextInt());
        System.out.println("Enter Name:");
        b1.setName(sc.next());
        System.out.println("Enter Author:");
        b1.setAuthor(sc.next());
        System.out.println("Enter Price:");
        b1.setPrice(sc.nextInt());
        System.out.println("Enter qty:");
        b1.setQty(sc.nextInt());
        try {
            Connection con=getCon();
            PreparedStatement pst=con.prepareStatement("update bookTB set name=?,author=?,price=?,qty=? where id=?");
            pst.setString(1, b1.getName());
            pst.setString(2, b1.getAuthor());
            pst.setInt(3, b1.getPrice());
            pst.setInt(4, b1.getQty());
            pst.setInt(5, b1.getId());
            int x=pst.executeUpdate();
            pst.close();
            con.close();
            if(x==1){
                System.out.println("Record Updated");
            }else{
                System.err.println("Error Update");
            }
        } catch (SQLException e) {
            System.out.println("Error :"+e);
        }
    }
    
    public void DeleteRecord(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Id :");
        int id=sc.nextInt();
        try {
            Connection con =getCon();
            PreparedStatement pst=con.prepareStatement("delete from bookTb where id=?");
            pst.setInt(1, id);
            int x=pst.executeUpdate();
            pst.close();
            con.close();
            if(x==1){
                System.out.println("Record Deleted");
            }else{
                System.out.println("Error Delete");
            }
        }catch (SQLException e) {
            System.out.println("Error :"+e);
        }
    }
    public ArrayList<String> AuthorByBook(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Book Name :");
        
        String name=sc.next();
        try {
            Connection con=getCon();
            PreparedStatement pst=con.prepareStatement("select * from bookTB where name=?");
            pst.setString(1,name);
            ResultSet rs=pst.executeQuery(); 
            while(rs.next()){
                list.add(rs.getString(3));
            }
            rs.close();
            pst.close();
            con.close();
            return list;
        } catch (SQLException e) {
            System.err.println("Error :"+e);
        }
        return null;
    }
}
