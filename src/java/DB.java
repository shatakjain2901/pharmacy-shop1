/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/DB"})
public class DB extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        try{
                Class.forName("org.gjt.mm.mysql.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1","root","");
                PreparedStatement stmt=con.prepareStatement("Create database PharmacyShop1");
                stmt.executeUpdate();
                stmt=con.prepareStatement("use PharmacyShop1");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Medicines(mid int Primary Key,name varchar(20),description varchar(30),price int,qtyInStock int,Expiry Date,Photo MediumBlob)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Members(Email varchar(30) Primary Key,Name varchar(30),Mobile varchar(10),Address varchar(30),City varchar(30),State varchar(30),PinCode varchar(6),SecretQuestion varchar(40),answer varchar(30))");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Users(email varchar(30),upass varchar(20),utype varchar(20))");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table Orders(Oderid int,Odate date,email varchar(30),TotalAmt int)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Create Table OrderItems(Oderid int,mid int,name varchar(30),price int,qty int)");
                stmt.executeUpdate();
                stmt=con.prepareStatement("Insert into users values('admin@yahoo.com','admin123','admin')");
                stmt.executeUpdate();
                PrintWriter out=response.getWriter();
                out.write("Done");
        }catch(Exception ee){System.out.println(ee);}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
