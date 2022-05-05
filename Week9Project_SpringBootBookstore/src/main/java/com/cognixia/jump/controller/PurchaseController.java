package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.EmptyLibraryException;
import com.cognixia.jump.model.Item;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.repository.CustomerRepository;
import com.cognixia.jump.repository.ItemRepository;
import com.cognixia.jump.repository.PurchaseRepository;

@RequestMapping("/api")
@RestController
public class PurchaseController {
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	PurchaseRepository purRepo;
	
	
	
	@DeleteMapping("/purchase/{id}")
	public ResponseEntity<Object> PurchaseItemById(@PathVariable Long id)
	{
		Optional<Item> foundItem = itemRepo.findById(id);
		
		if (foundItem.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with id " + id + " was not found!");
		}
		
		Purchase newPurch = new Purchase();
		newPurch.setCustomer(null);
		ArrayList<Item> cart = new ArrayList<Item>();
		cart.add(foundItem.get());
		newPurch.setItem(cart);

		itemRepo.deleteById(id);
		
		purRepo.save(newPurch);

		return ResponseEntity.status(HttpStatus.OK).body("Item with id " + id + 
				" and name " + foundItem.get().getBook().getTitle() + " was found and removed from inventory");
	}
	

	@GetMapping("/purchases")
	public List<Purchase> ViewPurchases() throws EmptyLibraryException
	{
		List<Purchase> allBooks = purRepo.findAll();
		
		if (allBooks.isEmpty())
			throw new EmptyLibraryException("No purchases have been made!");
		
		return allBooks;
	}
	
	

}
