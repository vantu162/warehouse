package com.example.warehouse.services;

import com.example.warehouse.exception.CustomException;
import com.example.warehouse.model.dto.ApiResponse;
import com.example.warehouse.model.dto.CollateralRequest;
import com.example.warehouse.model.dto.ResponseSearch;
import com.example.warehouse.model.entity.Loans;
import com.example.warehouse.repository.LoansRepository;
import com.example.warehouse.model.entity.Collateral;
import com.example.warehouse.repository.loan.CollateralRepository;
import com.example.warehouse.model.entity.User;
import com.example.warehouse.repository.acc.UserRepository;
import com.example.warehouse.model.entity.Loan;
import com.example.warehouse.repository.loan.LoanQueryManager;
import com.example.warehouse.repository.loan.LoanRepository;
import com.example.warehouse.util.Contants;
import com.example.warehouse.util.Code;
import com.example.warehouse.util.Validate;
import com.google.gson.Gson;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Service
public class LoanManager implements LoanQueryManager
{
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(LoanManager.class);
    public final LoanRepository loanRepository;
    public final CollateralRepository collateralRepository;
    public final UserRepository userRepository;
    public final LoansRepository loansRepository;

    public final LoansNativeQuery loansNativeQuery;

    public LoanManager(LoanRepository loanRepository, CollateralRepository collateralRepository,
                       UserRepository userRepository, LoansRepository loansRepository, LoansNativeQuery loansNativeQuery) {
        this.loanRepository = loanRepository;
        this.collateralRepository = collateralRepository;
        this.userRepository = userRepository;
        this.loansRepository = loansRepository;
        this.loansNativeQuery = loansNativeQuery;
    }

