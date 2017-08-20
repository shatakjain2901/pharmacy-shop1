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
@WebServlet(urlPatterns = {"/myorders"})
public class myorders extends HttpServlet {

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
                stmt=con.prepareStatement("Select * from orders where email=?");
                stmt.setString(1,hs.getAttribute("EMAIL")+"");
                rs=stmt.executeQuery();
                out.write("<hr><table align=center>");
                out.write("<tr><th>Order Id</th><th>Order Date</th><th>Total Amount</th><th></th></tr>");
                while(rs.next())
                {
                    out.write("<tr>");
                    out.write("<td>"+rs.getString(1)+"</td>");
                    out.write("<td>"+rs.getString(2)+"</td>");
                    out.write("<td>"+rs.getString(4)+"</td>");
                    out.write("<td><a href='myorders?oid="+rs.getString(1)+"'>Order Details</a></td>");
                    out.write("</tr>");
                }
                out.write("</table>");
                if(request.getParameter("oid")!=null)
                {
                    stmt=con.prepareStatement("Select * from orderItems where oderid=?");
                    stmt.setString(1,request.getParameter("oid"));
                    rs=stmt.executeQuery();
                    out.write("<hr><table align=center>");
                    out.write("<tr><th>Medicine Id</th><th>Medicine Name</th><th>Price</th><th>Qty</th></tr>");
                    while(rs.next())
                    {
                        out.write("<tr>");
                        out.write("<td>"+rs.getString(2)+"</td>");
                        out.write("<td>"+rs.getString(3)+"</td>");
                        out.write("<td>"+rs.getString(4)+"</td>");
                        out.write("<td>"+rs.getString(5)+"</td>");
                        out.write("</tr>");
                    }
                    out.write("</table>");
                }         
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
