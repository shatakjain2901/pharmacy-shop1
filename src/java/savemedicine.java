/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/savemedicine"})
public class savemedicine extends HttpServlet {

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
                int size=request.getContentLength();
                InputStream in=request.getInputStream();
                byte[] data=new byte[size];
                int n=in.read(data);
                int remain=size-n;
                while(remain>0)
                {
                    n=in.read(data,size-remain,remain);
                    remain=remain-n;
                }                
                String str=new String(data);
                int p1=str.indexOf("\r\n");
                String bndry=str.substring(0,p1);
                p1=str.indexOf("name=\"t1\"",p1+1)+13;
                int p2=str.indexOf("\r\n",p1+1);
                String t1=str.substring(p1,p2);
                p1=str.indexOf("name=\"t2\"",p1+1)+13;
                p2=str.indexOf("\r\n",p1+1);
                String t2=str.substring(p1,p2);
                p1=str.indexOf("name=\"t3\"",p1+1)+13;
                p2=str.indexOf("\r\n",p1+1);
                String t3=str.substring(p1,p2);
                
                p1=str.indexOf("name=\"t4\"",p1+1)+13;
                p2=str.indexOf("\r\n",p1+1);
                String t4=str.substring(p1,p2);
                
                p1=str.indexOf("name=\"t5\"",p1+1)+13;
                p2=str.indexOf("\r\n",p1+1);
                String t5=str.substring(p1,p2);
                
                p1=str.indexOf("Content-Type",p1+1);
                p1=str.indexOf("\r\n\r\n",p1+1)+4;
                p2=str.indexOf(bndry,p1+1)-3;
                
                byte[] mydata=new byte[p2-p1+1];
                for(int i=0;i<mydata.length;i++)
                {
                    mydata[i]=data[p1+i];
                }
                Class.forName("org.gjt.mm.mysql.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/PharmacyShop1","root","");
                PreparedStatement stmt;
                ResultSet rs;
                stmt=con.prepareStatement("Select count(*)+1 from medicines");
                rs=stmt.executeQuery();
                String mid="";
                if(rs.next()) mid=rs.getString(1);
                stmt=con.prepareStatement("Insert into medicines values(?,?,?,?,?,?,?)");
                stmt.setString(1,mid);
                stmt.setString(2,t1);
                stmt.setString(3,t2);
                stmt.setString(4,t3);
                stmt.setString(5,t4);
                stmt.setString(6,t5);
                stmt.setBytes(7,mydata);
                stmt.executeUpdate();
            response.sendRedirect("products");
        }catch(Exception ee){}
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
