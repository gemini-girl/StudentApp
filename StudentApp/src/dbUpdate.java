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

@WebServlet("/dbUpdate")

public class dbUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Statement s;
	ResultSet r;
	PreparedStatement p;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = response.getWriter();
			String sid = null;
			String sname = null;
			String major = null;
			String degree = null;
			String school = null;
			
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
				    + " <li><a href=\"/StudentApp/dbUpdate\">Update a Student</a></li>"
				    + "<li><a href=\"/StudentApp/dbDelete\">Delete a Student</a></li> </p></ul><br><br>");
			writer.print("<br><br>");
			writer.print("<form method = \"get\" name = \"myform\" onSubmit=\"return validateForm()\">");
			writer.print("Select a ID from the list to update:  ");
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
				r = s.executeQuery("select * from student where studentID = '" +list+ "'");
				writer.print("<form>");
			    while(r.next()){
				//writer.print("<p><label>Student ID : </label><input name = \"sid\" type = \"text\" value = \" Hello\"required></input></p>");
				//writer.print("<p><label>Student ID : </label><input name = \"sid\" type = \"text\" value = \" "+list+ "\"required></input></p>");
				writer.print("<p><label>Full Name : </label><input name = \"sname\" type = \"text\" value = \" "+r.getString("sname")+"\"required></input></p>");
				writer.print("<p><label>Degree : </label><input name = \"degree\" type = \"text\" value = \" "+r.getString("degree")+"\"required></input></p>");
				writer.print("<p><label>Major : </label><input name = \"major\" type = \"text\" value = \" "+r.getString("major")+"\"required></input></p>");
				writer.print("<p><label>School : </label><input name = \"school\" type = \"text\" value = \" "+r.getString("school")+"\"required></input></p>");
				}
				writer.print("<p><input type=\"submit\" value=\"Update\"/></p>");
				writer.print("</form>");
				
				//sid = request.getParameter("sid");
				sid = list;			
				sname = request.getParameter("sname");
				major = request.getParameter("major");
				degree = request.getParameter("degree");
				school = request.getParameter("school"); 
			    System.out.println(sid +" "+ sname +" "+ degree +" "+ major +" "+ school);
				
				 if((sid != null && !sid.isEmpty()) && (sname != null && !sname.isEmpty()) && 
				    		(major != null && !major.isEmpty()) && (degree != null && !degree.isEmpty()) && 
				    		(school != null && !school.isEmpty()))
				    {
				    	 p = db.c.prepareStatement("update student set studentId = ?, sname = ?, degree = ?, major = ?, schoo; = ? where studentID = '" + list +"'");
						    p.setString(1, sid);
						    p.setString(2, sname);
						    p.setString(3, degree);
						    p.setString(4, major);
						    p.setString(5, school);
						    p.executeUpdate();
						    db.c.commit();
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
				sid = null;
				sname = null;
				major = null;
				degree = null;
				school = null;
	
		    }
			writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");
			db.c.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
