package br.com.joaovictor.dscatalog.services;

import java.time.Instant;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.joaovictor.dscatalog.dtos.CategoryDTO;
import br.com.joaovictor.dscatalog.dtos.ProductDTO;
import br.com.joaovictor.dscatalog.entities.Category;
import br.com.joaovictor.dscatalog.entities.Product;
import br.com.joaovictor.dscatalog.repositories.CategoryRepository;
import br.com.joaovictor.dscatalog.repositories.ProductRepository;
import br.com.joaovictor.dscatalog.services.exceptions.DatabaseException;
import br.com.joaovictor.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;



@Service
public class ProductService {

	private ProductRepository repository;
	
	private CategoryRepository categoryRepository;
	
	



	public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
		super();
		this.repository = repository;
		this.categoryRepository = categoryRepository;
	}



	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return new ProductDTO(
				repository.
				findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Product not found")));
	}



	public Page<ProductDTO> findAll(PageRequest pageRequest) {
		return repository.searchAll(pageRequest);
	}


	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try {
			Product product = repository.getReferenceById(id);
			copyDtoToEntity(dto, product);
			return new ProductDTO(repository.save(product));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
				
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) throw new ResourceNotFoundException("Produto não encontrado!");
		
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Referential integration failure");
		}
	}
	
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDate(Instant.now());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
		List<Long> categoryIds = dto.getCategories().stream()
				.map(CategoryDTO::getId)
				.toList();
		
		List<Category> categories = categoryRepository.findAllById(categoryIds);
		
		if(categories.size() != categoryIds.size()) {
			throw new ResourceNotFoundException("Alguma categoria não foi encontrada!");
		}
		entity.getCategories().clear();
		categories.forEach(entity::addCategory);
	}
	
//	private void copyDtoToEntity(ProductDTO dto, Product entity) {
//		entity.setName(dto.getName());
//		entity.setDate(Instant.now());
//		entity.setDescription(dto.getDescription());
//		entity.setPrice(dto.getPrice());
//		entity.setImgUrl(dto.getImgUrl());
//		
//		dto.getCategories().forEach(x ->{
//			Category category = categoryRepository.getReferenceById(x.getId());
//			entity.getCategories().add(category);
//		});
//	}
}
