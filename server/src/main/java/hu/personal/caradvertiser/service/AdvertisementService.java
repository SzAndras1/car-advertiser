package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.mapper.AdvertisementMapper;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementMapper advertisementMapper;
}

