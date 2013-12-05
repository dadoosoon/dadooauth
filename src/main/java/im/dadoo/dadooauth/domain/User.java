package im.dadoo.dadooauth.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User implements Serializable {

	/** 
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
	 * @since JDK 1.6 
	 */
	private static final long serialVersionUID = -4548165990598087189L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 30)
	private String name;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 30)
	private String password;
	
	@Column(name = "signup_datetime", nullable = false)
	private Long signupDatetime;
	
	@Column(nullable = false)
	private Integer state;
	
	public User() {}
	
	public static User create(String name, String email, 
			String password, Long signupDatetime, Integer state) {
		User user = new User();
		user.name = name;
		user.email = email;
		user.password = password;
		user.signupDatetime = signupDatetime;
		user.state = state;
		return user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getSignupDatetime() {
		return signupDatetime;
	}

	public void setSignupDatetime(Long signupDatetime) {
		this.signupDatetime = signupDatetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
