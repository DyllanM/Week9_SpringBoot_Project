package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.EmptyLibraryException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Item;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.ItemRepository;

@RequestMapping("/api")
@RestController
public class InventoryController {
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	ItemRepository itemRepo;
	
	
	@GetMapping("/library")
	public List<Book> getAllBooks() throws EmptyLibraryException
	{
		List<Book> allBooks = bookRepo.findAll();
		
		if (allBooks.isEmpty())
			throw new EmptyLibraryException("There are no books in the library!!!");
		
		return allBooks;
	}
	@GetMapping("/inventory")
	public List<Item> getAllItems() throws EmptyLibraryException
	{
		List<Item> allBooks = itemRepo.findAll();
		
		if (allBooks.isEmpty())
			throw new EmptyLibraryException("There are no items in the inventory!!!");
		
		return allBooks;
	}
	
	@GetMapping("library/genre/{genre}")
	public ResponseEntity<Object> getBookByGenre(@PathVariable String genre)
	{
		List<Book> foundBooks = bookRepo.findByGenre(genre);
		
		if (foundBooks.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Books Found in the " + genre + " genre");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(foundBooks);
	}
	
	@GetMapping("library/title/{title}")
	public ResponseEntity<Object> getBookByTitle(@PathVariable String title)
	{
		Optional<Book> foundBooks = bookRepo.findByTitle(title);
		
		if (foundBooks.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(title + "was not found!");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(foundBooks.get());
	}
	
	@PostMapping("/addBook")
	public ResponseEntity<String> addBook(@RequestBody Book book)
	{
		Optional<Book> alreadyIn = bookRepo.findByTitle(book.getTitle());
		
		if (alreadyIn.isPresent())
		{
			Item newIt = new Item(alreadyIn.get());
			newIt.setPurchase(null);
			itemRepo.save(newIt);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Added additional " + book.getTitle() + " to inventory");
		}
		
		bookRepo.save(book);
		//itemRepo.save(new Item());
		return ResponseEntity.status(HttpStatus.CREATED).body("Added new " + book.getTitle() + " to inventory");
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateItem(@RequestBody Item item)
	{
		Optional<Item> foundItem = itemRepo.findById(item.getId());
		
		if (foundItem.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found!");
		}
		
		foundItem.get().setPrice(item.getPrice());
		itemRepo.save(foundItem.get());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with title " + foundItem.get().getBook().getTitle() + " found and updated");
	}
	
	@DeleteMapping("delete/item/{id}")
	public ResponseEntity<Object> deleteItemById(@PathVariable Long id)
	{
		Optional<Item> foundItem = itemRepo.findById(id);
		
		if (foundItem.isPresent())
		{
			itemRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Item with id " + id + 
					" and name " + foundItem.get().getBook().getTitle() + " was found and removed from inventory");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with id " + id + " was not found!");
	}
	@DeleteMapping("delete/book/id/{id}")
	public ResponseEntity<Object> deleteBookById(@PathVariable Long id)
	{
		Optional<Book> foundItem = bookRepo.findById(id);
		
		if (foundItem.isPresent())
		{
			bookRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Book with id " + id + 
					" and name " + foundItem.get().getTitle() + " was found and removed from inventory");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id " + id + " was not found!");
	}
	@DeleteMapping("delete/book/title/{title}")
	public ResponseEntity<Object> deleteBookByTitle(@PathVariable String title)
	{
		Optional<Book> foundItem = bookRepo.findByTitle(title);
		
		if (foundItem.isEmpty())
		{
			bookRepo.deleteById(foundItem.get().getId());
			return ResponseEntity.status(HttpStatus.OK).body(foundItem.get().getTitle() + " was found and removed from inventory");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with title \"" + title + "\" was not found!");
	}
	

}
