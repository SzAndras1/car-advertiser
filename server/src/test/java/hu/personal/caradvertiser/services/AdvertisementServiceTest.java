package hu.personal.caradvertiser.services;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.fixtures.AdvertisementFixtures;
import hu.personal.caradvertiser.mapper.AdvertisementMapperImpl;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import hu.personal.caradvertiser.repository.UserRepository;
import hu.personal.caradvertiser.service.AdvertisementSearchService;
import hu.personal.caradvertiser.service.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
    AdvertisementService advertisementService;
    @Mock
    AdvertisementRepository advertisementRepository;

    @Mock
    AdvertisementSearchService advertisementSearchService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        advertisementService = new AdvertisementService(advertisementRepository, new AdvertisementMapperImpl(), advertisementSearchService, userRepository);
    }

    @Test
    public void getAdShouldReturnWithTheCorrectResult() {
        Long id = 1L;
        Advertisement advertisement = AdvertisementFixtures.simpleAdvertisement(id);
        AdvertisementDto expected = AdvertisementFixtures.simpleAdvertisementDto(id);
        given(advertisementRepository.findById(id)).willReturn(Optional.of(advertisement));

        AdvertisementDto result = advertisementService.getAd(id);

        assertThat(result, is(equalTo(expected)));
    }
}
