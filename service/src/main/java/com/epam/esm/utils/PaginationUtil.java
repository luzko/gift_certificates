package com.epam.esm.utils;

import com.epam.esm.constant.PaginationParam;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.PaginationException;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    public int definePage(String param) {
        int page;
        if (param == null) {
            page = PaginationParam.DEFAULT_PAGE.getValue();
        } else {
            try {
                page = Integer.parseInt(param);
                if (page < PaginationParam.MIN_PAGE.getValue()) {
                    throw new PaginationException(ExceptionType.PAGE_VALUE_NEGATIVE);
                }
            } catch (NumberFormatException e) {
                throw new PaginationException(ExceptionType.PAGE_VALUE_INVALID);
            }
        }
        return page;
    }

    public int defineLimit(String param) {
        int limit;
        if (param == null) {
            limit = PaginationParam.DEFAULT_LIMIT.getValue();
        } else {
            try {
                limit = Integer.parseInt(param);
                if (limit < PaginationParam.MIN_LIMIT.getValue() || limit > PaginationParam.MAX_LIMIT.getValue()) {
                    throw new PaginationException(ExceptionType.LIMIT_VALUE_EXCEED);
                }
            } catch (NumberFormatException e) {
                throw new PaginationException(ExceptionType.LIMIT_VALUE_INVALID);
            }
        }
        return limit;
    }

    public int defineOffset(String pageParam, int limit) {
        return (definePage(pageParam) - 1) * limit;
    }
}
