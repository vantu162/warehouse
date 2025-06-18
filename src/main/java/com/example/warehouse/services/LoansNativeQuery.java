package com.example.warehouse.services;

import com.example.warehouse.model.dto.CollateralRequest;
import com.example.warehouse.model.entity.Loansv1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    public Page<Loansv1> builderQuerySearch(CollateralRequest request){

        StringBuilder query = new StringBuilder();
        query.append(createQuery());

        if (request.getStatusCode() != null) {
            query.append("WHERE l.status in :statusList ");
        }

        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            query.append("AND lower(u.full_name)  like :keyword");
        }

        Query test= entityManager.createNativeQuery(query.toString(), Loansv1.class);
        builderParameter(test, request);

       // Lấy dữ liệu với phân trang
        @SuppressWarnings("unchecked")
        List<Loansv1> loans = test.setFirstResult(request.getPage())
                .setMaxResults(request.getSize())
                .getResultList();

        String countSql = "SELECT COUNT(*) FROM (%s) AS total".formatted(query);
        Query countQuery = entityManager.createNativeQuery(countSql);
        builderParameter(countQuery, request);

        Number total = (Number) countQuery.getSingleResult();
        int totalElements = total.intValue();  // ✅ đây là số bản ghi tổng

        Pageable pageable= PageRequest.of(request.getPage(),request.getSize());
        return new PageImpl<>(loans, pageable, totalElements);
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

    private void builderParameter(Query query,CollateralRequest request){
        if (request.getStatusCode() != null) {
            query.setParameter("statusList", request.getStatusCode());
        }
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            query.setParameter("keyword", "%" + request.getKeyword().toLowerCase() + "%");
        }
    }
}
