package maximax444.blps.controller;

import lombok.AllArgsConstructor;
import maximax444.blps.dto.ProductDTO;
import maximax444.blps.entity.Product;
import maximax444.blps.security.MyResourceNotFoundException;
import maximax444.blps.service.CustomerInterface;
import maximax444.blps.service.CustomerService;
import maximax444.blps.service.ProductInterface;
import maximax444.blps.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final CustomerInterface customerService;
	private final ProductInterface productService;

	@PostMapping(value = "/recomend")
	public Set<Product> findRecomendedProducts(HttpServletRequest httpServletRequest,
											   @RequestBody ProductDTO productDTO) {
		var customerOpt = customerService.whoIs(httpServletRequest);
		if (customerOpt.isEmpty()) {
			throw new MyResourceNotFoundException("Ошибка авторизации");
		}
		var customer = customerOpt.get();
		if (customer.isBanned()) {
			throw new MyResourceNotFoundException("Ваши товары скрыты с сайта, свяжитесь с администрацией");
		}

		return productService.getRecomendedProducts(productDTO, customer);
	}
}
