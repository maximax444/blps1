package maximax444.blps.service;

import lombok.AllArgsConstructor;
import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;
import maximax444.blps.entity.Ordered;
import maximax444.blps.entity.Product;
import maximax444.blps.repository.OrderedRepository;
import maximax444.blps.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service("productInterface")
public class ProductService implements ProductInterface {

	private OrderInterface orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderedRepository orderedRepository;


	public List<Product> findProductsByFilter(Long minPrice, Long maxPrice) {
		return productRepository.findAllByPriceBetween(minPrice, maxPrice);
	}


	public List<Product> findMyProducts(Customer customer) {
		return productRepository.findAllByOwner(customer);
	}


	public List<Product> findAllReadyProducts() {
		return productRepository.findAllByStatusIsTrue();
	}


	public List<Product> findAllZeroProducts() {
		return productRepository.findAllByStatusIsFalse();
	}


	public void deleteAllByOwner(Customer customer) {
		productRepository.deleteAllByOwner(customer);
	}

	@Transactional
	public Product createProduct(ProductDTO productDTO, Customer customer) {
		Product product = new Product();
		product.setOwner(customer);
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setCount(productDTO.getCount());

		return productRepository.save(product);
	}

	public Set<Product> getRecomendedProducts(ProductDTO productDTO, Customer customer) {
		List<Order> custOrders = orderService.findAllByCustomer(customer);

		List<Order> relOrders = new ArrayList<>();
		Set<Product> recomendedProducts = new HashSet<>();
		System.out.println("before1");
		for (Order itOrd : custOrders) {
			for (Ordered itProd : orderedRepository.findAllByOrder(itOrd)) {
				if (itProd.getProduct().getName().equals(productDTO.getName())) {
					relOrders.add(itOrd);
				}
			}
		}
		System.out.println("before2");
		for (Order itOrd : relOrders) {
			for (Ordered itProd : orderedRepository.findAllByOrder(itOrd)) {

				recomendedProducts.add(itProd.getProduct());
			}
		}
		System.out.println("before3");
		if (recomendedProducts.size() <= 3) {
			List<Product> products = productRepository.findAllByName(productDTO.getName());
			List<Ordered> allOrderedWithProduct = orderedRepository.findAllByProduct(products.get(0));
			List<Order> allOrdersWithProduct = new ArrayList<>();
			for (Ordered itWp : allOrderedWithProduct) {
				allOrdersWithProduct.add(itWp.getOrder());
			}
			for (Order itOrd2 : allOrdersWithProduct) {
				for (Ordered itProd2 : orderedRepository.findAllByOrder(itOrd2)) {
					if (!itProd2.getProduct().getName().equals(productDTO.getName())) {
						recomendedProducts.add(itProd2.getProduct());
					}
				}
			}
 		}
		System.out.println("before4");
		return recomendedProducts;
	}


	public void save(Product product) {
		productRepository.save(product);
	}
}
