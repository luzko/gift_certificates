package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The type Gift certificate controller. The class used to manipulate CRUD operations on GiftCertificate data.
 */
@RestController
@RequestMapping(value = "/v1/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Create a new GiftCertificate.
     *
     * @param giftCertificateDTO the giftCertificateDTO with information to create
     * @return the created gift certificate
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO add(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.add(giftCertificateDTO);
    }

    /**
     * Replacement of a certificate information by a id.
     *
     * @param id                 the certificate id for replacement information
     * @param giftCertificateDTO the giftCertificateDTO with information for replacement
     * @return the modified certificate
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO update(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.update(id, giftCertificateDTO);
    }

    /**
     * Partial change of certificate information.
     *
     * @param id                      the certificate id for partial change of certificate information
     * @param giftCertificatePatchDTO the giftCertificatePatchDTO with information for partial change information
     * @return the modified certificate
     */
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO update(@PathVariable long id, @RequestBody GiftCertificatePatchDTO giftCertificatePatchDTO) {
        return giftCertificateService.update(id, giftCertificatePatchDTO);
    }

    /**
     * Remove a GiftCertificate by id.
     *
     * @param id the id gift certificate to remove
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") long id) {
        giftCertificateService.remove(id);
    }

    /**
     * Find a GiftCertificateDTO by id
     *
     * @param id the id certificate for search
     * @return the GiftCertificateDTO with the requested id
     */
    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable("id") long id) {
        return giftCertificateService.findById(id);
    }

    /**
     * Find all certificates.
     *
     * @param parameters the search parameters
     * @return the list of GiftCertificateDTOs
     */
    @GetMapping
    public List<GiftCertificateDTO> findAll(@RequestParam Map<String, String> parameters) {
        return giftCertificateService.findAll(parameters);
    }
}
