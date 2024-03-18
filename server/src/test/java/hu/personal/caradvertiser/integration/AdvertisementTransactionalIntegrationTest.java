package hu.personal.caradvertiser.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.fixtures.AdvertisementFixtures;
import hu.personal.caradvertiser.mapper.AdvertisementMapper;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.model.FilterDto;
import hu.personal.caradvertiser.model.FilterResultDto;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdvertisementTransactionalIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @WithMockUser(username = "testUser")
    @Test
    void searchShouldReturnWithTheCorrectResult() throws Exception {
        userRepository.save(new User(0L, "testUser", "testEmail", "testPassword"));
        FilterDto filterDto = new FilterDto().brand("test");
        List<AdvertisementDto> savedAdvertisements = List.of(
                advertisementMapper.toDto(advertisementRepository.save(AdvertisementFixtures.simpleAdvertisement(15L))),
                advertisementMapper.toDto(advertisementRepository.save(AdvertisementFixtures.simpleAdvertisement(16L)))
        );
        FilterResultDto filterResultDto = new FilterResultDto()
                .page(0)
                .pageSize(10)
                .ads(savedAdvertisements)
                .totalElements((long) savedAdvertisements.size());

        mockMvc.perform(get("/api/v1/ad/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(filterDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(filterResultDto)));
    }
}
