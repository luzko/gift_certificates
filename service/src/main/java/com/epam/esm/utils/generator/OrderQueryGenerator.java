package com.epam.esm.utils.generator;

import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.UserException;
import com.epam.esm.specification.Specification;
import com.epam.esm.specification.impl.GetOrderByUserId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderQueryGenerator {
    private static final String USER_ID = "userId";

    private List<Specification> specifications;

    public List<Specification> generate(Map<String, String> parameters) {
        specifications = new ArrayList<>();
        generateSpecifications(parameters);
        return specifications;
    }

    private void generateSpecifications(Map<String, String> parameters) {
        Long userId = defineLongValue(parameters.get(USER_ID));
        if (userId != null) {
            specifications.add(new GetOrderByUserId(userId));
        }
    }

    private Long defineLongValue(String userIdValue) {
        Long userId = null;
        if (userIdValue != null) {
            try {
                userId = Long.parseLong(userIdValue);
                if (userId < 1) {
                    throw new UserException(ExceptionType.USER_ID_NEGATIVE);
                }
            } catch (NumberFormatException e) {
                throw new UserException(ExceptionType.USER_ID_INVALID);
            }
        }
        return userId;
    }
}
