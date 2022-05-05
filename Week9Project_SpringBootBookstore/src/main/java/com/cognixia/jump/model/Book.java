package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Book implements Serializable {

	private static final long serialVersionUID = -3861382918054972345L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String genre;
	
	@Column
	private int length;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "book", targetEntity = Item.class, cascade = CascadeType.ALL)
	private List<Item> itemInstance;
	
	public Book()
	{
		this(-1L, "N/A", "N/A", 0, new ArrayList<Item>());
	}

	public Book(Long id, String title, String genre, int length, List<Item> item) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.length = length;
		this.itemInstance = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<Item> getItem() {
		return itemInstance;
	}

	public void setItem(List<Item> item) {
		this.itemInstance = item;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", genre=" + genre + ", length=" + length + "]";
	}
	
	

}
