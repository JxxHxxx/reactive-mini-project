package com.jxx.reactive.place.functional_endpoint_based;

import com.jxx.reactive.place.annotation_based.dto.PlaceForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;

@Slf4j
@Component
public class PlaceValidator implements Validator {

    private static List<String> cities = List.of("서울시", "성남시", "하남시", "수원시");

    @Override
    public boolean supports(Class<?> clazz) {
        return PlaceForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PlaceForm placeForm = (PlaceForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "상호명은 필수 값입니다.");

        if (!cities.contains(placeForm.city())) {
            errors.rejectValue("city","unknown.city", "지원하지 않는 시(군)입니다.");
        }
    }

    public void verify(PlaceForm placeForm) {
        Errors errors = new BeanPropertyBindingResult(placeForm, PlaceForm.class.getName());
        validate(placeForm, errors);

        if (errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage()).toList();

            throw new IllegalArgumentException(errorMessages.toString());
        }
    }
}
