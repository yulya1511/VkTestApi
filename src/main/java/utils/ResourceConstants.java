package utils;

public enum ResourceConstants {
    CONFIG("config"),
    TEST_DATA("test_data");

    private final String resource;

    ResourceConstants(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return this.resource;
    }
}
