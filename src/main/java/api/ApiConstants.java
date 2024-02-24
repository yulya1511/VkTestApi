package api;

public enum ApiConstants {
    POST("wall.post?"),
    DELETE("wall.delete?");

    private String method;

    ApiConstants(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }
}
