package com.learn.customersvc.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@Setter
public class CustomersDTO {

    private List<CustomerDTO> data;
    private long totalNumberOfElements;
    private int totalPages;
    private int currentPage;
    @JsonProperty("isFirst")
    private boolean isFirst;
    @JsonProperty("isLast")
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public CustomersDTO(Page<CustomerDTO> customerDTOPage){
        this.setData(customerDTOPage.getContent());
        this.setTotalNumberOfElements(customerDTOPage.getTotalElements());
        this.setTotalPages(customerDTOPage.getTotalPages());
        this.setCurrentPage(customerDTOPage.getNumber()+1);
        this.setFirst(customerDTOPage.isFirst());
        this.setLast(customerDTOPage.isLast());
        this.setHasNext(customerDTOPage.hasNext());
        this.setHasPrevious(customerDTOPage.hasPrevious());

    }





}
