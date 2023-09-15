package maximax444.blps.repository;

import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;
import maximax444.blps.entity.Ordered;
import maximax444.blps.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedRepository extends CrudRepository<Ordered, Long> {
    List<Ordered> findAllByProduct(Product product);
    List<Ordered> findAllByOrder(Order order);
}