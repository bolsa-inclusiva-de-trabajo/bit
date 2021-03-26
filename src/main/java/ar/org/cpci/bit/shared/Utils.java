package ar.org.cpci.bit.shared;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Utils {

    private static final Map<String, String> env = System.getenv();

    private Utils() {
        throw new IllegalStateException("Utils class");
    }

    public static int getIntFromEnv(String key, String value) {
        return Integer.parseInt(env.getOrDefault(key, value));
    }

    public static String getAuthSuccessUrl(boolean isEmployer) {
        return isEmployer ? "/bagapplicants" : "/bagoffers";
    }

    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
