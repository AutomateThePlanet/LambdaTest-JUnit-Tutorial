package junitdemo.parallel;

public enum TestResult {
    SUCCESS("passed"),
    FAILURE("failed");

    private String result;


    TestResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
