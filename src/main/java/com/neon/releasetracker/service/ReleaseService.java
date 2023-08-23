package com.neon.releasetracker.service;

import com.neon.releasetracker.domain.Release;
import com.neon.releasetracker.dto.ReleaseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReleaseService {

    List<Release> filterAndFindReleases(String name, String description, String status, LocalDate releaseDate);
    Release createRelease(ReleaseDto releaseDto);
    Release findReleaseById(Integer releaseId);
    void deleteRelease(Integer releaseId);
}
