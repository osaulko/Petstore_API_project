import io.qameta.allure.*;
import models.Pet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import io.restassured.module.jsv.JsonSchemaValidator;
import utils.PetHelper;
import java.io.File;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class PetTests extends BaseTest {
    /** Тест postPetTest покрывает 12p001 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Создание питомца")
    @Story("Позитивный сценарий создания питомца")
    @Description("Тест покрывает 12p001 кейс - Создание нового питомца")
    @ParameterizedTest
    @MethodSource("BaseTest#postPetSupplier")
    public void postPetTest(Integer id, String name, ArrayList<String> photoUrls) {
        Allure.step("Создание нового питомца", () -> {
            Allure.parameter("ID питомца", id);
            Allure.parameter("Имя питомца", name);
            Pet pet = PetHelper.getPet(id, name, photoUrls);

                    Allure.step("Отправка POST-запроса на создание питомца", () -> {
                PetHelper.postPetResponse(pet)
                        .statusCode(SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
            });

            Allure.step("Проверка созданного питомца", () -> {
                io.restassured.response.ValidatableResponse petTest = PetHelper.getPetTest(String.valueOf(id));
                petTest.body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
            });
        });
    }
    /** Тест putPetTest покрывает 12p002 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Обновление питомца")
    @Story("Позитивный сценарий обновления питомца")
    @Description("Тест покрывает 12p002 кейс - Обновление информации о питомце")
    @ParameterizedTest
    @MethodSource("BaseTest#putPetSupplier")
    public void putPetTest(Integer id, String name, ArrayList<String> photoUrls) {
        Allure.step("Обновление информации о питомце", () -> {
            Allure.parameter("ID питомца", id);
            Allure.parameter("Новое имя питомца", name);

            Pet pet = PetHelper.getPet(id, name, photoUrls);

            Allure.step("Отправка PUT-запроса на обновление питомца", () -> {
                PetHelper.putPetResponse(pet)
                        .statusCode(SC_OK)
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
            });
        });
    }
    /** Тест postUploadImageTest покрывает 12p003 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Загрузка изображения")
    @Story("Позитивный сценарий загрузки изображения")
    @Description("Тест покрывает 12p003 кейс - Загрузка изображения для питомца")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/PostUploadImageData.csv", numLinesToSkip = 1)
    public void postUploadImageTest(Integer id, String fileName) {
        Allure.step("Загрузка изображения для питомца", () -> {
            Allure.parameter("ID питомца", id);
            Allure.parameter("Имя файла", fileName);

            Allure.step("Отправка запроса на загрузку изображения", () -> {
                PetHelper.postUploadImageTest(id, new File(fileName).getAbsolutePath())
                        .statusCode(SC_OK)
                        .body(JsonSchemaValidator
                                .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
            });
        });
    }
    /** Тест getPetTest покрывает 12p004 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Получение питомца")
    @Story("Позитивный сценарий получения питомца по ID")
    @Description("Тест покрывает 12p004 кейс - Получение питомца по идентификатору")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/GetPetData.csv", numLinesToSkip = 1)
    void getPetTest(String id) {
        Allure.step("Получение информации о питомце", () -> {
            Allure.parameter("ID питомца", id);

            Allure.step("Отправка GET-запроса для получения питомца", () -> {
                io.restassured.response.ValidatableResponse petTest = PetHelper.getPetTest(id);
                petTest.statusCode(SC_OK)
                        .body(JsonSchemaValidator
                                .matchesJsonSchemaInClasspath("Pet/Schemes/PetScheme.json"));
            });
        });
    }
    /** Тест getFindByStatusTest покрывает 12p005 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Поиск питомцев по статусу")
    @Story("Позитивный сценарий поиска питомцев")
    @Description("Тест покрывает 12p005 кейс - Получение питомцев по статусу")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/GetFindByStatusData.csv", numLinesToSkip = 1)
    void getFindByStatusTest(String status) {
        Allure.step("Поиск питомцев по статусу", () -> {
            Allure.parameter("Статус", status);

            Allure.step("Отправка GET-запроса для поиска питомцев", () -> {
                PetHelper.getFindByStatus(status)
                        .statusCode(SC_OK)
                        .body(JsonSchemaValidator
                                .matchesJsonSchemaInClasspath("pet/Schemes/FindByStatusScheme.json"));
            });
        });
    }
    /** Тест postUpdatePetByFormDataTest покрывает 12p006 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Обновление питомца с помощью формы")
    @Story("Позитивный сценарий обновления питомцев")
    @Description("Тест покрывает 12p006 кейс - Обновление питомцев по форме")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/PostPetWithFormData.csv", numLinesToSkip = 1)
    void postUpdatePetByFormDataTest (Integer id, String name, String status) {
        Allure.step("Обновление информации о питомце с помощью формы", () -> {
                    Allure.parameter("ID питомца", id);
                    Allure.parameter("Новое имя питомца", name);
                    Allure.parameter("Новый статус питомца", status);

            Allure.step("Отправка POST-запроса для изменения питомцев", () -> {
                    PetHelper.postUpdatePetByFormDataTest(id, name, status)
                            .statusCode(SC_OK)
                            .body(JsonSchemaValidator
                                    .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
            });
        });
    }
    /** Тест deletePetTest покрывает 12p007 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Удаление питомца")
    @Story("Позитивный сценарий удаления питомца")
    @Description("Тест покрывает 12p007 кейс - Удаление питомца по ID")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Positive/DeletePetData.csv", numLinesToSkip = 1)
    void deletePetTest(Integer id) {
        Allure.step("Удаление питомца", () -> {
            Allure.parameter("ID питомца", id);
            PetHelper.deletePetTest(id)
                    .statusCode(SC_OK)
                    .body(JsonSchemaValidator
                            .matchesJsonSchemaInClasspath("pet/Schemes/BasicResponseScheme.json"));
        });
    }
    /** Тест getPetTest покрывает 12p008 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Получение питомца")
    @Story("Негативный сценарий получения питомца по ID")
    @Description("Тест покрывает 12p008 кейс - Получение питомца по некорректному ID")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/GetPetData.csv", numLinesToSkip = 1)
    void getPetTestNegative(String id) {
        Allure.step("Попытка получения несуществующего питомца", () -> {
            Allure.parameter("Некорректный ID", id);
            PetHelper.getPetTest(id).statusCode(SC_NOT_FOUND)
                    .body(JsonSchemaValidator
                            .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
        });
    }
    /** Тест getFindByStatusTest покрывает 12p009 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Поиск питомцев по статусу")
    @Story("Негативный сценарий поиска питомцев")
    @Description("Тест покрывает 12p009 кейс - Поиск питомцев по некорректному статусу")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/GetFindByStatusData.csv", numLinesToSkip = 1)
    void getFindByStatusNegativeTest(String status) {
        Allure.step("Попытка поиска питомцев по некорректному статусу", () -> {
            Allure.parameter("Некорректный статус", status);
            PetHelper.getFindByStatus(status)
                    .statusCode(SC_OK)
                    .body(JsonSchemaValidator
                            .matchesJsonSchemaInClasspath("pet/Schemes/FindByStatusScheme.json"));
        });
    }
    /** Тест postUploadImageTest покрывает 12p010 кейс.
     *  В этом тесте проверяю код состояния и тело ответа.
     */
    @Epic("Pet API")
    @Feature("Загрузка изображения")
    @Story("Негативный сценарий загрузки изображения")
    @Description("Тест покрывает 12p010 кейс - Загрузка изображения для несуществующего питомца")
    @ParameterizedTest
    @CsvFileSource(resources = "Pet/test_data/Negative/PostUploadImageData.csv", numLinesToSkip = 1)
    public void postUploadImageNegativeTest(Integer id, String fileName) {
        Allure.step("Попытка загрузки изображения для несуществующего питомца", () -> {
            Allure.parameter("ID питомца", id);
            Allure.parameter("Имя файла", fileName);
            PetHelper.postUploadImageTest(id, new File(fileName).getAbsolutePath())
                    .statusCode(SC_OK)
                    .body(JsonSchemaValidator
                            .matchesJsonSchemaInClasspath("Pet/Schemes/BasicResponseScheme.json"));
        });
    }
}