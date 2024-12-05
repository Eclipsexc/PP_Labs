package Vegetables;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CabbageTest {

    @Test
    public void test() {
        Cabbage veg = new Cabbage("Брюсельська капуста", 34, Arrays.asList("C", "K", "B6"), 4.0, 3.5, 0.5, "терпкий", "великий");
        assertInstanceOf(Cabbage.class, veg);
    }
}
