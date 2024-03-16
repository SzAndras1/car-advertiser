package hu.personal.caradvertiser.controller;

import hu.personal.caradvertiser.CaradvertiserApi;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdvertisementController implements CaradvertiserApi {
    private final AdvertisementService advertisementService;
    @Override
    public ResponseEntity<AdvertisementDto> getAd(Long id) {
        return null;
    }
}
