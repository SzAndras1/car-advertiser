package hu.personal.caradvertiser.fixtures;

import hu.personal.caradvertiser.entity.Advertisement;
import hu.personal.caradvertiser.entity.User;
import hu.personal.caradvertiser.model.AdvertisementDto;

public class AdvertisementFixtures {
    public static Advertisement simpleAdvertisement(Long id) {
        return new Advertisement(id, new User(0L, "testUser", "testEmail", "testPassword"), "testBrand", "testType", "testDescription", 100L);
    }

    public static AdvertisementDto simpleAdvertisementDto(Long id) {
        return new AdvertisementDto()
                .id(id)
                .brand("testBrand")
                .type("testType")
                .description("testDescription")
                .price(100L);
    }
}
