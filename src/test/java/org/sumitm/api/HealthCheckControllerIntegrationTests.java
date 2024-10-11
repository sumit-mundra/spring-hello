package org.sumitm.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckControllerIntegrationTests {

    @Autowired
    HealthCheckController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void checkNonNullBean() {
        assertThat(controller).isNotNull();
    }

    @Test
    void simpleHealthCheck_get_allowed() {
        assertTrue(this.restTemplate
                .getForEntity("http://localhost:" + port + "/health?format=short", String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void simpleHealthCheck_getShortFormat_ok() {
        String body = this.restTemplate
                .getForEntity("http://localhost:" + port + "/health?format=short", String.class)
                .getBody();
        assertNotNull(body);
        assertTrue(body.contains("Ok"));
    }

    @Test
    void simpleHealthCheck_getFullFormat_ok() {
        String body = this.restTemplate
                .getForEntity("http://localhost:" + port + "/health?format=full", String.class)
                .getBody();
        assertNotNull(body);
        assertTrue(body.contains("Ok"));
        assertTrue(body.contains(ZonedDateTime.now().toLocalDate().toString()));
    }

    @Test
    void simpleHealthCheck_badFormat_failsWithBadRequest() {
        ResponseEntity<String> result = this.restTemplate
                .getForEntity("http://localhost:" + port + "/health", String.class);
        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void simpleHealthCheck_badFormatFF_failsWithBadRequest() {
        ResponseEntity<String> result = this.restTemplate
                .getForEntity("http://localhost:" + port + "/health?format=ff", String.class);
        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

}