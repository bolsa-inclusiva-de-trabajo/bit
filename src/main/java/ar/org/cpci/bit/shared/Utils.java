package ar.org.cpci.bit.shared;

import java.util.Map;

public final class Utils {

    private static final Map<String, String> env = System.getenv();

    private Utils() {
        throw new IllegalStateException("Utils class");
    }

    public static int getIntFromEnv(String key, String value) {
        return Integer.parseInt(env.getOrDefault(key, value));
    }

}
