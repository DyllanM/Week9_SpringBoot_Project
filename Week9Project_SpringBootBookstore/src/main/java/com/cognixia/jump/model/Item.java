package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = -1558625709184297004L;
	
//	public enum Condition {
//		NEW_CONDITION, USED_CONDITION
//	}
	
	static public final float DEFAULT_PRICE = 15.0f;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private Long id;
	
	//@Column
	private float price;

	//@Enumerated(EnumType.STRING)
//	private Condition condition;

	@ManyToOne
	@JoinColumn(name="item", referencedColumnName="purchase_id")
	private Purchase purchase;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	public Item()
	{
		this(-1L, 0.0f, /*Condition.NEW_CONDITION,*/new Purchase(), new Book());
	}
	
	public Item(Book book)
	{
		this(-1L, DEFAULT_PRICE, new Purchase(), book);
	}

	public Item(Long id, float price, /*Condition condition, */Purchase purchase, Book book) {
		super();
		this.id = id;
		this.price = price;
		//this.condition = condition;
		this.purchase = purchase;
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

//	public Condition getCondition() {
//		return condition;
//	}
//
//	public void setCondition(Condition condition) {
//		this.condition = condition;
//	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", price=" + price + ", purchase=" + purchase + ", book=" + book + "]";
	}
	
	
}
