package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.mapper.AdvertisementMapper;
import hu.personal.caradvertiser.model.AdRegisterResponseDto;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.model.FilterDto;
import hu.personal.caradvertiser.model.FilterResultDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import hu.personal.caradvertiser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementMapper advertisementMapper;

    private final AdvertisementSearchService advertisementSearchService;

    private final UserRepository userRepository;

    public AdvertisementDto getAd(Long id) {
        return advertisementMapper.toDto(advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no item with this id.")));
    }

    public AdRegisterResponseDto createAd(AdvertisementDto advertisementDto) {
        Advertisement savedAdvertisement = advertisementMapper.toEntity(advertisementDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(IllegalArgumentException::new);
        savedAdvertisement.setUser(user);
        AdvertisementDto savedAdvertisementDto = advertisementMapper.toDto(advertisementRepository.save(savedAdvertisement));
        return new AdRegisterResponseDto().id(savedAdvertisementDto.getId());
    }

    public AdvertisementDto deleteAd(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no item with this id."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (!currentUsername.equals(advertisement.getUser().getUsername())) {
            throw new IllegalArgumentException("You cannot delete another User's Advertisement.");
        }
        advertisementRepository.delete(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    public FilterResultDto search(FilterDto filterDto) {
        return advertisementSearchService.search(filterDto);
    }
}

