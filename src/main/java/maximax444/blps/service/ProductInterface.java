package maximax444.blps.service;

import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Customer;
import maximax444.blps.entity.Order;
import maximax444.blps.entity.Ordered;
import maximax444.blps.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductInterface {
    List<Product> findProductsByFilter(Long minPrice, Long maxPrice);


    List<Product> findMyProducts(Customer customer);


    List<Product> findAllReadyProducts();


    List<Product> findAllZeroProducts();


    void deleteAllByOwner(Customer customer);


    Product createProduct(ProductDTO productDTO, Customer customer);

    Set<Product> getRecomendedProducts(ProductDTO productDTO, Customer customer);


    void save(Product product);
}
