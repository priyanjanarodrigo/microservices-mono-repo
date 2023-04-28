package com.myorg.ps.integration;

import static com.myorg.ps.util.Constants.MONGODB_IMAGE;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@ActiveProfiles({"local", "test"})
@Testcontainers(disabledWithoutDocker = true)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

  @Container
  protected final static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGODB_IMAGE);

  @DynamicPropertySource
  protected static void setPropertiesDynamically(DynamicPropertyRegistry dynamicPropertyRegistry) {
    log.info("Setting up properties dynamically via dynamicPropertyRegistry");
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  @DisplayName("Verifying the status of Test Container(s)")
  protected void testTestContainersStatus() {
    assertTrue(mongoDBContainer.isCreated(), "Mongodb container is expected to be created");
    assertTrue(mongoDBContainer.isRunning(), "Mongodb container should be in running state");
  }
}
