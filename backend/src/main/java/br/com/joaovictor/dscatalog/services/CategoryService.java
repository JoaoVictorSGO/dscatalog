package br.com.joaovictor.dscatalog.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joaovictor.dscatalog.dtos.CategoryDTO;
import br.com.joaovictor.dscatalog.repositories.CategoryRepository;
import br.com.joaovictor.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	private CategoryRepository repository;

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		return repository.findAll()
				.stream()
				.map(x -> new CategoryDTO(x)).toList();
	}

	public CategoryDTO findById(Long id) {
		return new CategoryDTO(repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Emtity not found")));
	}
	
	
}
