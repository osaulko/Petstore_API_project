import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.PetHelper;
import java.io.File;
import static org.apache.http.HttpStatus.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NegativePetTest extends BaseTest {

    /** Тест getPetTest покрывает 12p008 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/GetPetData.csv", numLinesToSkip = 1)
    void getPetTest (String id) {
        PetHelper.getPetTest(id)
                .statusCode(SC_NOT_FOUND)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
    }
    /** Тест getFindByStatusTest покрывает 12p009 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/GetFindByStatusData.csv", numLinesToSkip = 1)
    void getFindByStatusTest (String status) {
        PetHelper.getFindByStatus(status)
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("pet/Schemes/FindByStatusScheme.json"));
    }
    /** Тест postUploadImageTest покрывает 12p010 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/PostUploadImageData.csv", numLinesToSkip = 1)
    public void postUploadImageTest (Integer id, String fileName) {
        PetHelper.postUploadImageTest(id, new File(fileName).getAbsolutePath())
                .statusCode(SC_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
    }
}