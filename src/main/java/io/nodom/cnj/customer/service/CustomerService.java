package io.nodom.cnj.customer.service;


import io.nodom.cnj.customer.exception.CustomerNotFoundException;
import io.nodom.cnj.customer.model.Customer;
import io.nodom.cnj.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> findAllCustomers() {
    return this.customerRepository.findAll();
  }

  public Customer findCustomerById(Long customerId) {
    return this.customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException("No Customer with Id: " + customerId));
  }

  public Customer createCustomer(@Valid Customer customer) {
    Objects.requireNonNull(customer, "customer cannot be NULL");
    return this.customerRepository.save(customer);
  }

  public Customer deleteCustomerById(Long id) {
    Customer customer = this.customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("No Customer With Id" + id));

    this.customerRepository.delete(customer);
    return customer;
  }

  public Customer updateCustomer(Customer customer) {
    Objects.requireNonNull(customer, "customer cannot be NULL");

    return this.customerRepository.findById(customer.getId())
        .map(existingCustomer -> {
          existingCustomer.setFirstName(customer.getFirstName());
          existingCustomer.setLastName(customer.getLastName());
          this.customerRepository.save(existingCustomer);
          return customer;
        }).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
  }
}
