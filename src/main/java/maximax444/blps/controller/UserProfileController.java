package maximax444.blps.controller;

import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Product;
import maximax444.blps.entity.Customer;
import maximax444.blps.security.MyResourceNotFoundException;
import maximax444.blps.service.CustomerInterface;
import maximax444.blps.service.CustomerService;
import maximax444.blps.service.ProductInterface;
import maximax444.blps.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserProfileController {

    private final CustomerInterface customerService;
    private final ProductInterface productService;

    @GetMapping(value = "/users/user/{id}", produces = "application/json")
    public Customer getUserDetail(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @DeleteMapping(value = "/users/delete/me")
    public String deleteMe(HttpServletRequest httpServletRequest) {
        customerService.deleteMe(httpServletRequest);
        return "Вы успешно удалены из системы";
    }

    @GetMapping(value = "/users/user/all", produces = "application/json")
    public List<Customer> getAllUsers() {
        return customerService.findAll();
    }


    @GetMapping(value = "/products/filter", produces = "application/json")
    public List<Product> findProducts(@RequestParam("minPrice") final Long minPrice,
                                          @RequestParam("maxPrice") final Long maxPrice) {
        return productService.findProductsByFilter(minPrice, maxPrice);
    }

    @GetMapping(value = "/products/my", produces = "application/json")
    public List<Product> findMyProducts(HttpServletRequest httpServletRequest) {
        var customerOpt = customerService.whoIs(httpServletRequest);
        if (customerOpt.isEmpty()) {
            throw new MyResourceNotFoundException("Ошибка авторизации");
        }
        var customer = customerOpt.get();
        if (customer.isBanned()) {
            throw new MyResourceNotFoundException("Ваши товары скрыты с сайта, свяжитесь с администрацией");
        }
        return productService.findMyProducts(customer);
    }

    @GetMapping(value = "/products/all", produces = "application/json")
    public List<Product> findAllApprovedApartments() {
        return productService.findAllReadyProducts();
    }


    @PostMapping(value = "/products/create", produces = "application/json")
    public Product createProduct(HttpServletRequest httpServletRequest,
                                     @RequestBody ProductDTO productDTO) {
        var customerOpt = customerService.whoIs(httpServletRequest);
        if (customerOpt.isEmpty()) {
            throw new MyResourceNotFoundException("Ошибка авторизации");
        }
        var customer = customerOpt.get();
        if (customer.isBanned()) {
            throw new MyResourceNotFoundException("Ваши товары скрыты с сайта, свяжитесь с администрацией");
        }
        return productService.createProduct(productDTO, customer);
    }

}
