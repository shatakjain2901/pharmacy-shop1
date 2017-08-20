/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/finalorder"})
public class finalorder extends HttpServlet {

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
                Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/PharmacyShop1","root","");
                PreparedStatement stmt;
                ResultSet rs;
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                String links="<td><a href='register'>Register</a></td><td><a href='login'>Login</a></td>";
                HttpSession hs=request.getSession(false);
                if(hs!=null)
                {
                    links="<td><a href='profile'>My Profile</a></td><td><a href='myorders'>My Orders</a></td><td><a href='logout'>Logout</a></td>";
                }
                out.write("<html><body>");
                out.write("<img src='banner.jpg' width='100%'><hr>");
                out.write("<table border='0' width='100%'>");
                out.write("<tr><td><a href='index'>Medicines</a></td>"+links+"<td><a href='basket'>Basket</a></td><td><a href='contact'>Contact Us</a></td><td><a href='medicines'>About Medicines</a></td><td><a href='precautions'>Precautions</a></td></tr>");
                out.write("</table><hr>");
                stmt=con.prepareStatement("Select count(*)+1 from Orders");
                rs=stmt.executeQuery();
                String oid="";
                if(rs.next())
                {
                    oid=rs.getString(1);
                }
                java.util.Date d=new java.util.Date();
                String dt=(d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate();
                
                Cookie[] ck=request.getCookies();
                String val="";
                if(ck!=null)
                {
                    for(int i=0;i<ck.length;i++)
                    {
                        String nm=ck[i].getName();
                        if(nm.equals("BASKET"))
                        {
                            val=ck[i].getValue();
                        }
                    }
                }
                String[] pids=val.split("#");
                int total=0;
                for(int i=1;i<pids.length;i++)
                {
                    stmt=con.prepareStatement("Select * from Medicines where mid=?");
                    stmt.setString(1,pids[i]);
                    rs=stmt.executeQuery();
                    if(rs.next())
                    {
                        String s1=rs.getString(2);
                        String s2=rs.getString(3);
                        int s3=rs.getInt(4);
                        String s4=rs.getString(6);
                        String s5=rs.getString(7);
                        total=total+s3;
                        stmt=con.prepareStatement("Insert into orderitems values(?,?,?,?,?)");
                        stmt.setString(1,oid);
                        stmt.setString(2,pids[i]);
                        stmt.setString(3,s1);
                        stmt.setInt(4,s3);
                        stmt.setString(5,"1");
                        stmt.executeUpdate();
                    }
                }
                stmt=con.prepareStatement("Insert into orders values(?,?,?,?)");
                stmt.setString(1,oid);
                stmt.setString(2,dt);
                stmt.setString(3,hs.getAttribute("EMAIL")+"");
                stmt.setInt(4,total);
                stmt.executeUpdate();
                Cookie c=new Cookie("BASKET","");
                c.setMaxAge(-1);
                response.addCookie(c);
                out.write("<center>Thanks for Your Order</center>");
                out.write("<center>Order id is "+oid+"</center>");
                out.write("<center>This is Cash On Delivery Order</center>");
                out.write("<center>We will inform the status of your order via Email/SMS</center>");
                out.write("<center>Happy Shopping</center>");
                out.write("<center><a href='index'>Continue</a></center>");
                out.write("<hr><img src='footer.jpg' width='100%'>");
                out.write("</body></html>");
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
