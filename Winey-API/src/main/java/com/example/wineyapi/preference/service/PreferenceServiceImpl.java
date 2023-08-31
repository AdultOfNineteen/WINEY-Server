package com.example.wineyapi.preference.service;

import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineydomain.common.model.Chocolate;
import com.example.wineydomain.common.model.Coffee;
import com.example.wineydomain.common.model.Fruit;
import com.example.wineydomain.preference.entity.Preference;
import com.example.wineydomain.preference.repository.PreferenceRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BiConsumer;


@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    private Preference newPreference(User user) {
        Preference preference = Preference.builder()
                .sweetness(0)
                .acidity(0)
                .body(0)
                .tannins(0)
                .build();

        preference.setUser(user);
        return preferenceRepository.save(preference);
    }

    @Transactional
    @Override
    public Preference update(Long userId, PreferenceRequest.UpdatePreferenceDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(CommonResponseStatus.NOT_EXIST_USER));
        Preference preference = Optional.ofNullable(user.getPreference())
                .orElseGet(() -> newPreference(user));

        updatePreference(request.getChocolate(), preference, this::setChocolateValues);
        updatePreference(request.getCoffee(), preference, this::setCoffeeValues);
        updatePreference(request.getFruit(), preference, this::setFruitValues);

        return preference;
    }

    private <T> void updatePreference(T item, Preference preference, BiConsumer<T, Preference> updater) {
        if (item == null) return;
        updater.accept(item, preference);
    }

    private void setChocolateValues(Chocolate chocolate, Preference preference) {
        switch (chocolate) {
            case MILK:
                preference.setSweetness(4);
                preference.setAcidity(2);
                break;
            case DARK:
                preference.setSweetness(2);
                preference.setAcidity(4);
                break;
        }
    }

    private void setCoffeeValues(Coffee coffee, Preference preference) {
        switch (coffee) {
            case AMERICANO:
                preference.setBody(2);
                break;
            case CAFE_LATTE:
                preference.setBody(4);
                break;
        }
    }

    private void setFruitValues(Fruit fruit, Preference preference) {
        switch (fruit) {
            case PEACH:
                preference.setTannins(4);
                break;
            case PINEAPPLE:
                preference.setTannins(2);
                break;
        }
    }

}
