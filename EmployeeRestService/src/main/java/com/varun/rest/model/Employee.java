package com.varun.rest.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.context.annotation.ComponentScan;

@Entity
@ComponentScan
public class Employee {

	@Id
	private String id;

	private String name;
	
	private String bu;
	
	private String email;

	private String phone;
	
	
	public Employee(String id, String name, String bu, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.bu = bu;
		this.email = email;
		this.phone = phone;
	}
	
	
	public Employee() {
		super();
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bu == null) ? 0 : bu.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (bu == null) {
			if (other.bu != null)
				return false;
		} else if (!bu.equals(other.bu))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EmpBean [id=" + id + ", name=" + name + ", bu=" + bu + ", email=" + email + ", phone=" + phone + "]";
	}

	
	
}
