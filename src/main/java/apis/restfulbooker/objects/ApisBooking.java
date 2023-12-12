package apis.restfulbooker.objects;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

public class ApisBooking {
    private SHAFT.API api;
    private final String booking_serviceName = "/booking";
    public ApisBooking(SHAFT.API api) {
        this.api = api;
    }
    @Step("Create Booking with First Name: {firstName} | Last Name: {lastName} | Additional Needs: {additionalNeeds}")
    public void createBooking(String firstName, String lastName, String additionalNeeds) {
        String createBookingBody = """
                {
                    "firstname" : "{FIRST_NAME}",
                    "lastname" : "{LAST_NAME}",
                    "totalprice" : 111,
                    "depositpaid" : true,
                    "bookingdates" : {
                        "checkin" : "2023-01-01",
                        "checkout" : "2024-01-01"
                    },
                    "additionalneeds" : "{ADDITIONAL_NEEDS}"
                }
                """
                .replace("{FIRST_NAME}", firstName)
                .replace("{LAST_NAME}", lastName)
                .replace("{ADDITIONAL_NEEDS}", additionalNeeds);

        api.post(booking_serviceName)
                .setContentType(ContentType.JSON)
                .setRequestBody(createBookingBody)
                .setTargetStatusCode(Apis.SUCCESS)
                .perform();
    }

    @Step("Delete Booking with First Name: {firstName} | Last Name: {lastName}")
    public void deleteBooking(String firstName, String lastName) {
        api.delete(booking_serviceName + "/" + getBookingId(firstName, lastName))
                .setTargetStatusCode(Apis.SUCCESS_DELETE)
                .perform();
    }

    public String getBookingId(String firstName, String lastName) {
        api.get(booking_serviceName)
                .setUrlArguments("firstname=" + firstName + "&lastname=" + lastName)
                .perform();
        return api.getResponseJSONValue("$[0].bookingid");
    }
    public void validateThatTheBookingIsCreated(String expectedFirstName, String expectedLastName, String expectedAdditionalNeeds) {
        api.verifyThatResponse().extractedJsonValue("booking.firstname").isEqualTo(expectedFirstName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.lastname").isEqualTo(expectedLastName).perform();
        api.verifyThatResponse().extractedJsonValue("booking.additionalneeds").isEqualTo(expectedAdditionalNeeds).perform();
        api.verifyThatResponse().body().contains("\"bookingid\":").perform();
    }

    public void validateThatTheBookingDeleted() {
        api.assertThatResponse().body().isEqualTo("Created").perform();
    }

}
