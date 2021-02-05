package com.epam.esm.service.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.UserException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserDAO userDAO;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(long id) {
        return userMapper.toDto(userDAO.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll(Map<String, String> parameters) {
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        int offset = paginationUtil.defineOffset(parameters.get(LinkParam.PAGE.getValue()), limit);
        List<UserDTO> userDTOs = userMapper.toDto(userDAO.findAll(offset, limit));
        if (!userDTOs.isEmpty()) {
            return userDTOs;
        }
        throw new UserException(ExceptionType.USERS_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public long defineCount() {
        return userDAO.defineCount();
    }
}
