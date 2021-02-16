package com.epam.esm.validation;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateOrderDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.OrderException;
import com.epam.esm.exception.TagException;
import com.epam.esm.exception.UserException;
import com.epam.esm.model.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppValidator {
    private final Validator validator;

    public void validate(GiftCertificateDTO giftCertificateDTO) {
        Set<ConstraintViolation<GiftCertificateDTO>> violations = validator.validate(giftCertificateDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new GiftCertificateException(exceptionType);
        }));
        Set<TagDTO> tagDTOs = giftCertificateDTO.getTags();
        if (tagDTOs != null) {
            tagDTOs.forEach(this::validate);
        }
    }

    public void validate(GiftCertificatePatchDTO giftCertificatePatchDTO) {
        Set<ConstraintViolation<GiftCertificatePatchDTO>> violations = validator.validate(giftCertificatePatchDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new GiftCertificateException(exceptionType);
        }));
        Set<TagDTO> tagDTOs = giftCertificatePatchDTO.getTags();
        if (tagDTOs != null) {
            tagDTOs.forEach(this::validate);
        }
    }

    public void validate(TagDTO tagDTO) {
        Set<ConstraintViolation<TagDTO>> violations = validator.validate(tagDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new TagException(exceptionType);
        }));
    }

    public void validate(UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new UserException(exceptionType);
        }));
    }

    public void validate(OrderDTO orderDTO) {
        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new OrderException(exceptionType);
        }));
        List<GiftCertificateOrderDTO> giftCertificateOrderDTOs = orderDTO.getCertificates();
        if (giftCertificateOrderDTOs != null) {
            giftCertificateOrderDTOs.forEach(this::validate);
        }
    }

    public void validate(List<GiftCertificateOrderDTO> certificateOrderDTOs, Map<Long, GiftCertificate> certificateMap) {
        if (certificateMap.isEmpty()) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
        } else if (certificateMap.size() < certificateOrderDTOs.size()) {
            Set<Long> ids = new HashSet<>();
            certificateOrderDTOs.forEach(certificateOrderDTO -> ids.add(certificateOrderDTO.getId()));
            if (ids.size() < certificateOrderDTOs.size()) {
                throw new GiftCertificateException(ExceptionType.CERTIFICATE_DUPLICATION);
            } else {
                throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
            }
        }
    }

    private void validate(GiftCertificateOrderDTO giftCertificateOrderDTO) {
        Set<ConstraintViolation<GiftCertificateOrderDTO>> violations = validator.validate(giftCertificateOrderDTO);
        violations.forEach(violation -> ExceptionType.defineTypeByCode(violation.getMessage()).ifPresent(exceptionType -> {
            throw new GiftCertificateException(exceptionType);
        }));
    }
}
