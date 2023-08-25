package com.neon.releasetracker.dto;

import com.neon.releasetracker.domain.ReleaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ReleaseDto {

    private String name;
    private String description;
    private ReleaseStatus releaseStatus;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
