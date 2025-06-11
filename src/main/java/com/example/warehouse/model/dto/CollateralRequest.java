package com.example.warehouse.model.dto;

import com.example.warehouse.model.entity.Collateral;
import com.example.warehouse.model.entity.Loan;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CollateralRequest {
    public Loan loan;
    public Collateral collateral;
    public int status;//1: approve, 2 reject
    public String username;
    public Long idLoan;
    public List<Integer> statusCode;// [0,1,2]
    public String keyword;
    public int page;
    public int size;

}
