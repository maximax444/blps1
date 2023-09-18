package maximax444.blps.service;


import maximax444.blps.dto.CustomerDTO;
import maximax444.blps.entity.Customer;
import maximax444.blps.repository.CustomerRepository;
import maximax444.blps.security.MyResourceNotFoundException;
import lombok.AllArgsConstructor;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
@Service("customerInterface")
public class CustomerService implements CustomerInterface {

	private final ProductInterface productService;
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public String login(String username, String password) {
		Optional<Customer> customer = customerRepository.login(username, password);
		if (customer.isPresent()) {
			Base64.Encoder encoder = Base64.getEncoder();
			String token = new String(encoder.encode(username.getBytes())) +
					":" + new String(encoder.encode(password.getBytes()));
			Customer custom = customer.get();
			custom.setToken(token);
			customerRepository.save(custom);
			return token;
		}

		return "";
	}

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerRepository.findAll();
	}

	@Override
	public Optional<User> findByToken(String token) {
		Optional<Customer> customer = customerRepository.findByToken(token);
		if (customer.isPresent()) {
			Customer customer1 = customer.get();
			User user = new User(customer1.getUserName(), customer1.getPassword(), true, true, true, true,
					AuthorityUtils.createAuthorityList("USER"));
			return Optional.of(user);
		}
		return Optional.empty();
	}

	@Override
	public Customer findById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.orElseThrow(() -> new MyResourceNotFoundException("Пользователь не найден"));
	}

	@Override
	public Customer registration(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		if (customerDTO.getName() == null || customerDTO.getPassword() == null) {
			throw new MyResourceNotFoundException("Вы ошиблись, отсутствует имя или пароль");
		}
		customer.setUserName(customerDTO.getName());
		customer.setPassword(customerDTO.getPassword());
		var res = customerRepository.findByUserNameAndPassword(
				customerDTO.getName(),
				customerDTO.getPassword()
		);
		return res.orElseGet(() -> customerRepository.save(customer));
	}

	@Override
	public Optional<Customer> whoIs(HttpServletRequest httpServletRequest) {
		String token = !Objects.equals(httpServletRequest.getHeader(AUTHORIZATION), "") ?
				httpServletRequest.getHeader(AUTHORIZATION) : "";
		token = token.replaceAll("Basic", "").trim();
		return customerRepository.findByToken(token);
	}

	@Override
	@Transactional
	public void deleteMe(HttpServletRequest httpServletRequest) {
		var customerOpt = whoIs(httpServletRequest);
		if (customerOpt.isEmpty()) {
			throw new MyResourceNotFoundException("Ошибка авторизации");
		}
		var customer = customerOpt.get();
		productService.deleteAllByOwner(customer);
		customerRepository.delete(customer);
	}


	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
	}
}
