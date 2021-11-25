package junitdemo;

public enum Brand {
    DINERS("Diners"),
    DISCOVER("Discover"),
    MATER_CARD("MasterCard"),
    VISA("Visa");

    Brand(String brandName) {
        this.brandName = brandName;
    }
    private String brandName;

    public String getBrandName() {
        return brandName;
    }
}