package br.com.joaovictor.dscatalog.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaovictor.dscatalog.entities.Category;
import br.com.joaovictor.dscatalog.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	private CategoryService service;

	public CategoryResource(CategoryService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		
		
		return ResponseEntity.ok().body(service.findAll());
		
	}
}
