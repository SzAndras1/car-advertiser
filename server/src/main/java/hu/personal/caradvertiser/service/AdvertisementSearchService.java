package hu.personal.caradvertiser.service;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.mapper.AdvertisementMapper;
import hu.personal.caradvertiser.model.AdvertisementDto;
import hu.personal.caradvertiser.model.FilterDto;
import hu.personal.caradvertiser.model.FilterResultDto;
import hu.personal.caradvertiser.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdvertisementSearchService {
    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementMapper advertisementMapper;

    @Transactional
    public FilterResultDto search(FilterDto filterDto) {
        int page = Objects.isNull(filterDto.getPage()) ? 0 : filterDto.getPage();
        int pageSize = 10;
        Page<Advertisement> ads = advertisementRepository.search(
                filterDto.getBrand(),
                filterDto.getType(),
                filterDto.getPrice(),
                PageRequest.of(page, pageSize)
        );
        List<AdvertisementDto> advertisementDtoList = ads.get()
                .map(advertisementMapper::toDto)
                .collect(Collectors.toList());
        return new FilterResultDto()
                .page(page)
                .pageSize(pageSize)
                .ads(advertisementDtoList)
                .totalElements(ads.getTotalElements());
    }
}
