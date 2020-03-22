package io.nodom.cnj.customer.repository;

import io.nodom.cnj.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
