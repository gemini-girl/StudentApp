import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dbDelete")
public class dbDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	Connection c;
	Statement s;
	ResultSet r;
	PreparedStatement p;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter writer = response.getWriter();
			dbConn db = new dbConn();
			db.connect();
			s = db.c.createStatement();
			writer.print("<!DOCTYPE html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\"></head>");
			writer.print("<script>"
					+ "function validateForm() {"
					+"if(document.getElementById(\"list\").value == \"\"){"
					+ "document.getElementById('error').innerHTML=\"Select a ID\";"
					+ "document.getElementById(\"list\").focus();"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }}"
					+ "</script>");
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
			writer.print("<form method = \"get\" name = \"myform\" onSubmit=\"return validateForm()\">");
			writer.print("Select a ID from the list to Delete:  ");
			r = s.executeQuery("select studentID from student order by studentID");
			writer.print("<select name =\"list\" id=\"list\">");
			writer.print("<option value=\"\">Select a Student ID</option>");	
			while(r.next()){
				writer.print("<option value="+ Integer.parseInt(r.getString("studentID"))+">"
						+ Integer.parseInt(r.getString("studentID"))+"</option>");	
			}
			writer.print("</select><br><p><input type=\"submit\" value=\"Submit\"/></p>");
			writer.print("<p><b><div style=\"color:red\" id =\"error\"> </div></b></p>");
			writer.print("</form>");
			String list = request.getParameter("list");
			System.out.println(request.getParameter("list"));

			if(list != null && !list.isEmpty())
		    {	
				r = s.executeQuery("delete from student where studentID = '" +list+ "'");
				r = s.executeQuery("select * from student order by studentID");
				ResultSetMetaData md = r.getMetaData();
				int count = md.getColumnCount();
				writer.println("<table border=1 CELLPADDING=0 CELLSPACING=0 WIDTH=90%>");
				writer.print("<tr>");
				for (int i=1; i<=count; i++) {
				    	writer.print("<th>");
				    	writer.print(md.getColumnLabel(i));
				    }
				writer.println("</tr>");
				
				while(r.next()){
				writer.println("<tr><td><center>"+r.getString("studentID")+"</center></td>"
			               + "<td><center>"+r.getString("sname")+"</center></td>"
			               + "<td><center>"+r.getString("degree")+"</center></td>"
			               + "<td><center>"+r.getString("major")+"</center></td>"
				           + "<td><center>"+r.getString("school")+"</center></td></tr>");
				}
				writer.println("</table>");
		    }
			writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");
			db.c.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
