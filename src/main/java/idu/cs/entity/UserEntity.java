package idu.cs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import idu.cs.domain.User;

@Entity
@Table(name = "user_table")
public class UserEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	// database에서 sequence number, auto increment => primary key 역할
	@Column(nullable=false, length=20, unique=true)
	private String userid;
	private String userpw;
	private String name;
	private String company;
	
	public UserEntity(String userid, String userpw, String name, String company) {
		super();
		this.userid = userid;
		this.userpw = userpw;
		this.name = name;
		this.company = company;
	}
	
	public UserEntity() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	
	public User buildDomain() {
		User user = new User();
		user.setId(id); //primary key, auto increment, hibernate sequence
		user.setUserid(userid); //login id, unique
		user.setUserpw(userpw);
		user.setName(name);
		user.setCompany(company);
		return user;
	}
	
	public void buildEntity(User user) {
		id = user.getId();
		userid = user.getUserid();
		userpw = user.getUserpw();
		name = user.getName();
		company = user.getCompany();
	}
	
}