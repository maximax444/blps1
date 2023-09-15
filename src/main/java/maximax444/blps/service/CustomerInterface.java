package maximax444.blps.service;

import maximax444.blps.dto.CustomerDTO;
import maximax444.blps.entity.Customer;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface CustomerInterface {
    String login(String username, String password);
    List<Customer> findAll();
    Optional<User> findByToken(String token);
    Customer findById(Long id);
    Customer registration(CustomerDTO customerDTO);
    Optional<Customer> whoIs(HttpServletRequest httpServletRequest);
    void deleteMe(HttpServletRequest httpServletRequest);
    void save(Customer customer);
}
