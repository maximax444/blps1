package maximax444.blps.service;

import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;
import maximax444.blps.entity.Ordered;
import maximax444.blps.entity.Product;
import maximax444.blps.repository.OrderRepository;
import lombok.AllArgsConstructor;
import maximax444.blps.repository.OrderedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service("orderInterface")
public class OrderService implements OrderInterface {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderedRepository orderedRepository;

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void delete(Order order) {
		orderRepository.delete(order);
	}

	@Transactional
	public Order addOrder(List<ProductDTO> productDTOs, Customer customer) {
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setStatus(true);
		orderRepository.save(newOrder);

		Ordered newOrdered;
		for (ProductDTO itProd : productDTOs) {
			newOrdered  = new Ordered();
			newOrdered.setOrder(newOrder);

			Product product = new Product();
			product.setOwner(customer);
			product.setName(itProd.getName());
			product.setPrice(itProd.getPrice());
			product.setCount(itProd.getCount());
			newOrdered.setProduct(product);

			orderedRepository.save(newOrdered);
		}
		return newOrder;
	}

	public List<Order> findAllByCustomer(Customer customer) {
		return orderRepository.findAllByCustomer(customer);
	}

}
