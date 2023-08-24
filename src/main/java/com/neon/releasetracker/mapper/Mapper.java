package com.neon.releasetracker.mapper;

import com.neon.releasetracker.domain.Release;
import com.neon.releasetracker.dto.ReleaseDto;

public class Mapper {

    public static ReleaseDto map(Release release) {
        return ReleaseDto.builder()
                .name(release.getName())
                .description(release.getDescription())
                .releaseDate(release.getReleaseDate())
                .releaseStatus(release.getReleaseStatus())
                .build();
    }
}
