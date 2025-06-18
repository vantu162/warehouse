package com.example.warehouse.services;

import com.example.warehouse.model.dto.CollateralRequest;
import com.example.warehouse.model.entity.Loans;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoansNativeQuery {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Loans> builderQuerySearch(CollateralRequest request){

        StringBuilder query = new StringBuilder();
        query.append(createQuery());
        query.append(condition(request.keyword));

        // Lấy dữ liệu với phân trang
        List<Loans> loans = entityManager.createNativeQuery(query.toString(), Loans.class)
                .setFirstResult(request.getPage())
                .setMaxResults(request.getSize())
                .getResultList();


        // Đếm tổng số bản ghi
        long totalElements = (long) entityManager.createNativeQuery("select count(*) from (" + query.toString() + ") AS total")
                .getSingleResult();

        Pageable pageable= PageRequest.of(request.getPage(),request.getSize());
        Page<Loans> db = new PageImpl<>(loans, pageable, totalElements);
        return db;
    }

    private String createQuery(){
        return "select l.id AS id, " +
                "l.user_name AS username, " +
                "l.amount AS amount, " +
                "l.month AS month, " +
                "l.month_pay AS monthPay, " +
                "l.rate AS rate, " +
                "l.start_date AS startDate, " +
                "l.end_date AS endDate, " +
                "l.status AS status, " +
                "l.name_status AS nameStatus, " +
                "u.full_name AS fullName "+
                "from Loan l " +
                "left join User u on l.user_name = u.user_name ";
    }

    private String condition(String keyword){
        return "where l.status in (0,1,2) and (" +
                "lower(u.full_name) like '%"+keyword+"%'" +
                ")";
    }

}
