package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.repository;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
}
