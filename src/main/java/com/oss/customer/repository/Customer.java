// tag::sample[]
package com.oss.customer.repository;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Customer {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	private String name;
	private String email;
	private String phone;
	private Date onboardDate;

	protected Customer() {
	}


	public Customer(String name, String email, Date onboardDate) {
		super();
		this.name = name;
		this.email = email;
		this.onboardDate = onboardDate;
	}


	public Date getOnboardDate() {
		return onboardDate;
	}



	public void setOnboardDate(Date onboardDate) {
		this.onboardDate = onboardDate;
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

}
