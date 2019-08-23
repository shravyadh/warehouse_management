package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Login {
	//	@GeneratedValue(strategy=GenerationType.AUTO)
	//	private int id;
	@Id
	@Column(length=20,nullable=false)
	private String username;
	
	@Column(length=20,nullable=false)
	@Size(min=4, max=10,message="Name must be between 2 and 32 characters long")
	private String password;
	@Column
	private String role;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Login()
	{

	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPassword()
	{
		return password;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return username;
	}
}
