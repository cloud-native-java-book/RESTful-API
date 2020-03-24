package io.nodom.cnj.customer.controller;


import io.nodom.cnj.customer.service.CustomerProfilePhotoService;
import java.net.URI;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Slf4j
@RestController
@RequestMapping(value = "/customers/{id}/photo")
public class CustomerProfilePhotoController {

  private final CustomerProfilePhotoService customerProfilePhotoService;

  public CustomerProfilePhotoController(CustomerProfilePhotoService customerProfilePhotoService) {
    this.customerProfilePhotoService = customerProfilePhotoService;
  }

  @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<Resource> getCustomerProfilePhoto(@PathVariable Long id) {
    Resource customerPhoto = this.customerProfilePhotoService.readPhoto(id);

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(customerPhoto);
  }

  /**
   * It could be an intensive IO Operation, for this reason, we decided to do it the non-blocking
   * way, using Asynchronous Calls, note that is recommended to use only the heavy operation inside
   * the callable lambda - {@link Callable}.
   *
   * @param id    of the customer's profile.
   * @param photo to be uploaded.
   * @return a response with the HTTP Status.
   */
  @RequestMapping(method = {RequestMethod.POST,
      RequestMethod.PUT}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Callable<ResponseEntity<?>> uploadImage(@PathVariable Long id,
      @RequestParam MultipartFile photo) {
    log.info(
        String.format("====== upload start: /customer/%s/photo (%S bytes) ======", id,
            photo.getSize()));
    return () -> {
      Long customerId = this.customerProfilePhotoService.uploadPhoto(id, photo);

      URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("{id}")
          .buildAndExpand(customerId).toUri();

      return ResponseEntity.created(location).build();
    };
  }

}
