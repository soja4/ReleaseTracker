package com.neon.releasetracker.controller;

import com.neon.releasetracker.domain.ReleaseStatus;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("release-tracker")
@RequiredArgsConstructor
public class ReleaseController {

    private final ReleaseService releaseService;

    @GetMapping(path = "/releases")
    public List<ReleaseDto> getAllReleases(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) ReleaseStatus releaseStatus,
                                           @RequestParam(required = false) LocalDate releaseDate) {
        log.info("request for filtering and getting releases -- name: {}, description: {}, releaseStatus: {}, releaseDate: {}", name, description, releaseStatus, releaseDate);
        return releaseService.filterAndFindReleases(name, description, releaseStatus, releaseDate);
    }

    @GetMapping(path = "/releases/{releaseId}")
    public ReleaseDto findReleaseById(@PathVariable Integer releaseId) {
        log.info("request for getting release by id: {}", releaseId);
        return releaseService.findReleaseById(releaseId);
    }
    @PostMapping(path = "/releases")
    public ReleaseDto createRelease(@RequestBody ReleaseDto releaseDto) {
        log.info("request for creating new release, releaseDto: {}", releaseDto);
        return releaseService.createRelease(releaseDto);
    }
    @PutMapping(path = "/releases/{releaseId}")
    public ReleaseDto updateRelease(@RequestBody ReleaseDto releaseDto, @PathVariable Integer releaseId) {
        log.info("request for updating existing release with id: {}, releaseDto: {}", releaseId, releaseDto);
        return releaseService.updateRelease(releaseDto, releaseId);
    }
    @DeleteMapping(path = "/releases/{releaseId}")
    public ResponseEntity<String> deleteRelease(@PathVariable Integer releaseId) {
        log.info("request for deleting release with id: {}", releaseId);
        releaseService.deleteRelease(releaseId);
        return new ResponseEntity<>("Release with id: " + releaseId + " is deleted", HttpStatus.OK);
    }
}
