package com.neon.releasetracker.dto;

import com.neon.releasetracker.domain.ReleaseStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReleaseDto {

    private String name;
    private String description;
    private String releaseStatus;
    private LocalDate releaseDate;
}
