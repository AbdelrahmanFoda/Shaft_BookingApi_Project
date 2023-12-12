package apis.restfulbooker.objects;

import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

public class Apis {
    private SHAFT.API api;

    public Apis(SHAFT.API api) {
        this.api = api;
    }

    public static String baseUrl = System.getProperty("baseUrl");
    public static final int SUCCESS = 200;
    public static final int SUCCESS_DELETE = 201;
    private static final String authentication_serviceName = "/auth";
    public static void login(SHAFT.API api, String username, String password) {
        String tokenBody = """
                {
                    "username" : "{USERNAME}",
                    "password" : "{PASSWORD}"
                }
                """
                .replace("{USERNAME}", username)
                .replace("{PASSWORD}", password);

        api.post(authentication_serviceName)
                .setContentType(ContentType.JSON)
                .setRequestBody(tokenBody)
                .setTargetStatusCode(SUCCESS)
                .perform();

        String token = api.getResponseJSONValue("token");

        api.addHeader("Cookie", "token=" + token);
    }

    public void login(String username, String password) {
        String tokenBody = """
                {
                    "username" : "{USERNAME}",
                    "password" : "{PASSWORD}"
                }
                """
                .replace("{USERNAME}", username)
                .replace("{PASSWORD}", password);

        api.post(authentication_serviceName)
                .setContentType(ContentType.JSON)
                .setRequestBody(tokenBody)
                .setTargetStatusCode(SUCCESS)
                .perform();

        String token = api.getResponseJSONValue("token");

        api.addHeader("Cookie", "token=" + token);
    }

}
