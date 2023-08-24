package com.neon.releasetracker.service;

import com.neon.releasetracker.dto.ReleaseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReleaseService {

    List<ReleaseDto> filterAndFindReleases(String name, String description, String status, LocalDate releaseDate);
    ReleaseDto createRelease(ReleaseDto releaseDto);
    ReleaseDto findReleaseById(Integer releaseId);
    void deleteRelease(Integer releaseId);
    ReleaseDto updateRelease(ReleaseDto releaseDto, Integer releaseId);
}
