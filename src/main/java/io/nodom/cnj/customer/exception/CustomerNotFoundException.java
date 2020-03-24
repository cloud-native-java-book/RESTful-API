package io.nodom.cnj.customer.exception;


import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {

  private final Long customerId;

  public CustomerNotFoundException(Long customerId) {
    super("cannot find customer with id: " + customerId);
    this.customerId = customerId;
  }
}
