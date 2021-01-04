package com.epam.esm.validation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.TagException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppValidator {
    private final Validator validator;

    public void validate(GiftCertificateDTO giftCertificateDTO) {
        Set<ConstraintViolation<GiftCertificateDTO>> violations = validator.validate(giftCertificateDTO);
        for (ConstraintViolation<GiftCertificateDTO> violation : violations) {
            ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
                throw new GiftCertificateException(exceptionType);
            });
        }
        List<TagDTO> tagDTOs = giftCertificateDTO.getTags();
        if (tagDTOs != null) {
            tagDTOs.forEach(this::validate);
        }
    }

    public void validate(GiftCertificatePatchDTO giftCertificatePatchDTO) {
        Set<ConstraintViolation<GiftCertificatePatchDTO>> violations = validator.validate(giftCertificatePatchDTO);
        for (ConstraintViolation<GiftCertificatePatchDTO> violation : violations) {
            ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
                throw new GiftCertificateException(exceptionType);
            });
        }
        List<TagDTO> tagDTOs = giftCertificatePatchDTO.getTags();
        if (tagDTOs != null) {
            tagDTOs.forEach(this::validate);
        }
    }

    public void validate(TagDTO tagDTO) {
        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);
        for (ConstraintViolation<TagDTO> violation : violations) {
            ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
                throw new TagException(exceptionType);
            });
        }
    }
}
