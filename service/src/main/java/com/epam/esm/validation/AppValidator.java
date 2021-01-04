package com.epam.esm.validation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.TagException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppValidator {
    private final Validator validator;

    public void validate(GiftCertificateDTO giftCertificateDTO) {
        Set<ConstraintViolation<GiftCertificateDTO>> violations = validator.validate(giftCertificateDTO);
        for (ConstraintViolation<GiftCertificateDTO> violation : violations) {
            Optional<ExceptionType> type = ExceptionType.defineTypeByCode(violation.getMessage());
            if (type.isPresent()) {
                throw new GiftCertificateException(type.get());
            }
        }
        List<TagDTO> tagDTOs = giftCertificateDTO.getTags();
        if (tagDTOs != null) {
            tagDTOs.forEach(this::validate);
        }
    }

    public void validate(TagDTO tagDTO) {
        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);
        for (ConstraintViolation<TagDTO> violation : violations) {
            Optional<ExceptionType> type = ExceptionType.defineTypeByCode(violation.getMessage());
            if (type.isPresent()) {
                throw new TagException(type.get());
            }
        }
    }
}
