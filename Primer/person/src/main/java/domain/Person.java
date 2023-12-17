package domain;

/**
* Класс для данных сотрудника
*/
public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private Long idRole;
	private Role role;
 
	
 	public Person() {
 	}
 
 	public Person(String firstName, String lastName, String phone, String email, Role role) {
 		this.firstName = firstName;
 		this.lastName = lastName;
 		this.email = email;
 		this.phone = phone;
 		this.role = role;
 	}
 
 	public Person(String firstName, String lastName, String phone, String email, Long idRole, Role role) {
 		this.firstName = firstName;
 		this.lastName = lastName;
 		this.email = email;
 		this.phone = phone;
 		this.role = role;
 		this.idRole = idRole;
 	}
 
 	public Person(Long id, String firstName, String lastName, String phone, String email, Long idRole, Role role) {
 		this.id = id;
 		this.firstName = firstName;
 		this.lastName = lastName;
 		this.email = email;
 		this.phone = phone;
 		this.role = role;
 		this.idRole = idRole;
 	}
 
 
 	public String getFirstName() {
 		return firstName;
 	}
 	
 	public void setFirstName(String firstName) {
 		this.firstName = firstName;
 	}

 	public String getLastName() {
 		return lastName;
 	}
 	
 	public void setLastName(String lastName) {
 		this.lastName = lastName;
 	}
 	
 	public String getPhone() {
 		return phone;
 	}
 	
 	public void setPhone(String phone) {
 		this.phone = phone;
 	}
 	
 	public Role role () {
 		return role;
 	}
 	
 	public Long getId() {
 		return id;
 	}
 	
 	public void setId(Long id) {
 		this.id = id;
 	}
 	
 	public String getEmail() {
 		return email;
 	}
 	
 	public void setEmail(String email) {
 		this.email = email;
 	}
 	
 	public String getRole() {
 		return role.getNamerole();
 	}
 	
 	public void setRole(Role role) {
 		this.role = role;
 	}
 	
 	public Long getIdRole() {
 		return idRole;
 	}
 	
 	public void setIdRole(Long idRole) {
 		this.idRole = idRole;
 	}
 	
 	@Override
 	public String toString() {
 		return "Role {" + "Id = " + id + ", firstName = " + firstName + ", lastName = " + lastName + 
 				", phone = " + phone + ", email = " + email +", namerole = " + getRole() + "}";
 	}
 	
}





