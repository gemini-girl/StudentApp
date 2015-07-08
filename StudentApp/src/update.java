import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/update")
public class update extends HttpServlet {
	Statement s;
	ResultSet r;
	PreparedStatement p;
	
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				PrintWriter writer = response.getWriter();
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

				String sid = null;
				String sname = null;
				String major = null;
				String degree = null;
				String school = null;
				
				dbConn db = new dbConn();
				db.connect();
				s = db.c.createStatement();
				r = s.executeQuery("select studentID from student order by studentID");
				String list = (String) request.getSession(false).getAttribute("list");
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
				    	 p = db.c.prepareStatement("update student set sname = ?, degree = ?, major = ?, school = ? where studentID = '" + list +"'");
						   // p.setString(1, sid);
						    p.setString(1, sname);
						    p.setString(2, degree);
						    p.setString(3, major);
						    p.setString(4, school);
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
					writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");

							} 
				catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}	
		}
	}