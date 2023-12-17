package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import domain.Person;
import exception.DAOException;

/**
* Класс реализации функций взаимодействия с базой данных для таблицы
persons
* (Сотрудники)
*/

public class PersonDbDAO implements RepositoryDAO<Person> {
	
	// SQL-запросы к таблице persons базы данных
	private static final String select_all_person = "SELECT id, firstName, lastname, "+ "email, phone, roleid FROM persons ORDER BY lastname ASC";
	private static final String select_person_ById = "SELECT id, roleid, firstname, lastname, phone, email "+ "FROM persons WHERE id = ?";
	private static final String insert_person = "INSERT INTO persons(roleid, firstname, lastname, phone, email)"+ "VALUES(?,?,?,?,?)";
	private static final String edit_person = "UPDATE persons SET roleid = ?, "+ "firstname = ?, lastname = ?, phone = ?, email = ? WHERE id = ?";
	private static final String delete_person = "DELETE FROM persons WHERE id = ?";
	
	// Создание соединения с базой данных
	private ConnectionBuilder builder = new DbConnectionBuilder();
	
	private Connection getConnection() throws SQLException {
		return builder.getConnection();
	}
	
	RoleDbDAO dao = new RoleDbDAO();
	public PersonDbDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Long insert (Person person) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(insert_person, new String[] { "id" })) {
			Long Id = -1L;
			pst.setLong(1, person.getIdRole());
			pst.setString(2, person.getFirstName());
			pst.setString(3, person.getLastName());
			pst.setString(4, person.getPhone());
			pst.setString(5, person.getEmail());
			pst.executeUpdate();
			ResultSet gk = pst.getGeneratedKeys();
			if (gk.next()) {
				Id = gk.getLong("id");
			}
			gk.close();
			return Id;
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void update(Person person) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(edit_person)) {
			
			pst.setLong(1, person.getIdRole());
			pst.setString(2, person.getFirstName());
			pst.setString(3, person.getLastName());
			pst.setString(4, person.getPhone());
			pst.setString(5, person.getEmail());
			pst.setLong(6, person.getId());
			pst.executeUpdate();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void delete(Long Id) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(delete_person)) {
			pst.setLong(1, Id);
			pst.executeUpdate();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public Person findById(Long Id) throws DAOException {
		Person person = null;
		try (Connection con = getConnection()) {
			PreparedStatement pst = con.prepareStatement(select_person_ById);
			pst.setLong(1, Id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				person = fillPerson(rs);
			}
			rs.close();
			pst.close();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
		return person;
	}
	
	@Override
	public List<Person> findAll() throws DAOException {
		List<Person> list = new LinkedList<>();
		
		try (Connection con = getConnection();PreparedStatement pst = con.prepareStatement(select_all_person); ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				list.add(fillPerson(rs));
			}
			rs.close();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
		
		return list;
	}
	
	private Person fillPerson(ResultSet rs) throws SQLException, DAOException {
		Long idRole = rs.getLong("roleid");
		Person person = new Person();
		person.setId(rs.getLong("id"));
		person.setFirstName(rs.getString("firstname"));
		person.setLastName(rs.getString("lastname"));
		person.setPhone(rs.getString("phone"));
		person.setEmail(rs.getString("email"));
		person.setIdRole(idRole);
		return person;
	}
}