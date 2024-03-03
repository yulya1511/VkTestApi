package api;

public enum ApiConstants {
    POST("wall.post?"),
    DELETE("wall.delete?"),
    GET_WALL_UPLOAD_SERVER("photos.getWallUploadServer?"),
    WALL_GET("wall.get?"),
    SAVE_WALL_PHOTO("photos.saveWallPhoto?");

    private String method;

    ApiConstants(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }
}
