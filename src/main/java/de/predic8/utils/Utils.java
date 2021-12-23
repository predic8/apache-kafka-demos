package de.predic8.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int randInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }
}
