package utils;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.Pet;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static config.PropertyReader.properties;

public class PetHelper {
    static String URL = properties.getProperty("url");

    @NotNull
    public static String PET = URL + "/pet",
            FIND_BY_STATUS = PET + "/findByStatus";

    public static ValidatableResponse postPetResponse(Pet pet) {
        return
                given()
                        .body(pet)
                        .when()
                        .post(PET)
                        .then()
                        .log().ifValidationFails(LogDetail.ALL);
    }

    public static ValidatableResponse putPetResponse(Pet pet) {
        return
                given()
                        .body(pet)
                        .when()
                        .put(PET)
                        .then()
                        .log().body();
    }

    public static ValidatableResponse postUploadImageTest(Integer id, String file) {
        return
                given()
                        .contentType(ContentType.MULTIPART)
                        .multiPart("file", file)
                        .when()
                        .post(PET + "/" + id.toString() + "/" + "uploadImage")
                        .then()
                        .log().ifValidationFails(LogDetail.ALL);
    }

    public static ValidatableResponse getPetTest(String id) {
        return
                given()
                        .header("api_key", "special-key")
                        .when()
                        .get(PET + "/" + id)
                        .then()
                        .log().ifValidationFails(LogDetail.ALL);
    }

    public static ValidatableResponse getFindByStatus (String status) {
        return
                given()
                        .queryParam("status", status)
                        .when()
                        .get(FIND_BY_STATUS)
                        .then()
                        .log().all();
    }

    public static ValidatableResponse postUpdatePetByFormDataTest (Integer id, String name, String status) {
        return
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("name", name)
                        .formParam("status", status)
                        .when()
                        .post(PET + "/" + id.toString())
                        .then()
                        .log().ifValidationFails(LogDetail.ALL);
    }

    public static ValidatableResponse deletePetTest (Integer id) {
        return
                when()
                        .delete(PET + "/" + id.toString())
                        .then()
                        .log().ifValidationFails(LogDetail.ALL);
    }

}
