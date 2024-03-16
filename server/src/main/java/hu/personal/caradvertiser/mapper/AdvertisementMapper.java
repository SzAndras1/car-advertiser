package hu.personal.caradvertiser.mapper;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.model.AdvertisementDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper extends Converter<Advertisement, AdvertisementDto> {
    Advertisement toEntity(AdvertisementDto advertisementDto);
    AdvertisementDto toDto(Advertisement advertisement);
}
