package com.learn.customersvc.restassured;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
@Disabled
class RestAssuredIntegrationTests {


    @BeforeAll
    public static void setup() {
        baseURI = "http://localhost:8080";
    }



  /*  @Test
    public void testGetCustomerResponseBodyAndStatus() {
        given()
                .baseUri(baseURI).
                pathParam("id", "1").
                when()
                .get("/api-customer/v1/customer/{id}",1).
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("id", hasItems(1, 2),
                        "name", hasItems("User-1", "User-2"),
                        "role", hasItems("Admin", "Supervisor"),
                        "id[0]", equalTo(1),
                        "name[0]", is(equalTo("User-1")),
                        "size()", equalTo(2)
                );
    }*/


    @Test
    @Order(2)
    public void testGetCustomerResponseBodyAndStatusWithExtract() {
        Response response = given()
                .baseUri(baseURI).
                pathParam("id", "1").
                when()
                .get("/api-customer/v1/customer/{id}").
                then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();

        JsonPath jsonPathObj = response.jsonPath();
        Assertions.assertEquals(jsonPathObj.getLong("data.customerId"), Long.valueOf(1));
    }

    @Test
    @Order(1)
    public void testPostCustomer() {

        String inputJson = """
                {
                            "givenName": "Pooja",
                            "surName": "Santhosh",
                            "gender": "MALE",
                            "dob":  "1980-12-29"  
                }
                                
                """;

        Response response = given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body(inputJson.toString())
                .log().all()
                .when()
                .post("/api-customer/v1/customer").
                then().
                log().all().
                assertThat().
                statusCode(201).
                extract().
                response();

        JsonPath jsonPathObj = response.jsonPath();
        Assertions.assertEquals(jsonPathObj.getString("data.givenName"), "Pooja");
        Assertions.assertEquals(jsonPathObj.getString("data.surName"), "Santhosh");
        Assertions.assertEquals(jsonPathObj.getString("data.gender"), "MALE");
    }


}
