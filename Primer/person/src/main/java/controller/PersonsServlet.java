package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import dao.ConnectionProperty;
import dao.PersonDbDAO;
import dao.RoleDbDAO;
import domain.Person;
import domain.Role;
import exception.DAOException;


/**
* Servlet implementation class PersonServlet
*/

@WebServlet("/persons")
public class PersonsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	 
	public PersonsServlet() throws FileNotFoundException, IOException {
		super();
		prop = new ConnectionProperty();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userPath;
		List<Person> persons;
		List<Role> roles;
		RoleDbDAO daoRole = new RoleDbDAO();
		PersonDbDAO dao = new PersonDbDAO();
		
		try {
			persons = dao.findAll();
			roles = daoRole.findAll();
			for (Person person: persons) {
				person.setRole(daoRole.FindById(person.getIdRole(), roles));
			}
			request.setAttribute("persons", persons);
			request.setAttribute("roles", roles);
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		
		userPath = request.getServletPath();
		if("/persons".equals(userPath)){
			request.getRequestDispatcher("/views/person.jsp").forward(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDbDAO dao = new PersonDbDAO();
		RoleDbDAO daoRole = new RoleDbDAO();
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		int index1 = role.indexOf('='); 
		int index2 = role.indexOf(","); 
		
		String r1 = role.substring(index1 + 1, index2);
		Long idRole = Long.parseLong(r1.trim());

		try {
			Role rl = daoRole.findById(idRole);
			Person newPerson = new Person(firstName, lastName, phone, email, idRole, rl);
			
			Long index = dao.insert(newPerson);
			System.out.println("Adding result: " + index );
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

	
	
}