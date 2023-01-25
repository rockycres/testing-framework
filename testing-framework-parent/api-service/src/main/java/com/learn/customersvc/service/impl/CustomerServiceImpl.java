package com.learn.customersvc.service.impl;


import com.learn.customersvc.dto.model.CustomerDTO;
import com.learn.customersvc.exception.CustomerNotFoundException;
import com.learn.customersvc.model.Customer;
import com.learn.customersvc.repository.CustomerRepository;
import com.learn.customersvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Class that implements the travel's service methods
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public CustomerDTO save(Customer travel) {
         return customerRepository.save(travel).convertEntityToDTO();
    }

    /**
     * @see CustomerService#findAllByOrderNumber(String)
     */
    /*@Override
    public List<Customer> findAllByOrderNumber(String orderNumber) {
        return travelRepository.findAllByOrderNumber(orderNumber);
    }*/

    /**
     * @see CustomerService#deleteById(Long)
     */
    @Override
    public void deleteById(Long travelId) {
        customerRepository.deleteById(travelId);
    }

    /**
     * @throws CustomerNotFoundException
     * @see CustomerService#findById(Long)
     */
    @Override
    public Optional<CustomerDTO> findById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer id=" + id + " not found"));
        return Optional.ofNullable(customer.convertEntityToDTO());
    }

    /**
     * @see CustomerService#findBetweenDates(LocalDateTime, LocalDateTime, Pageable)
     */
  /*  @Override
    public Page<Customer> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return travelRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate, endDate, pageable);
    }*/

    /**
     * @see CustomerService#findAll(Pageable)
     */




    public Page<CustomerDTO> findAll(Pageable p) {
        return customerRepository.findAll(p).map(customer -> customer.convertEntityToDTO());
    }

}
