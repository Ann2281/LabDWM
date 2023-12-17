package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import domain.Role;
import exception.DAOException;

/**
* Класс реализации функций взаимодействия с базой данных для таблицы
roles
* (Должности)
*/
public class RoleDbDAO implements RepositoryDAO<Role> {
	
	public RoleDbDAO() {
		// TODO Auto-generated constructor stub
	}
	
	// SQL-запросы к таблице roles базы данных 
	private static final String select_all_role = "SELECT id, rolename FROM roles ORDER BY rolename ASC";
	private static final String select_role_ById = "SELECT id, rolename FROM roles WHERE id = ?";
	private static final String insert_role = "INSERT INTO roles(rolename) VALUES(?)";
	private static final String edit_role = "UPDATE roles SET rolename = ? WHERE id = ?";
	private static final String delete_role = "DELETE FROM roles WHERE id = ?";
	
	// Создание соединения с базой данных
	private ConnectionBuilder builder = new DbConnectionBuilder();
	
	private Connection getConnection() throws SQLException {
		return builder.getConnection();
	}
	
	// Добавление новой должности
	@Override
	public Long insert (Role role) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(insert_role, new String[] { "id" })) {
			Long Id = -1L;
			pst.setString(1, role.getNamerole());
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
	
	// Редактирование должности
	@Override
	public void update(Role role) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst= con.prepareStatement(edit_role)) {
			pst.setString(1, role.getNamerole());
			pst.setLong(2, role.getId());
			pst.executeUpdate();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	// Удаление должности
	@Override
	public void delete(Long Id) throws DAOException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(delete_role)) {
			pst.setLong(1, Id);
			pst.executeUpdate();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	// Поиск должности по Id
	@Override
	public Role findById(Long Id) throws DAOException {
		Role role = null;
		
		try (Connection con = getConnection()) {
			PreparedStatement pst = con.prepareStatement(select_role_ById);
			pst.setLong(1, Id);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				role = fillRole(rs);
			}
			
			rs.close();
			pst.close();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
		return role;
	}
	
	// Формирование списка всех должностей
	@Override
	public List<Role> findAll() throws DAOException {
		List<Role> list = new LinkedList<>();
		
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(select_all_role); ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				list.add(fillRole(rs));
			}
			rs.close();
		} 
		catch (Exception e) {
			throw new DAOException(e);
		}
		return list;
	}
	
	// Формирование класса Должности по результатам запроса к БД
	private Role fillRole(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getLong("id"));
		role.setNamerole(rs.getString("rolename"));
		return role;
	}
	
	// Поиск должности по id из списка должностей
	public Role FindById(Long id, List<Role> roles) {
		if (roles != null) {
			for (Role r : roles) {
				if ((r.getId()).equals(id)) {
					return r;
				}
			}
		} 
		else {
			return null;
		}
		return null;
	}
}