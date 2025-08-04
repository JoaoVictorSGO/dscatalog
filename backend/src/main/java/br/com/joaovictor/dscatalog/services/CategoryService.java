package br.com.joaovictor.dscatalog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.joaovictor.dscatalog.entities.Category;
import br.com.joaovictor.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository repository;

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
	
}
