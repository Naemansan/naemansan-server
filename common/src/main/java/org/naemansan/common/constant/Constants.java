package org.naemansan.common.constant;

import java.util.List;

public class Constants {
    public static final Long PRESIGNED_URL_EXPIRATION_MINUTES = 3L;
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String IMAGE_CONTENT_PREFIX = "image";
    public static String PROFILE_PREFIX = "profile";
    public static String COURSE_IMAGE_PREFIX = "course-thumbnail";
    public static String SPOT_IMAGE_PREFIX = "moment-image";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/guest/**",
            "/auth/sign-in",
            "/auth/sign-up",
            "/oauth2/authorization/kakao",
            "/oauth2/authorization/google",
            "/login/oauth2/code/kakao",
            "/login/oauth2/code/google",

            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**");

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
}
