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

/**
 *
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/register"})
public class register extends HttpServlet {

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
                if(request.getParameter("b1")!=null)
                {
                    stmt=con.prepareStatement("Insert into Members values(?,?,?,?,?,?,?,?,?)");
                    stmt.setString(1,request.getParameter("t1"));
                    stmt.setString(2,request.getParameter("t3"));
                    stmt.setString(3,request.getParameter("t4"));
                    stmt.setString(4,request.getParameter("t5"));
                    stmt.setString(5,request.getParameter("t6"));
                    stmt.setString(6,request.getParameter("t7"));
                    stmt.setString(7,request.getParameter("t8"));
                    stmt.setString(8,request.getParameter("t9"));
                    stmt.setString(9,request.getParameter("t10"));
                    stmt.executeUpdate();
                    stmt=con.prepareStatement("Insert into users values(?,?,?)");
                    stmt.setString(1,request.getParameter("t1"));
                    stmt.setString(2,request.getParameter("t2"));
                    stmt.setString(3,"member");
                    stmt.executeUpdate();
                    response.sendRedirect("index");
                }
                PrintWriter out=response.getWriter();
                response.setContentType("text/html");
                String links="<td><a href='register'>Register</a></td><td><a href='login'>Login</a></td>";
                //links="<td><a href='profile'>My Profile</a></td><td><a href='myorders'>My Orders</a></td><td><a href='logout'>Logout</a></td>";
                out.write("<html><body>");
                out.write("<img src='banner.jpg' width='100%'><hr>");
                out.write("<table border='0' width='100%'>");
                out.write("<tr><td><a href='index'>Medicines</a></td>"+links+"<td><a href='basket'>Basket</a></td><td><a href='contact'>Contact Us</a></td><td><a href='medicines'>About Medicines</a></td><td><a href='precautions'>Precautions</a></td></tr>");
                out.write("</table><hr>");
                out.write("<form method='post'>");
                out.write("<table align='center'>");
                out.write("<tr><td>Email:</td><td><input type='email' name='t1'></td></tr>");
                out.write("<tr><td>Password:</td><td><input type='password' name='t2'></td></tr>");
                out.write("<tr><td>Name:</td><td><input type='text' name='t3'></td></tr>");
                out.write("<tr><td>Mobile:</td><td><input type='text' name='t4'></td></tr>");
                out.write("<tr><td>Address:</td><td><textarea name='t5'></textarea></td></tr>");
                out.write("<tr><td>State:</td><td><select name='t6'>");
                stmt=con.prepareStatement("Select distinct states from indianstates");
                rs=stmt.executeQuery();
                while(rs.next())
                {
                    out.write("<option>"+rs.getString(1)+"</option>");
                }
                out.write("</select></td></tr>");
                out.write("<tr><td>City:</td><td><select name='t7'>");
                stmt=con.prepareStatement("Select distinct city from indianstates where city is not null order by city");
                rs=stmt.executeQuery();
                while(rs.next())
                {
                    out.write("<option>"+rs.getString(1)+"</option>");
                }
                out.write("</select></td></tr>");
                out.write("<tr><td>Pin Code:</td><td><input type='text' name='t8'></td></tr>");
                out.write("<tr><td>Secret Question:</td><td><select name='t9'>");
                out.write("<option>First School?</option>");
                out.write("<option>Prefered Vacation</option>");
                out.write("<option>Dream Job</option>");
                out.write("<option>First Pet Name?</option>");
                out.write("</select></td></tr>");
                out.write("<tr><td>Answer:</td><td><input type='text' name='t10'></td></tr>");
                out.write("<tr><td></td><td><input type='submit' value='Register' name='b1'></td></tr>");
                out.write("</table></form>");
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


