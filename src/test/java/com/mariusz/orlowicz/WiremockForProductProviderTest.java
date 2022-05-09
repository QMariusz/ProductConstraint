package com.mariusz.orlowicz;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.mariusz.orlowicz.utils.CustomFeignException;
import com.mariusz.orlowicz.model.infrastructure.ProductProvider;
import com.mariusz.orlowicz.model.infrastructure.Product;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WiremockForProductProviderTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private ProductProvider productProvider;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @Test
    void should_returnProduct() {
        stubFor(WireMock.get(urlEqualTo("/products/id1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": \"id1\", \"constraintText\": \"OR(WITH_RELATION,IS_RICH)\"}")));

        Product product = productProvider.fetch("id1");
        assertThat(product.getId()).isEqualTo("id1");
        assertThat(product.getConstraintText()).isEqualTo("OR(WITH_RELATION,IS_RICH)");

        verify(getRequestedFor(urlPathEqualTo("/products/id1")));
    }

    @Test
    void should_throwFeignNotFoundException() {
        stubFor(WireMock.get(urlEqualTo("/products/notFound"))
                .willReturn(aResponse()
                        .withStatus(404)));

        Exception exception = assertThrows(CustomFeignException.class, () -> {
            productProvider.fetch("notFound");
        });

        String expectedMessage = "Not Found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(getRequestedFor(urlPathEqualTo("/products/notFound")));
    }

    @Test
    void should_throwFeignBadRequestException() {
        stubFor(WireMock.get(urlEqualTo("/products/bad_request"))
                .willReturn(aResponse()
                        .withStatus(400)));

        Exception exception = assertThrows(CustomFeignException.class, () -> {
            productProvider.fetch("bad_request");
        });

        String expectedMessage = "Bad Request";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        verify(getRequestedFor(urlPathEqualTo("/products/bad_request")));
    }

    @Test
    void should_throwFeignServerError() {
        stubFor(WireMock.get(urlEqualTo("/products/server_error"))
                .willReturn(aResponse()
                        .withStatus(500)));

        Exception exception = assertThrows(RetryableException.class, () -> {
            productProvider.fetch("server_error");
        });

        String expectedMessage = "Server Error";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        verify(getRequestedFor(urlPathEqualTo("/products/server_error")));
    }

    @Test
    void should_throwFeignGatewayTimeout() {
        stubFor(WireMock.get(urlEqualTo("/products/gateway_timeout"))
                .willReturn(aResponse()
                        .withStatus(504)));

        Exception exception = assertThrows(RetryableException.class, () -> {
            productProvider.fetch("gateway_timeout");
        });

        String expectedMessage = "Gateway Timeout";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        verify(getRequestedFor(urlPathEqualTo("/products/gateway_timeout")));
    }
}
