import models.Pet;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import io.restassured.module.jsv.JsonSchemaValidator;
import utils.PetHelper;
import java.io.File;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_OK;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PositivePetTest extends BaseTest {

    /** Тест postPetTest покрывает 12p001 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(1)
    @ParameterizedTest
    @MethodSource("BaseTest#postPetSupplier")
    public void postPetTest (Integer id, String name, ArrayList<String> photoUrls){
        Pet pet = Pet.getPet(id, name, photoUrls);
        PetHelper.postPetResponse(pet)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
    }
    /** Тест putPetTest покрывает 12p002 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(2)
    @ParameterizedTest
    @MethodSource("BaseTest#putPetSupplier")
    public void putPetTest (Integer id, String name, ArrayList<String> photoUrls) {
        Pet pet = Pet.getPet(id, name, photoUrls);

        PetHelper.putPetResponse(pet)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
    }
    /** Тест postUploadImageTest покрывает 12p003 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/PostUploadImageData.csv", numLinesToSkip = 1)
    public void postUploadImageTest (Integer id, String fileName) {
        PetHelper.postUploadImageTest(id, new File(fileName).getAbsolutePath())
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
    }
    /** Тест getPetTest покрывает 12p004 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(4)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/GetPetData.csv", numLinesToSkip = 1)
    void getPetTest(String id) {
        io.restassured.response.ValidatableResponse petTest = PetHelper.getPetTest(id);
        petTest.statusCode(SC_OK)
                .body(JsonSchemaValidator
                .matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
    }
    /** Тест getFindByStatusTest покрывает 12p005 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(5)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/GetFindByStatusData.csv", numLinesToSkip = 1)
    void getFindByStatusTest(String status) {
        PetHelper.getFindByStatus(status)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("pet/Schemes/FindByStatusScheme.json"));
    }
    /** Тест postUpdatePetByFormDataTest покрывает 12p006 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(6)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/PostPetWithFormData.csv", numLinesToSkip = 1)
    void postUpdatePetByFormDataTest (Integer id, String name, String status) {
        PetHelper.postUpdatePetByFormDataTest(id, name, status)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
    }
    /** Тест deletePetTest покрывает 12p007 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(7)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/DeletePetData.csv", numLinesToSkip = 1)
    void deletePetTest (Integer id) {
        PetHelper.deletePetTest(id)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("pet/Schemes/BasicResponseScheme.json"));
    }
}