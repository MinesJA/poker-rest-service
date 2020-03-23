package com.minesja.poker.pokerrestservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class CardControllerTests {

    @Autowired
    private CardController controller;

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldReturn200WhenRequestingRandomCard() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/cards/random", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestingSpecificCard() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/cards", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
