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

@WebServlet("/dbInsert")
public class dbInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Statement s;
	ResultSet r;
	PreparedStatement p;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = response.getWriter();
			dbConn db = new dbConn();
			db.connect();
			
			s = db.c.createStatement();
			writer.print("<!DOCTYPE html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\"></head>");
			writer.print("<script>"
					+ "function validation() {"
					+ "var a = document.forms[\"myform\"][\"sid\"].value;"
					+ "var b = document.forms[\"myform\"][\"sname\"].value;"
					+ "var c = document.forms[\"myform\"][\"degree\"].value;"
					+ "var d = document.forms[\"myform\"][\"major\"].value;"
					+ "var e = document.forms[\"myform\"][\"school\"].value;"

				    + " if (a == null || a == \"\"){"
				    + "document.getElementById('error').innerHTML=\" Student ID is required\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }"
									    
					+ " if (b == null || b == \"\"){"
				    + "document.getElementById('error').innerHTML=\" Full Name is required\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }"
					
				    + " if (c == null || c == \"\"){"
				    + "document.getElementById('error').innerHTML=\"Degree is required\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }"
					
				    + " if (d == null || d == \"\"){"
				    + "document.getElementById('error').innerHTML=\" Major is required\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }"
					
					+ " if (e == null || e == \"\") {"
				    + "document.getElementById('error').innerHTML=\" School is required\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }"
					+ "}</script>");			
			writer.print("<body><header><center><hgroup><h2>ONLINE SCHOOL</h2></hgroup></center></header>");
			writer.print("<section>");
			writer.print("<center><h3>Welcome "+request.getSession(false).getAttribute("user")+"<h3></center>");
			writer.print("<article><center>");
			writer.print("<ul id=\"nav\"><p >"
								+ "<li><a href=\"/StudentApp/dbSearch\">Search a Student</a></li>"
				    + "<li><a href=\"/StudentApp/dbInsert\">Add a New Student</a></lil>"
				    + " <li><a href=\"/StudentApp/updateHTML\">Update a Student</a></li>"
				    + "<li><a href=\"/StudentApp/dbDelete\">Delete a Student</a></li> </p></ul><br><br>");
			writer.print("<br>");
			writer.print("Fill the below details to enter a new record:");
			writer.print("<br>");
			writer.print("<br>");
			writer.print("<form name = \"myform\" method = \"get\" onsubmit=\"return validation();\">");
			writer.print("<p><label>Student ID : </label><input name = \"sid\" type = \"text\" ></input></p>");
			writer.print("<p><label>Full Name : </label><input name = \"sname\" type = \"text\" ></input></p>");
			writer.print("<p><label>Degree : </label><input name = \"degree\" type = \"text\" ></input></p>");
			writer.print("<p><label>Major : </label><input name = \"major\" type = \"text\" ></input></p>");
			writer.print("<p><label>School : </label><input name = \"school\" type = \"text\" ></input></p>");
			writer.print("<p><input type=\"submit\" value=\"Submit\"/></p>");
			writer.print("<p><b><div style=\"color:red\" id =\"error\"> </div></b></p>");
			writer.print("</form>");
			String sid = null;
			String sname = null;
			String major = null;
			String degree = null;
			String school = null;
			 sid = request.getParameter("sid");
			 sname = request.getParameter("sname");
			 major = request.getParameter("major");
			 degree = request.getParameter("degree");
			 school = request.getParameter("school");
			//System.out.println(sid +" "+ sname +" "+ degree +" "+ major +" "+ school);
		    if((sid != null && !sid.isEmpty()) && (sname != null && !sname.isEmpty()) && 
		    		(major != null && !major.isEmpty()) && (degree != null && !degree.isEmpty()) && 
		    		(school != null && !school.isEmpty()))
		    {
		    	 p = db.c.prepareStatement("insert into student values (?,?,?,?,?)");
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
		
		writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");
		db.c.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
