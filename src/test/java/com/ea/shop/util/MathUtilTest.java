package com.ea.shop.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = MathUtil.class)
public class MathUtilTest {

    @Test
    public void shouldPercentage() {
        BigDecimal result = MathUtil.percentage(new BigDecimal("100"), new BigDecimal("10"));
        Assertions.assertEquals(result, new BigDecimal("10"));
    }

    @Test
    public void shouldIsBig() {
        boolean result = MathUtil.isBig(new BigDecimal("100"), new BigDecimal("10"));
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldIsBigFirstNumberNull() {
        boolean result = MathUtil.isBig(null, new BigDecimal("10"));
        Assertions.assertFalse(result);
    }

    @Test
    public void shouldIsBigSecondNumberNull() {
        boolean result = MathUtil.isBig(new BigDecimal("100"), null);
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldIsBigThanZero() {
        boolean result = MathUtil.isBigThanZero(new BigDecimal("100"));
        Assertions.assertTrue(result);
    }

}