package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = -7977888459155526149L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Date joinDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	private User user;

	@OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
	private List<Purchase> purchases;
	
	public Customer()
	{
		this(-1L, "N/A", new Date(), new User(), new ArrayList<Purchase>());
	}

	public Customer(Long id, String name, Date joinDate, User user, List<Purchase> purchases) {
		super();
		this.id = id;
		this.name = name;
		this.joinDate = joinDate;
		this.user = user;
		this.purchases = purchases;
	}

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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//////////////////////////////////////////////////////////ASADSFGHJKMNHFDSAWERTYUJIKJHNDXSAERTYUIKJHBFDSERTYUKJHBGFDSERTGFH
	public List<Purchase> getPurchases() {
		return purchases;
	}

	//////////////////////////////////////////////////////////ASADSFGHJKMNHFDSAWERTYUJIKJHNDXSAERTYUIKJHBFDSERTYUKJHBGFDSERTGFH
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", joinDate=" + joinDate + ", user=" + user + ", purchases="
				+ purchases + "]";
	}
	
	

}
