import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet("/login")
public class login extends HttpServlet {
	RequestDispatcher dispatch;
	String user, pwd, label;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		user = request.getParameter("user");
		pwd = request.getParameter("pwd");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		System.out.println(session.getAttribute("user"));
		out.print(session.getAttribute("user"));
		if((user.compareTo("admin") == 0)&&(pwd.compareTo("admin")==0)){
			dispatch = request.getRequestDispatcher("/main");
			dispatch.forward(request, response);
		}else{
			dispatch = request.getRequestDispatcher("index.html");
			dispatch.include(request, response);
		}		
	}
}
