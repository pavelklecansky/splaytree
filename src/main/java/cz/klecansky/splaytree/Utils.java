package cz.klecansky.splaytree;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Utils {

    public static String generateProductId() {
        StringBuilder builder = new StringBuilder();

        builder.append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomStringUtils.randomAlphabetic(3))
                .append("-")
                .append(RandomUtils.nextInt(1, 10))
                .append(RandomUtils.nextInt(1, 10));

        return builder.toString();
    }
}
