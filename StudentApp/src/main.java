import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class main extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.print("<!DOCTYPE html><head><meta charset=\"utf-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\"></head>");
		writer.print("<body><header><center><hgroup><h2>ONLINE SCHOOL</h2></hgroup></center></header>");
		writer.print("<section>");
		writer.print("<center><h2>Welcome "+request.getSession(false).getAttribute("user")+"</h2></center>");
		writer.print("<article><center>");
		writer.print("<a href=\"/StudentApp/dbSearch\">Search a Student</a><br>");
	    writer.print("<a href=\"/StudentApp/dbInsert\">Add a New Student</a><br>");
		writer.print("<a href=\"/StudentApp/updateHTML\">Update a Student</a><br>");
		writer.print("<a href=\"/StudentApp/dbDelete\">Delete a Student</a><br>");
   		writer.print("<br>");		
		writer.print("</center></article></section><footer><center><small>Copyright © 2015</small></center></footer></body></html>");	
	}

}
