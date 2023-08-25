package com.neon.releasetracker.mapper;

import com.neon.releasetracker.domain.Release;
import com.neon.releasetracker.dto.ReleaseDto;

public class ReleaseMapper {

    public static ReleaseDto map(Release release) {
        return ReleaseDto.builder()
                .name(release.getName())
                .description(release.getDescription())
                .releaseDate(release.getReleaseDate())
                .releaseStatus(release.getReleaseStatus())
                .createdAt(release.getCreatedAt())
                .lastUpdatedAt(release.getLastUpdatedAt())
                .build();
    }
    public static Release toEntity(ReleaseDto releaseDto) {
        return Release.builder()
                .name(releaseDto.getName())
                .description(releaseDto.getDescription())
                .releaseDate(releaseDto.getReleaseDate())
                .releaseStatus(releaseDto.getReleaseStatus())
                .build();
    }
}
