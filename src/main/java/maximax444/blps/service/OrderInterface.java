package maximax444.blps.service;

import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderInterface {

    void save(Order order);

    void delete(Order order);

    Order addOrder(List<ProductDTO> productDTOs, Customer customer);
    List<Order> findAllByCustomer(Customer customer);
}
