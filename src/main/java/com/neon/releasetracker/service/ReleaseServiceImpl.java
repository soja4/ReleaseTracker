package com.neon.releasetracker.service;

import com.neon.releasetracker.domain.Release;
import com.neon.releasetracker.domain.ReleaseStatus;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.exception.NotFoundException;
import com.neon.releasetracker.exception.ReleaseTrackerException;
import com.neon.releasetracker.mapper.Mapper;
import com.neon.releasetracker.repository.ReleaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<ReleaseDto> filterAndFindReleases(String name, String description, ReleaseStatus releaseStatus, LocalDate releaseDate) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Release> cq = cb.createQuery(Release.class);

        Root<Release> release = cq.from(Release.class);
        Predicate releaseNamePredicate = cb.like(release.get("name"), "%" + name + "%");
        Predicate releaseDescriptionPredicate = cb.like(release.get("description"), "%" + description + "%");
        Predicate releaseStatusPredicate = cb.equal(release.get("releaseStatus"), releaseStatus);
        Predicate releaseDatePredicate = cb.equal(release.get("releaseDate"), releaseDate);
        if (name != null) {
            cq.where(releaseNamePredicate);
        }
        if (description != null) {
            cq.where(releaseDescriptionPredicate);
        }
        if (releaseStatus != null) {
            cq.where(releaseStatusPredicate);
        }
        if (releaseDate != null) {
            cq.where(releaseDatePredicate);
        }

        TypedQuery<Release> query = em.createQuery(cq);
        return query.getResultList().stream().map(Mapper::map).toList();
    }

    @Override
    public ReleaseDto createRelease(ReleaseDto releaseDto) {

        try {
            Release newRelease = Release.builder()
                    .name(releaseDto.getName())
                    .description(releaseDto.getDescription())
                    .releaseStatus(releaseDto.getReleaseStatus())
                    .releaseDate(releaseDto.getReleaseDate())
                    .build();

            return Mapper.map(releaseRepository.save(newRelease));
        } catch (Exception e) {
            log.error("Error creating new release: " + e.getCause().getMessage());
            throw new ReleaseTrackerException("Error creating new release: " + e.getCause().getMessage());
        }
    }

    @Override
    public ReleaseDto findReleaseById(Integer releaseId) {

        Optional<Release> releaseOptional = releaseRepository.findById(releaseId);
        if (releaseOptional.isPresent()) {
            return Mapper.map(releaseOptional.get());
        } else {
            log.error("Release with id: {} not found", releaseId);
            throw new NotFoundException(releaseId);
        }
    }

    @Override
    public void deleteRelease(Integer releaseId) {
        Optional<Release> release = releaseRepository.findById(releaseId);
        if (release.isEmpty()) {
            log.error("Release with id: {} not found", releaseId);
            throw new NotFoundException(releaseId);
        }
        releaseRepository.deleteById(releaseId);
    }

    @Override
    public ReleaseDto updateRelease(ReleaseDto releaseDto, Integer releaseId) {

        Optional<Release> release = releaseRepository.findById(releaseId);
        if (release.isEmpty()) {
            log.error("Release with id: {} not found", releaseId);
            throw new NotFoundException(releaseId);
        }
        try {
            release.get().setReleaseStatus(releaseDto.getReleaseStatus());
            release.get().setName(releaseDto.getName());
            release.get().setDescription(releaseDto.getDescription());
            release.get().setReleaseDate(releaseDto.getReleaseDate());

            return Mapper.map(releaseRepository.save(release.get()));
        } catch (Exception e) {
            log.error("Error updating new release with id: " + releaseId + " --- " + e.getCause().getMessage());
            throw new ReleaseTrackerException("Error updating new release with id: " + releaseId + " --- " + e.getCause().getMessage());
        }
    }
}
