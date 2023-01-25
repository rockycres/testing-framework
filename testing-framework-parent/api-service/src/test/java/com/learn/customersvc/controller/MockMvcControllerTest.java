package com.learn.customersvc.controller;


import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import com.learn.customersvc.common.enumeration.CustomerProfileEnum;
import com.learn.customersvc.common.enumeration.GenderTypeEnum;
import com.learn.customersvc.dto.model.CustomerDTO;
import com.learn.customersvc.dto.model.CustomersDTO;
import com.learn.customersvc.model.Customer;
import com.learn.customersvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
class MockMvcControllerTest {

    Faker faker = new Faker();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<CustomerDTO> customers;

    @BeforeEach
    void setUp() {


        this.customers = new ArrayList<>();
        this.customers.add(new CustomerDTO(1L, "Santhosh", "sekar", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue()));

        this.customers.add(new CustomerDTO(1L, "tester", "testing", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue()));

        this.customers.add(new CustomerDTO(1L, "developer", "dev", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue()));


    }

    @Test
    void shouldFetchOneCustomerById() throws Exception {
        final Long customerId = 1L;
        final CustomerDTO customer = new CustomerDTO(1L, "Santhosh", "sekar", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue());


        given(customerService.findById(customerId)).willReturn(Optional.of(customer));

        this.mockMvc.perform(get("/api-customer/v1/customer/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.givenName", is(customer.getGivenName())))
                .andExpect(jsonPath("$.data.surName", is(customer.getSurName())));
    }

    @Test
    void shouldFetchNoneWhenCustomerNotFound() throws Exception {
        final Long customerId = 1L;
        final CustomerDTO customer = new CustomerDTO(1L, "Santhosh", "sekar", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue());

        given(customerService.findById(customerId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/api-customer/v1/customer/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.details", is("customer not found")));
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        Page<CustomerDTO> pagedResponse = new PageImpl(customers);
        given(customerService.findAll(any())).willReturn(pagedResponse);
        this.mockMvc.perform(get("/api-customer/v1/customer/list")).andExpect(status().isOk()).andExpect(jsonPath("$.totalNumberOfElements", is(customers.size())));
    }


    @Test
    void shouldCreateNewUser() throws Exception {

        final CustomerDTO customer = new CustomerDTO(1L, "Santhosh", "sekar", new java.sql.Date(faker.date().birthday().getTime()).toLocalDate(), GenderTypeEnum.MALE.getValue(), CustomerProfileEnum.BASIC.getValue());

        given(customerService.save(any(Customer.class))).willReturn(customer);


        this.mockMvc.perform(post("/api-customer/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.givenName", is(customer.getGivenName())))
                .andExpect(jsonPath("$.data.surName", is(customer.getSurName())));
    }


}
