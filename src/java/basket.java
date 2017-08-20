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
@WebServlet(urlPatterns = {"/basket"})
public class basket extends HttpServlet {

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
                out.write("<table align=center>");
                out.write("<tr><th>S.No.</th><th>Medicine</th><th>Details</th><th>Price</th><th>Expiry Date</th><th></th></tr>");
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
                        out.write("<tr>");
                        out.write("<td>"+i+"</td>");
                        out.write("<td>"+s1+"</td>");
                        out.write("<td>"+s2+"</td>");
                        out.write("<td>"+s3+"</td>");
                        out.write("<td>"+s4+"</td>");
                        out.write("<td><img src='showImage?mid="+pids[i]+"' width='50'></td>");
                        out.write("</tr>");
                        total=total+s3;
                    }
                }
                out.write("<tr><th colspan='6'>Net Payable Amount is Rs "+total+"/-</th></tr>");
                out.write("</table>");
                if(hs!=null)
                {
                    out.write("<center><a href='finalorder'>Final Order</a></center>");
                }
                else
                {
                    out.write("<center>Please <a href='login'>login</a> to continue</center>");
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
