package com.culinary.userservice.security.configuration;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

@Tag("test")
@TestConfiguration
@EnableConfigurationProperties
@ActiveProfiles("test")
public class TestConfig {

}
