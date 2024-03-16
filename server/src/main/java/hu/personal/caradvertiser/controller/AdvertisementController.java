package hu.personal.caradvertiser.controller;

import hu.personal.caradvertiser.CaradvertiserApi;
import hu.personal.caradvertiser.model.AdRegisterResponseDto;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AdvertisementController implements CaradvertiserApi {
    private final AdvertisementService advertisementService;
    public static final String ADVERTISING_API_PATH = "/api/advertising/v1/advertisement";

    @Override
    public ResponseEntity<AdvertisementDto> getAd(Long id) {
        return ResponseEntity.ok(advertisementService.getAd(id));
    }

    @Override
    public ResponseEntity<AdRegisterResponseDto> createAd(AdvertisementDto advertisementDto) {
        AdRegisterResponseDto responseDto = advertisementService.createAd(advertisementDto);

        URI location = ServletUriComponentsBuilder
                .fromPath(ADVERTISING_API_PATH)
                .path("/{id}")
                .buildAndExpand(advertisementDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @Override
    public ResponseEntity<AdvertisementDto> deleteAd(Long id) {
        return ResponseEntity.ok(advertisementService.deleteAd(id));
    }
}
