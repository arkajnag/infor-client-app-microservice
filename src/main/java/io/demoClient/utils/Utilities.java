package io.demoClient.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface Utilities {
	
	public static Supplier<Integer> getRandomData = () -> (int) Math.round(Math.random() * 99999);

    public static UnaryOperator<LocalDateTime> getRandomLocalDateTime = localDateTime -> localDateTime.plusDays(new Random().nextInt((20 - 2 + 1) )+ 1);

    public static UnaryOperator<LocalDateTime> getRandomLocalDateTimeShortRange = localDateTime -> localDateTime.plusDays(new Random().nextInt((10 - 5 + 1) )+ 5);

    public static BinaryOperator<Double> getRandomDoubleWithinRange = (minLimit, maxLimit) -> minLimit + new Random().nextDouble() * (maxLimit - minLimit);

    public static <T> int get_RandomIndexNumber_FromList(List<T> T) {
        return ThreadLocalRandom.current().nextInt(T.size()) % T.size();
    }

}
