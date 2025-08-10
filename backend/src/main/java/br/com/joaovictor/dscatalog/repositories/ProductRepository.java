package br.com.joaovictor.dscatalog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.joaovictor.dscatalog.dtos.ProductDTO;
import br.com.joaovictor.dscatalog.entities.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{
	
	
	@Query("SELECT obj FROM Product obj "
			+ "JOIN FETCH obj.categories")
	Page<ProductDTO> searchAll(PageRequest pageRequest);
	
}
