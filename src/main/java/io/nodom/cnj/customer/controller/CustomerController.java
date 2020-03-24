package io.nodom.cnj.customer.controller;


import io.nodom.cnj.customer.exception.CustomerNotFoundException;
import io.nodom.cnj.customer.model.Customer;
import io.nodom.cnj.customer.service.CustomerService;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

  private static final String ID_PATH_TEMPLATE = "/{id}";

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(method = RequestMethod.OPTIONS)
  public ResponseEntity<?> options() {
    return ResponseEntity.ok()
        .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD,
            HttpMethod.OPTIONS).build();
  }

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<List<Customer>> getAllCustomer() {
    return ResponseEntity.ok(this.customerService.findAllCustomers());
  }

  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    Customer customer = this.customerService.findCustomerById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
    return ResponseEntity.ok(customer);
  }

  @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    Customer createdCustomer = this.customerService.createCustomer(customer);

    URI customerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
        .path(ID_PATH_TEMPLATE).buildAndExpand(createdCustomer.getId()).toUri();

    return ResponseEntity.created(customerLocation).body(createdCustomer);
  }

  @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
    Customer updatedCustomer = this.customerService.updateCustomer(customer);

    URI customerLocaltion = ServletUriComponentsBuilder.fromCurrentRequest().path(ID_PATH_TEMPLATE)
        .buildAndExpand(updatedCustomer.getId()).toUri();

    return ResponseEntity.created(customerLocaltion).body(updatedCustomer);
  }

  @DeleteMapping(value = ID_PATH_TEMPLATE, produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
    this.customerService.deleteCustomerById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Method Handler that serve to check the existence of a Customer via id
   *
   * @param id of the customer to find
   * @return
   */
  @RequestMapping(value = ID_PATH_TEMPLATE, method = RequestMethod.HEAD)
  public ResponseEntity<?> head(@PathVariable Long id) {
    this.customerService.findCustomerById(id);
    return ResponseEntity.noContent().build();
  }
}
