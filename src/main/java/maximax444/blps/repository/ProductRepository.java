package maximax444.blps.repository;

import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findAllByPriceBetween(Long minPrice, Long maxPrice);

	List<Product> findAllByOwner(Customer owner);

	List<Product> findAllByStatusIsTrue();

	List<Product> findAllByStatusIsFalse();

	List<Product> findAllByName(String name);

	void deleteAllByOwner(Customer customer);

}