package io.nodom.cnj.customer.service;

import io.nodom.cnj.customer.exception.CustomerNotFoundException;
import io.nodom.cnj.customer.repository.CustomerRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerProfilePhotoService {

  private final File rootFile;
  private final CustomerRepository customerRepository;

  public CustomerProfilePhotoService(@Value("${upload.dir}") final String uploadDirectory,
      CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
    this.rootFile = new File(uploadDirectory);
  }

  public Resource readPhoto(Long customerId) {
    return this.customerRepository.findById(customerId)
        .map(customer -> {
          File file = new File(this.rootFile, Arrays.toString(this.encode(customer.getId())));
          return new FileSystemResource(file);
        }).orElseThrow(
            () -> new CustomerNotFoundException(customerId));
  }

  public Long uploadPhoto(Long customerId, MultipartFile photo) {
    return this.customerRepository.findById(customerId)
        .map(customer -> {
          this.writeBinaryDateByCustomer(photo, customer.getId());
          return customer.getId();
        }).orElseThrow(
            () -> new CustomerNotFoundException(customerId));
  }

  private void writeBinaryDateByCustomer(MultipartFile photo, Long customerId) {
    try (InputStream in = photo.getInputStream();
        OutputStream out = new FileOutputStream(
            new File(this.rootFile, Arrays.toString(this.encode(customerId))))) {
      FileCopyUtils.copy(in, out);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private byte[] encode(Long id) {
    return String.valueOf(id).getBytes();
  }
}
