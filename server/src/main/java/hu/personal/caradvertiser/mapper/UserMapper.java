package hu.personal.caradvertiser.mapper;

import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.model.UserDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserDto> {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
