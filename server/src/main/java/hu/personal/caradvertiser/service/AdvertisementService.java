package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.mapper.AdvertisementMapper;
import hu.personal.caradvertiser.model.AdRegisterResponseDto;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.model.FilterDto;
import hu.personal.caradvertiser.model.FilterResultDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
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

    public AdvertisementDto getAd(Long id) {
        return advertisementMapper.toDto(advertisementRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    public AdRegisterResponseDto createAd(AdvertisementDto advertisementDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        advertisementDto.setUsername(authentication.getName());
        Advertisement savedAdvertisement = advertisementMapper.toEntity(advertisementDto);
        AdvertisementDto savedAdvertisementDto = advertisementMapper.toDto(advertisementRepository.save(savedAdvertisement));
        return new AdRegisterResponseDto().id(savedAdvertisementDto.getId());
    }

    public AdvertisementDto deleteAd(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (!currentUsername.equals(advertisement.getUsername())) {
            throw new IllegalArgumentException();
        }
        advertisementRepository.delete(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    public FilterResultDto search(FilterDto filterDto) {
        return advertisementSearchService.search(filterDto);
    }
}

