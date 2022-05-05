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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Purchase implements Serializable {

	private static final long serialVersionUID = 8749476082909710344L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "purchase_id")
	private Long id;
	
	@Column
	private Date date;

	@OneToMany(mappedBy="purchase", cascade=CascadeType.ALL)
	private List<Item> item;
	
	@ManyToOne
	@JoinColumn(name="purchases", referencedColumnName="customer_id")
	private Customer customer;
	
	public Purchase()
	{
		this(-1L, new Date(), new ArrayList<Item>(), new Customer());
	}

	public Purchase(Long id, Date date, List<Item> item, Customer customer) {
		super();
		this.id = id;
		this.date = date;
		this.item = item;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", date=" + date + ", item=" + item + ", customerLink=" + customer + "]";
	}
	
	

}
