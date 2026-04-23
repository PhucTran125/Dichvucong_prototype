package com.viettel.docsearch.domain;

public enum DocumentType {
    BIRTH_CERT("Giấy khai sinh"),
    MARRIAGE_CERT("Giấy đăng ký kết hôn"),
    BUSINESS_REG("Giấy phép kinh doanh"),
    DRIVER_LICENSE("Giấy phép lái xe"),
    LAND_USE_RIGHT("Giấy chứng nhận quyền sử dụng đất");

    private final String vietnameseLabel;

    DocumentType(String vietnameseLabel) {
        this.vietnameseLabel = vietnameseLabel;
    }

    public String getVietnameseLabel() {
        return vietnameseLabel;
    }
}
