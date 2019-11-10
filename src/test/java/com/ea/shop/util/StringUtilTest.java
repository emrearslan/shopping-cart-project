package com.ea.shop.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MathUtil.class)
class StringUtilTest {

    @Test
    public void shouldIsEmpty() {
        boolean result = StringUtil.isEmpty(null);
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldIsEmptyWithEmptyString() {
        boolean result = StringUtil.isEmpty("");
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldIsNotEmpty() {
        boolean result = StringUtil.isNotEmpty("java");
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldLike() {
        String result = StringUtil.like("Java");
        Assertions.assertEquals(result, "%java%");
    }

    @Test
    public void shouldLikeWithoutLowerCase() {
        String result = StringUtil.likeWithoutLowerCase("java");
        Assertions.assertEquals(result, "%java%");
    }


}