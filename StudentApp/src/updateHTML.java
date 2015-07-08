import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateHTML")
public class updateHTML extends HttpServlet {
	Statement s;
	ResultSet r;
	PreparedStatement p;
	
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				PrintWriter writer = response.getWriter();
				
				dbConn db = new dbConn();
				db.connect();
				s = db.c.createStatement();
				r = s.executeQuery("select studentID from student order by studentID");

				writer.print("<!DOCTYPE html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\"></head>");
				writer.print("<body><header><center><hgroup><h2>ONLINE SCHOOL</h2></hgroup></center></header>");
				writer.print("<section>");
				writer.print("<center><h3>Welcome "+request.getSession(false).getAttribute("user")+"<h3></center>");
				writer.print("<article><center>");
				writer.print("<ul id=\"nav\"><p >"
										+ "<li><a href=\"/StudentApp/dbSearch\">Search a Student</a></li>"
					    + "<li><a href=\"/StudentApp/dbInsert\">Add a New Student</a></lil>"
					    + " <li><a href=\"/StudentApp/updateHTML\">Update a Student</a></li>"
					    + "<li><a href=\"/StudentApp/dbDelete\">Delete a Student</a></li> </p></ul><br><br>");
				writer.print("<br><br>");
				writer.print("<form method = \"get\">");
				writer.print("Select a ID from the list to update:  ");
				writer.print("<select name =\"list\">");
				writer.print("<option value=\"Select a Student ID\">Select a Student ID</option>");	
				while(r.next()){
					writer.print("<option value="+ Integer.parseInt(r.getString("studentID"))+">"
							+ Integer.parseInt(r.getString("studentID"))+"</option>");	
				}
				writer.print("</select><br><p><input type=\"submit\" value=\"Submit\"/></p>");
				writer.print("</form>");
				String list = request.getParameter("list");
				System.out.println(request.getParameter("list"));
				HttpSession sess = request.getSession();
				sess.setAttribute("list", list);
				
				if(list != null && !list.isEmpty())
			    {	
					r = s.executeQuery("select * from student where studentID = '" +list+ "'");
					writer.print("<form method = \"get\" action = \"update\">");
				    while(r.next()){
					writer.print("<p><label>Full Name : </label><input name = \"sname\" type = \"text\" value = \""+r.getString("sname")+"\"required></input></p>");
					writer.print("<p><label>Degree : </label><input name = \"degree\" type = \"text\" value = \""+r.getString("degree")+"\"required></input></p>");
					writer.print("<p><label>Major : </label><input name = \"major\" type = \"text\" value = \""+r.getString("major")+"\"required></input></p>");
					writer.print("<p><label>School : </label><input name = \"school\" type = \"text\" value = \""+r.getString("school")+"\"required></input></p>");
					}
					writer.print("<p><input type=\"submit\" value=\"Update\"/></p>");
					writer.print("</form>");
				
			    }
				RequestDispatcher rd;
			//	rd = request.getRequestDispatcher("/update");
			//	rd.include(request, response);				
			writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");
			} 
				catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}

	}
