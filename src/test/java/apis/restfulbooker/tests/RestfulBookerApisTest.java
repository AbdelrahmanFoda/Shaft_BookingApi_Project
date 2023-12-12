package apis.restfulbooker.tests;

import apis.restfulbooker.objects.Apis;
import apis.restfulbooker.objects.ApisBooking;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBookerApisTest {
    private SHAFT.API api;
    private ApisBooking apisBooking;

    //////////// Tests \\\\\\\\\\\\
    @Test
    public void createBookingTest() {
        apisBooking.createBooking("Mahmoud", "ElSharkawy", "Metdla3a");
        apisBooking.validateThatTheBookingIsCreated("Mahmoud", "ElSharkawy", "Metdla3a");
    }

    @Test(dependsOnMethods = {"createBookingTest"})
    public void deleteBookingTest() {
        apisBooking.deleteBooking("Mahmoud", "ElSharkawy");
        apisBooking.validateThatTheBookingDeleted();
    }


    /////////// Configurations \\\\\\\\\\\\\\\
    @BeforeClass
    public void beforeClass() {
        api = new SHAFT.API(Apis.baseUrl);

        Apis.login(api, "admin", "password123");
//        Apis apis = new Apis(api);
//        apis.login("admin", "password123");
        apisBooking = new ApisBooking(api);
    }

}
