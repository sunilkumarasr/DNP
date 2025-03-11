package com.provizit.dnp.config;

public class Constant {

    public static final String EMAIL_PATTERN = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    // Suppress SonarQube warning for false positive hard-coded password detection
    @SuppressWarnings("squid:S2068") // For SonarLint or the respective rule in SonarQube

    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

}
