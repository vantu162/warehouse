package com.example.warehouse.util;

public class Contants {

    public static final String SERVER_URL = "http://localhost:8081";
    public static final String REALM = "myrealm";
    public static final String CLIENT_ID = "spr_id";
    public static final String CLIENT_SECRET = "Xm4PUSSRRMFnOvto2GfiVnRRUXvg3PSU";
    public static final String REFRESH_TOKEN_URL = "http://localhost:8081/realms/myrealm/protocol/openid-connect/token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String PASSWORD = "password";

    public static final String TOKEN_URL = "http://localhost:8081/realms/myrealm/protocol/openid-connect/token";
    public static final String REALMS_URL = "http://localhost:8081/realms/myrealm";
    public static final String ROLE_NAME_USER = "USER";


    public static final String TEXT_ACCESS_TOKEN = "access_token";
    public static final String TEXT_RESOURCE_ACCESS = "resource_access";
    public static final String TEXT_ROLES = "roles";
    public static final String TEXT_USERNAME = "username";
    public static final String TEXT_PASSWORD = "password";
    public static final String TEXT_CLIENT_ID = "client_id";
    public static final String TEXT_CLIENT_SECRET = "client_secret";
    public static final String TEXT_GRANT_TYPE = "grant_type";
    public static final String TEXT_REFRESH_TOKEN = "refreshToken";
    public static final String TEXT_PREFERRED_USER_NAME = "preferred_username";

    public static final String TEXT_BAD_REQUEST= "bad request";
    public static final String APPROVE_LOAN = "Phê duyệt";
    public static final String REJECT_LOAN = "Từ chối";
    public static final String ACCEPT_LOAN = "Tiếp nhận xử lý";

    public static final String FILED_START_DATE = "startDate";
    public static final String FILED_END_DATE = "endDate";
    public static final String FILED_USERNAME = "username";
    public static final String FILED_STATUS = "status";

    // tex exception
    public static final String EXCEPTION_INVALID_USER_EXISTS ="User already exists!";

    public static final String EXCEPTION_INVALID_USERNAME = "Username is invalid!";
    public static final String EXCEPTION_INVALID_PASSWORD = "Password is invalid!";
    public static final String EXCEPTION_INVALID_EMAIL = "Email is invalid!";

    public static final String EXCEPTION_INVALID_PHONE = "Phone is invalid!";
    public static final String EXCEPTION_INVALID_FIRST_NAME = "First name is invalid!";
    public static final String EXCEPTION_INVALID_LAST_NAME = "Last name is invalid!";
    public static final String EXCEPTION_INVALID_NATIONAL_ID = "National id is invalid!";

    public static final String EXCEPTION_INVALID_AMOUNT = "Amount is invalid!";
    public static final String EXCEPTION_INVALID_COLOR = "Color is invalid!";
    public static final String EXCEPTION_INVALID_TYPE = "Type is invalid!";
    public static final String EXCEPTION_INVALID_MANUFACTURER = "Manufacturer is invalid!";
    public static final String EXCEPTION_INVALID_MANUFACTURER_YEAR = "Manufacture year is invalid!";
    public static final String EXCEPTION_INVALID_LICENSE_PLATE = "License plate is invalid!";
    public static final String EXCEPTION_INVALID_MONTH = "Month is invalid!";


}
