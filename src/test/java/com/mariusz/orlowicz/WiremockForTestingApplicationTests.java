package com.mariusz.orlowicz;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.mariusz.orlowicz.infrastructure.ProductProvider;
import com.mariusz.orlowicz.model.Product;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WiremockForTestingApplicationTests {

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
    void firstTest() {
        stubFor(WireMock.get(urlEqualTo("/products/id1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": \"id1\", \"constraintText\": \"OR(WITH_RELATION,IS_RICH)\"}")));

        Product a = productProvider.fetch("id1");
        assertThat(a.getId()).isEqualTo("id1");
        assertThat(a.getConstraintText()).isEqualTo("OR(WITH_RELATION,IS_RICH)");

        verify(getRequestedFor(urlPathEqualTo("/products/id1")));
    }

    //Tests for error codes

    @Test
    @Ignore
    void error() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/products/notFound"))
                .willReturn(aResponse()
                        .withStatus(404)));

        Product a = productProvider.fetch("notFound");
        assertThat(a.getId()).isEqualTo("sad");
        assertThat(a.getConstraintText()).isEqualTo("sad");

        verify(getRequestedFor(urlPathEqualTo("/products/a")));
    }
}
