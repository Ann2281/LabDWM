package dao;
import java.util.List;
import exception.DAOException;

public interface RepositoryDAO<T> {
	// Добавление сущности в таблицу базы данных
	// возвращает ID добавленного должности
	public Long insert(T о) throws DAOException;
	
	// Редактирование cущности
	public void update(T o) throws DAOException;
	
	// Удаление сущности
	public void delete(Long Id) throws DAOException;
	
	// Поиск сущности по Id
	public T findById(Long Id) throws DAOException;
	
	// Получение списка сущностей
	public List<T> findAll() throws DAOException;
}