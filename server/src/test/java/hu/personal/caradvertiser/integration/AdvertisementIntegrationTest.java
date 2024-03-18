package hu.personal.caradvertiser.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.fixtures.AdvertisementFixtures;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import hu.personal.caradvertiser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdvertisementIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @WithMockUser(username = "testUser")
    @Test
    void getAdShouldReturnWithTheCorrectResult() throws Exception {
        Long id = 15L;
        userRepository.save(new User(0L, "testUser", "testEmail", "testPassword"));
        Advertisement savedAdvertisement = advertisementRepository.save(AdvertisementFixtures.simpleAdvertisement(id));

        mockMvc.perform(get("/api/v1/ad/{id}", savedAdvertisement.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is("testBrand")))
                .andExpect(jsonPath("$.type", is("testType")))
                .andExpect(jsonPath("$.description", is("testDescription")))
                .andExpect(jsonPath("$.price", is(100)));
    }

    @WithMockUser(username = "testUser")
    @Test
    void createAdShouldReturnWithTheCorrectResult() throws Exception {
        Long id = 15L;
        userRepository.save(new User(0L, "testUser", "testEmail", "testPassword"));
        Advertisement advertisement = AdvertisementFixtures.simpleAdvertisement(id);
        AdvertisementDto advertisementDto = AdvertisementFixtures.simpleAdvertisementDto(id);

        mockMvc.perform(post("/api/v1/ad")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(advertisementDto)))
                .andExpect(status().isCreated());

        Optional<Advertisement> result = advertisementRepository.findById(id);
        assertTrue(result.isPresent());
        assertThat(result.get(), is(advertisement));
    }

    @WithMockUser(username = "testUser")
    @Test
    void deleteAdShouldReturnWithTheCorrectResult() throws Exception {
        Long id = 15L;
        userRepository.save(new User(0L, "testUser", "testEmail", "testPassword"));
        Advertisement advertisement = AdvertisementFixtures.simpleAdvertisement(id);
        advertisementRepository.save(advertisement);

        mockMvc.perform(delete("/api/v1/ad/{id}", id))
                .andExpect(status().isOk());

        Optional<Advertisement> result = advertisementRepository.findById(id);
        assertFalse(result.isPresent());
    }

}
