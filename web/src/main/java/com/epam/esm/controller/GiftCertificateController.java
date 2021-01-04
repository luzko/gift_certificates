package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO add(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.add(giftCertificateDTO);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO update(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.update(id, giftCertificateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") long id) {
        giftCertificateService.remove(id);
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") long id) {
        return giftCertificateService.findById(id);
    }

    @GetMapping
    public List<GiftCertificateDTO> findAll(@RequestParam Map<String, String> parameters) {
        return giftCertificateService.findAll(parameters);
    }
}