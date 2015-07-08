import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dbSearch")
public class dbSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Statement s;
	ResultSet r;
	ResultSetMetaData md;
	int count;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = response.getWriter();
			dbConn db = new dbConn();
			db.connect();
			
			s = db.c.createStatement();
			db.c.commit();
			
			r = s.executeQuery("select * from student order by studentID");
			md = r.getMetaData();
			count = md.getColumnCount();
			
			writer.print("<!DOCTYPE html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\"></head>");
			writer.print("<script>" 
					+ "function validation() {"
					+ "var x = document.forms[\"myform\"][\"search\"].value;"
				    + " if (x == null || x == \"\")  {"
				    + "document.getElementById('error').innerHTML=\"Search keyword is incorrect or empty\";"
					+ "return false;"
					+ " }else {"
					+ "document.getElementById('error').innerHTML=\"\"; }}"
					+ "</script>");
			writer.print("<body><header><center><hgroup><h2>ONLINE SCHOOL</h2></hgroup></center></header>");
			writer.print("<section>");
			writer.print("<center><h3>Welcome "+request.getSession(false).getAttribute("user")+"</h3></center>");
			writer.print("<article><center>");
			writer.print("<ul id=\"nav\">"
					+ "<li><a href=\"/StudentApp/dbSearch\">Search a Student</a></li>"
				    + "<li><a href=\"/StudentApp/dbInsert\">Add a New Student</a></lil>"
				    + " <li><a href=\"/StudentApp/updateHTML\">Update a Student</a></li>"
				    + "<li><a href=\"/StudentApp/dbDelete\">Delete a Student</a></li> </p></ul><br><br>");
			writer.print("<br>");
			writer.print("<form name = \"myform\" method = \"get\" onsubmit=\"return validation();\">");
			writer.print("<select name =\"list\" id=\"list\">");
			writer.print("<option value=\"\">Select a Criteria</option>");	
			for (int i=1; i<=count; i++) {
				 writer.print("<p><option value="+ md.getColumnLabel(i)+">"
				 + md.getColumnLabel(i)+"</option>");	
			}
			writer.print("<input style=\"margin-left:20px;\" name = \"search\" type = \"text\"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			writer.print("<input type=\"submit\" value=\"Search\"/></p>");
			writer.print("<p><b><div style=\"color:red\" id =\"error\"> </div></b></p>");
			writer.print("</form><br>");
			String search = request.getParameter("search"); 
			String list = request.getParameter("list");
			if((search != null && !search.isEmpty()) && (list != null && !list.isEmpty())){
				r = s.executeQuery("select * from student where  "+ list +" = '"+search+"' order by studentID");
				//if (!r.next()) {
					//writer.print("No result for this search Criteria");
				//}else {
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

			}else if(search != null && !search.isEmpty())  {
				if((search.equalsIgnoreCase("all")) || (search.equalsIgnoreCase("everything"))){
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
			//}else {
				//	writer.print("No result for this search Criteria");
				}}
		db.c.close();
		writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}	
	}
}