    // xu logic tao khoan vay the chap
    @Transactional
    @Override
    public ApiResponse<CollateralRequest> createLoan(JwtAuthenticationToken token, CollateralRequest collateralRequest) {
        initLoanValidate(collateralRequest);
        String username = token.getToken().getClaim(Contants.TEXT_PREFERRED_USER_NAME);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getNationalId() != null && user.getUsername() != null ){

                Loan loan  = loanRepository.save(collateralRequest.loan);
                Collateral collateral = collateralRepository.save(collateralRequest.collateral);

                CollateralRequest data = new CollateralRequest();
                data.setLoan(loan);
                data.setCollateral(collateral);

                return ApiResponse.<CollateralRequest>builder()
                        .statusCode(Code.CREATED.getCode())
                        .message("Mortgage loan created successfully")
                        .data(data)
                        .build();
            }
        }

        return ApiResponse.<CollateralRequest>builder()
               .statusCode(Code.BAD_REQUEST.getCode())
               .message("Mortgage loan creation failed. Please try again")
               .build();
    }

    // kiem ra du lieu yeu cau khi tao khoan vay the chap co hop le khong
    private void initLoanValidate(CollateralRequest collateralRequest){

        Collateral collateral = collateralRequest.getCollateral();

        if(!Validate.isValidStr(collateral.getColor())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_COLOR);
        }

        if(!Validate.isValidStr(collateral.getType())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_TYPE);
        }

        if(!Validate.isValidStr(collateral.getManufacturer())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_MANUFACTURER);
        }

        if(!Validate.isValidStr(collateral.getManufactureYear())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_MANUFACTURER_YEAR);
        }

        if(!Validate.isValidStr(collateral.getLicensePlate())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_LICENSE_PLATE);
        }

        Loan loan = collateralRequest.getLoan();

        if(!Validate.isValidNumber(loan.getAmount())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_AMOUNT);
        }

        if(!Validate.isValidNumber(loan.getMonth())){
            throw new CustomException(Code.BAD_REQUEST.getCode(), Contants.EXCEPTION_INVALID_MONTH);
        }
    }

    // phe duyet, tu choi khoan vay the chap
    @Override
    public ApiResponse<Loan> updateLoan(CollateralRequest collateralRequest) {
        Loan loan = null;
        Optional<Loan> loanOptional = loanRepository.findByUsernameAndId(collateralRequest.username, collateralRequest.idLoan);
        if (loanOptional.isPresent()) {

            loan = loanOptional.get();
            loan.setStatus(collateralRequest.getStatus());

            String str = "";
            int stausCode = 0;
            if(collateralRequest.getStatus() == 1){
                str = Contants.APPROVE_LOAN;
                stausCode = Code.ACCEPTED.getCode();
            }else if(collateralRequest.getStatus() == 2){
                str = Contants.REJECT_LOAN;
                stausCode = Code.REJECT.getCode();
            }
            loan.setNameStatus(str);
            loanRepository.save(loan);

            return ApiResponse.<Loan>builder()
                    .statusCode(stausCode)
                    .message("Mortgage loan updated successfully")
                    .data(loan)
                    .build();
        }
        return ApiResponse.<Loan>builder()
                .statusCode(Code.BAD_REQUEST.getCode())
                .message("Invalid request")
                .data(loan)
                .build();
    }

    // lay ve danh sach khoan vay the chap theo keyword
    @Override
    public ResponseSearch<Loans> getListLoan(CollateralRequest request, JwtAuthenticationToken token) {
        Collection<? extends GrantedAuthority> authorities = token.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String username = "";
        if (roles.contains("ROLE_USER")) {
            username = token.getToken().getClaim(Contants.TEXT_PREFERRED_USER_NAME);
        }
        Page<Loans> pages = getPageSearch(request, username);
        System.out.println("Loans: " + new Gson().toJson(pages));
        List<Loans> datas = pages.getContent();

        return ResponseSearch.<Loans>builder()
                .currentPage(pages.getNumber())
                .pageSize(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .data(datas)
                .build();
    }

    @Override
    public ResponseSearch<Loans> getListLoanByEntityManager(CollateralRequest request, JwtAuthenticationToken token) {

        Page<Loans> pages = loansNativeQuery.builderQuerySearch(request);
        System.out.println("query EntityMananger Loans : " + new Gson().toJson(pages));
        List<Loans> datas = pages.getContent();

        return ResponseSearch.<Loans>builder()
                .currentPage(pages.getNumber())
                .pageSize(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .data(datas)
                .build();
    }

    // dung page de tra ve du lieu cho phan trang
    private Page<Loans> getPageSearch(CollateralRequest request, String username) {
        Specification<Loans> loans = (root, query, criteriaBuilder) -> builderSearchQuery(root, criteriaBuilder,
                request.getKeyword(), request.getStatusCode(), username);
        Pageable pageable= PageRequest.of(request.getPage(),request.getSize());
        return loansRepository.findAll(loans, pageable);
    }
    // gop cac dieu kien loc voi nhau theo dieu kien loc: status AND (column OR column...)
    private Predicate builderSearchQuery(Root<Loans> root, CriteriaBuilder cb, String keyword, List<Integer> status, String username) {
        logger.debug("Keyword nhận được: '{}'", keyword);

        List<Predicate> predicates = new ArrayList<>();
        if(!username.isEmpty()){
            predicates.add(cb.in(root.get(Contants.FILED_USERNAME)).value(username));
        }
        predicates.add(root.get(Contants.FILED_STATUS).in(status));

//        predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%"));

        List<Predicate> searchFiledPredicates = buildSearchFieldPredicatesQuery(cb, root, keyword);
        predicates.add(cb.or(searchFiledPredicates.toArray(new Predicate[0])));

        return cb.and(predicates.toArray(new Predicate[0]));// .toArray(new Predicate[0]) - dung de chanh loi 1=0
    }

    private List<Predicate> buildSearchFieldPredicatesQuery(CriteriaBuilder cb, Root<Loans> root, String keyword ){
        List<Predicate> orConditions = new ArrayList<>();
        // chuyen doi cac truong cua loans class thanfh cac field
        Field[] fields = Loans.class.getDeclaredFields();
        List<String> fieldColumns = new ArrayList<>();

        for (Field field : fields) {
            System.out.println("field: " +field.getName());
            fieldColumns.add(field.getName());
        }

        for (String fieldName : fieldColumns) {
            Path<?> path = root.get(fieldName);
            Class<?> type = path.getJavaType();
            // kiem tra type va them dieu kien filter theo cac column
            if (type == String.class) {
                orConditions.add(cb.like(cb.lower(path.as(String.class)), "%" + keyword.toLowerCase() + "%"));
            } else if (type == Long.class || type == long.class) {
                try {
                    orConditions.add(cb.like(cb.lower(path.as(String.class)), "%" + Long.parseLong(keyword) + "%"));
                } catch (NumberFormatException ignored) {}
            }
            if(fieldName.equals(Contants.FILED_START_DATE) || fieldName.equals(Contants.FILED_END_DATE)) {

                Expression<String> formattedDate = cb.function("DATE_FORMAT", String.class,
                        root.get("startDate"), cb.literal("%Y-%m-%d %H:%i:%s")
                );
                orConditions.add(cb.like(cb.lower(formattedDate), "%" + keyword.toLowerCase() + "%"));
            }
        }
        return orConditions;
    }
}
