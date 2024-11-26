import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import static utils.PetHelper.PET;


public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(PET)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    /**
     * Тестовые данные для postPetTest (Positive)
     */
    private static Stream<Arguments> postPetSupplier () {
        return Stream.of(
                Arguments.of(1244, "Marsik", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(12356, "Sharic", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(175454, "Jimm", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(18253, "Andrew", new ArrayList<>(Arrays.asList("url1", "url2")))
        );
    }
    /**
     * Тестовые данные для putPetTest (Positive)
     */
    private static Stream<Arguments> putPetSupplier () {
        return Stream.of(
                Arguments.of(1244, "MaRsik", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(12356, "ShAriC", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(175454, "JiMM", new ArrayList<>(Arrays.asList("url1", "url2"))),
                Arguments.of(18253, "AnDrew", new ArrayList<>(Arrays.asList("url1", "url2")))
        );
    }
}
