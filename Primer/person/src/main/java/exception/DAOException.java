package exception;

@SuppressWarnings("serial")
public class DAOException extends Exception{

	public DAOException(Exception e) {
		System.out.println(e.getMessage());
	}

}
