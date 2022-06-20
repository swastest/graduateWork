package tests.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import preRequest.PreRequestTasks;
import preRequest.PreRequestToken;
import tests.TestBase;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static tests.spec.Specs.response200;

@Tag("task")
@DisplayName("Изменить статус задачи")
public class PostTaskId extends TestBase {
    @DisplayName("Админ меняет статус новой задачи на 'job_is_done'")
    @Test
    void AdminEditingTaskStatusJobIsDone() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "job_is_done")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

    @DisplayName("Менеджер меняет статус новой задачи на 'job_is_done'")
    @Test
    void ManagerEditingTaskStatusJobIsDone() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .queryParam("status", "job_is_done")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

    @DisplayName("Админ меняет статус новой задачи на 'scheduled'")
    @Test
    void AdminEditingTaskStatusScheduled() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "scheduled")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

    @DisplayName("Менеджер меняет статус новой задачи на 'confirmed'")
    @Test
    void ManagerEditingTaskStatusConfirmed() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "confirmed")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }


    @DisplayName("Админ меняет статус новой задачи на 'in_progress'")
    @Test
    void AdminEditingTaskStatusInProgress() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "in_progress")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

    @DisplayName("Техник меняет статус новой задачи на 'in_progress'")
    @Test
    void TechEditingTaskStatusInProgress() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "in_progress")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

    @DisplayName("Техник меняет статус новой задачи на 'job_is_done'")
    @Test
    void TechEditingTaskStatusJobIsDone() {
        Integer idTask = PreRequestTasks.getIdTask();
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .queryParam("status", "job_is_done")
                .pathParam("id", idTask)
                .when().log().all()
                .post("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));
        PreRequestTasks.deleteTask(idTask);
    }

}
