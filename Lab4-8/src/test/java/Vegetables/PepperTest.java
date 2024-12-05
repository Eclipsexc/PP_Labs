package Vegetables;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PepperTest {

    @Test
    public void test() {
        Pepper veg = new Pepper("Перець Чілі", 40, Arrays.asList("C", "A", "B6"), 1.9, 9.0, 0.4, "гострий", 9);
        assertInstanceOf(Pepper.class, veg);
    }
}
