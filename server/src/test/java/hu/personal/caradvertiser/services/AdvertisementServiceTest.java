package hu.personal.caradvertiser.services;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.fixtures.AdvertisementFixtures;
import hu.personal.caradvertiser.mapper.AdvertisementMapperImpl;
import hu.personal.caradvertiser.model.AdRegisterResponseDto;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import hu.personal.caradvertiser.repository.UserRepository;
import hu.personal.caradvertiser.service.AdvertisementSearchService;
import hu.personal.caradvertiser.service.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @Test
    public void createAdShouldReturnWithTheCorrectResult() {
        Long id = 1L;
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Advertisement advertisement = AdvertisementFixtures.simpleAdvertisement(id);
        AdvertisementDto request = AdvertisementFixtures.simpleAdvertisementDto(id);
        when(userRepository.findByUsername(advertisement.getUser().getUsername())).thenReturn(Optional.of(advertisement.getUser()));
        when(securityContext.getAuthentication().getName()).thenReturn(advertisement.getUser().getUsername());
        given(advertisementRepository.save(advertisement)).willReturn(advertisement);

        AdRegisterResponseDto result = advertisementService.createAd(request);

        assertThat(result, is(equalTo(new AdRegisterResponseDto().id(id))));
    }
}
