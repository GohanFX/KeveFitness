package hu.gohanfx.kevefitness.kevefitness.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class KeyGenerator {

    public String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
