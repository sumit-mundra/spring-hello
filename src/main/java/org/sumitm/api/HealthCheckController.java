package org.sumitm.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RequestMapping("/health")
@RestController
public class HealthCheckController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> healthCheck(@RequestParam(name = "format") String format) {
        HealthCheckResponse res = null;
        HealthCheckFormat formaEnum = null;
        try {
            formaEnum = Enum.valueOf(HealthCheckFormat.class, format.toUpperCase());
        } catch (
                IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        switch (formaEnum) {
            case FULL -> {
                res = HealthCheckResponse.fullResponse();
            }
            case SHORT -> {
                res = HealthCheckResponse.shortResponse();
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + formaEnum);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum HealthCheckFormat {
        SHORT("short"),
        FULL("full");

        private final String format;

        HealthCheckFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class HealthCheckResponse {
        private final String response;

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private final ZonedDateTime date;

        private HealthCheckResponse(String response, ZonedDateTime date) {
            this.response = response;
            this.date = date;
        }

        public static HealthCheckResponse fullResponse() {
            return new HealthCheckResponse("Ok", ZonedDateTime.now());
        }

        public static HealthCheckResponse shortResponse() {
            return new HealthCheckResponse("Ok", null);
        }

        public String getResponse() {
            return response;
        }

        public ZonedDateTime getDate() {
            return date;
        }
    }

}
