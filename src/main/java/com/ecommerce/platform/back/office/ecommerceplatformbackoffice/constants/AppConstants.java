package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.constants;

public class AppConstants {


    private AppConstants() {}

    public static final String FILE_FORMAT_XLSX = ".xlsx";
    public static final String FILE_FORMAT_XLS = ".xls";

    public static final String FILE_FORMAT_NOT_SUPPORTED = "File format not supported. Only Excel files are allowed.";
    public static final String FILE_EMPTY = "File is empty";



    public static final String TOPIC_NEW_ORDER = "/topic/order";
    public static final String WEBSOCKET_SERVER_SELF_URL_TEMPLATE = "ws://localhost:%d/websocket-server";

}
