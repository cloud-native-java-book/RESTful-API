package io.nodom.cnj.customer.controller;

import io.nodom.cnj.customer.exception.CustomerNotFoundException;
import java.util.Optional;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequestMapping(produces = "application/vnd.error+json")
@RestControllerAdvice(annotations = RestController.class)
public class CustomerControllerAdvice {

  @ExceptionHandler(CustomerNotFoundException.class)
  ResponseEntity<VndErrors> notFoundException(CustomerNotFoundException ex) {
    return this.error(ex, HttpStatus.NOT_FOUND, ex.getCustomerId());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<VndErrors> assertionException(IllegalArgumentException ex) {
    return this.error(ex, HttpStatus.BAD_REQUEST, 0L);
  }

  private <E extends Exception> ResponseEntity<VndErrors> error(E error, HttpStatus httpSatatus,
      Long logref) {
    String msg = Optional.of(error.getMessage()).orElse(error.getClass().getSimpleName());
    return new ResponseEntity<>(new VndErrors("CustomerId: ".concat(logref.toString()), msg), httpSatatus);
  }

}
