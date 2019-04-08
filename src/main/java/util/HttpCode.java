package util;

public enum HttpCode {
    INTERNAL_SERVER_ERROR("500"),
    OK("200"),
    CREATED("201");
    private String code;
    HttpCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
