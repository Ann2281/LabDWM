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

@WebServlet("/editperson")
public class EditPersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionProperty prop;
	 
	public EditPersonServlet() throws FileNotFoundException, IOException {
		super();
		prop = new ConnectionProperty();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userPath;
		List<Role> roles;
		List<Person> persons = null;
		Person editperson = null;
		RoleDbDAO daoRole = new RoleDbDAO();
		PersonDbDAO dao = new PersonDbDAO();
		
		try {
			roles = daoRole.findAll();
			request.setAttribute("roles", roles);
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		
		try {
			persons = dao.findAll();
			roles = daoRole.findAll();
			for (Person person: persons) {
				person.setRole(daoRole.FindById(person.getIdRole(), roles));
			}
		}
		catch (DAOException e) {
			e.printStackTrace();
		}
		
		String strId = request.getParameter("id");
		Long id = null; // id редактируемого сотрудника
		if(strId != null) {
			id = Long.parseLong(strId);
		}
		try {
			editperson = dao.findById(id);
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("personEdit", editperson);
		request.setAttribute("persons", persons);
		userPath = request.getServletPath();
		if ("/editperson".equals(userPath)) {
			request.getRequestDispatcher("/views/editperson.jsp").forward(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDbDAO dao = new PersonDbDAO();
		RoleDbDAO daoRole = new RoleDbDAO();
		
		String strId = request.getParameter("id");
		Long id = null;
		if(strId != null) {
			id = Long.parseLong(strId);
		}
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		int index1 = role.indexOf('='); 
		int index2 = role.indexOf(","); 
		String r1 = role.substring(index1+1, index2);
		Long idRole = Long.parseLong(r1.trim());
		
		try {
			Role rl = daoRole.findById(idRole);
			Person editPerson = new Person(id,firstName, lastName, phone, email, idRole, rl);
			dao.update(editPerson);
		} 
		catch (DAOException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/employees/persons");
	}
	
}