package maximax444.blps.repository;

import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findAllByCustomer(Customer customer);
}