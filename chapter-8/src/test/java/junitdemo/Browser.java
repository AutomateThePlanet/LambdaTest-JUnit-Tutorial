package junitdemo;

public enum Browser {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    OPERA("opera"),
    SAFARI("safari");

    private String browserName;


    Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getName() {
        return browserName;
    }
}
