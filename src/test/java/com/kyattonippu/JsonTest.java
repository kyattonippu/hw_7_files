package com.kyattonippu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonTest {

    private final ClassLoader cl = JsonTest.class.getClassLoader();

    @Test
    @DisplayName("Парсинг JSON файла")
    void jsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("Client.json"); InputStreamReader isr = new InputStreamReader(is)) {
            Client client = objectMapper.readValue(isr, Client.class);

            Assertions.assertEquals("Maya", client.firstName);
            Assertions.assertEquals("Sivkova", client.lastName);
            Assertions.assertEquals("woman", client.gender);
            Assertions.assertEquals(29, client.age);
            Assertions.assertEquals("Russia", client.address.country);
            Assertions.assertEquals("Irkutsk area", client.address.region);
            Assertions.assertEquals("Bratsk", client.address.city);
            Assertions.assertEquals("123456", client.address.postalCode);
            Assertions.assertEquals(List.of("0987654321", "kyattonippu"), client.contacts);
        }
    }
}
