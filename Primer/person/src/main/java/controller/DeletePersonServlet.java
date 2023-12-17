package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import dao.ConnectionProperty;
import dao.PersonDbDAO;
import exception.DAOException;

@WebServlet( "/deleteperson")
public class DeletePersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	
	public DeletePersonServlet() throws FileNotFoundException, IOException {
		super();
		prop = new ConnectionProperty();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDbDAO dao = new PersonDbDAO();
		String strId = request.getParameter("id");
		Long deleteid = null;
		
		if(strId != null) {
			deleteid = Long.parseLong(strId);
		}
		try {
			dao.delete(deleteid);
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/employees/persons");
	}
}