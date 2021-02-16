package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationDTO;
import com.epam.esm.dto.TokenDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.Role;
import com.epam.esm.service.UserService;
import com.epam.esm.util.builder.impl.UserHateoasBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The type User controller.
 * The class used to manipulate CRUD operations on Tag data.
 */
@RestController
@RequestMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserHateoasBuilder userHateoasBuilder;

    @PostMapping("/login")
    public TokenDTO userLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        return userService.login(authenticationDTO);
    }


    //TODO register

    /**
     * Find a UserDTO by id.
     *
     * @param id the id user for search
     * @return the UserDTO with the requested id
     */
    @GetMapping("/{id}")
    public TokenDTO findById(@PathVariable("id") long id) {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername("test@test.test");
        authenticationDTO.setPassword("aaa");
        return userService.login(authenticationDTO);
    }

    /**
     * Find all users.
     *
     * @param params the search parameters
     * @return the list of UserDTO
     */
    @GetMapping
    public RepresentationModel<UserDTO> findAll(@RequestParam Map<String, String> params) {
        Map<String, String> parameters = new LinkedCaseInsensitiveMap<>();
        parameters.putAll(params);
        List<UserDTO> users = userService.findAll(parameters);
        long count = userService.defineCount();
        return userHateoasBuilder.addLinks(users, parameters, count);
    }
}
