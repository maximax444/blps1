package maximax444.blps.controller;

import maximax444.blps.dto.CustomerDTO;
import maximax444.blps.entity.Customer;
import lombok.AllArgsConstructor;
import maximax444.blps.service.CustomerInterface;
import maximax444.blps.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TokenController {

    private final CustomerInterface customerService;

    @PostMapping("/token")
    public String getToken(@RequestParam("username") final String username, @RequestParam("password") final String password) {
        String token = customerService.login(username, password);
        if (StringUtils.isEmpty(token)) {
            return "no token found";
        }
        return token;
    }

    @PostMapping(value = "/registration")
    public Customer createNewUser(@RequestBody CustomerDTO customerDTO) {
        return customerService.registration(customerDTO);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public List<Customer> getAllUsers() {
        return customerService.findAll();
    }
}
