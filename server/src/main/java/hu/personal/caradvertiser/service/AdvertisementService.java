package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.exception.EntityNotFoundException;
import hu.personal.caradvertiser.exception.NotValidException;
import hu.personal.caradvertiser.exception.OtherUserEntityException;
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

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementMapper advertisementMapper;

    private final AdvertisementSearchService advertisementSearchService;

    private final UserRepository userRepository;

    public AdvertisementDto getAd(Long id) {
        return advertisementMapper.toDto(advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id", "There is no item with this id.")));
    }

    public AdRegisterResponseDto createAd(AdvertisementDto advertisementDto) {
        validateAdvertisement(advertisementDto);
        Advertisement savedAdvertisement = advertisementMapper.toEntity(advertisementDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(IllegalArgumentException::new);
        savedAdvertisement.setUser(user);
        AdvertisementDto savedAdvertisementDto = advertisementMapper.toDto(advertisementRepository.save(savedAdvertisement));
        return new AdRegisterResponseDto().id(savedAdvertisementDto.getId());
    }

    public AdvertisementDto deleteAd(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("id", "There is no item with this id."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (!currentUsername.equals(advertisement.getUser().getUsername())) {
            throw new OtherUserEntityException("id", "You cannot delete another User's Advertisement.");
        }
        advertisementRepository.delete(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    public FilterResultDto search(FilterDto filterDto) {
        return advertisementSearchService.search(filterDto);
    }

    private void validateAdvertisement(AdvertisementDto advertisementDto) {
        String brand = advertisementDto.getBrand();
        String type = advertisementDto.getType();
        String description = advertisementDto.getDescription();
        Long price = advertisementDto.getPrice();
        List<String> wrongFields = new LinkedList<>();
        if (brand.length() < 2 || brand.length() > 20) {
            wrongFields.add("brand");
        }
        if (type.length() < 2 || type.length() > 20) {
            wrongFields.add("type");
        }
        if (description.length() < 2 || description.length() > 200) {
            wrongFields.add("description");
        }
        if (price > 9999999999L) {
            wrongFields.add("price");
        }
        if (wrongFields.size() > 0) {
            throw new NotValidException(String.join(", ", wrongFields), "Data not provided correctly");
        }
    }
}

