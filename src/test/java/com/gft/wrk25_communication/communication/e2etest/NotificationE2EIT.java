package com.gft.wrk25_communication.communication.e2etest;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationE2EIT {

    @LocalServerPort
    private int port;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UUID userId = UUID.fromString("a19bc46f-5f82-4a93-a1cb-7777cbfdb331");

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/notifications";
    }

    @BeforeEach
    void setUpDatabase() {
        jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/data/h2/schema_testing.sql"));
        populator.addScript(new ClassPathResource("/data/h2/data_testing.sql"));
        populator.execute(dataSource);
    }

    @Test
    void testGetNotificationsByUserId_returnsExpectedNotifications() {
        ResponseEntity<NotificationDTO[]> response = restTemplate.getForEntity(
                baseUrl() + "/" + userId,
                NotificationDTO[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Notification> expected = notificationRepository.findAllByUserId(new UserId(userId));
        Assertions.assertNotNull(response.getBody());
        assertEquals(expected.size(), response.getBody().length);
    }


    @Test
    void testPatchNotification_setsImportantSuccessfully() {
        UUID notificationId = UUID.fromString("d2f7e6a1-9a3a-4bce-9f0d-2b8b0c1a1a1a");

        NotificationDTO updateDTO = new NotificationDTO(
                notificationId,
                null,
                userId,
                "irrelevant",
                false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NotificationDTO> request = new HttpEntity<>(updateDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl() + "/" + notificationId,
                HttpMethod.PATCH,
                request,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Notification updated = notificationRepository
                .findAllByUserId(new UserId(userId))
                .stream()
                .filter(n -> n.getId().equals(new NotificationId(notificationId)))
                .findFirst()
                .orElseThrow();

        assertFalse(updated.isImportant());
    }

    @Test
    void testDeleteNotificationRemovesNotificationFromRepository() {
        UUID notificationId = UUID.fromString("d2f7e6a1-9a3a-4bce-9f0d-2b8b0c1a1a1a");

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl() + "/" + notificationId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        boolean exists = notificationRepository.existsById(new NotificationId(notificationId));
        assertFalse(exists);
    }
}
