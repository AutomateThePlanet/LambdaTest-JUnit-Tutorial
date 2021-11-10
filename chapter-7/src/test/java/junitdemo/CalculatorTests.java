package junitdemo;

import org.junit.jupiter.api.*;

@TestMethodOrder(value = MethodOrderer.Random.class)
public class CalculatorTests {
    private final Calculator _calculator = new Calculator();

    @BeforeAll
    public static void setUpClass() {
        System.out.println("This is @BeforeAll annotation");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("This is @BeforeEach annotation");
    }

    @NightlyRunTest
    @Order(2)
    public void test_Addition() {
        System.out.println("This is test 1");
        var actualResult = _calculator.add(1, 1);

        Assertions.assertEquals(2, actualResult);
    }

    @Test
    @Order(1)
    public void testAdditionDifferentNumbers(){
        System.out.println("This is test 2");
        var actualResult = _calculator.add(2, 1);

        Assertions.assertEquals(3, actualResult);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("This is @After annotation");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("This is @AfterAll annotation");
    }
}
