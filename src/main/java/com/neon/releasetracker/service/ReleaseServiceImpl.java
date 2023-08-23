package com.neon.releasetracker.service;

import com.neon.releasetracker.domain.Release;
import com.neon.releasetracker.domain.ReleaseStatus;
import com.neon.releasetracker.dto.ReleaseDto;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Release> filterAndFindReleases(String name, String description, String releaseStatus, LocalDate releaseDate) {

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
        return query.getResultList();
    }

    @Override
    public Release createRelease(ReleaseDto releaseDto) {

        Release newRelease = Release.builder()
                .name(releaseDto.getName())
                .description(releaseDto.getDescription())
                .releaseStatus(ReleaseStatus.ON_PROD)
                .releaseDate(releaseDto.getReleaseDate())
                .build();

        return releaseRepository.save(newRelease);
    }

    @Override
    public Release findReleaseById(Integer releaseId) {
        return releaseRepository.findById(releaseId).get();
    }

    @Override
    public void deleteRelease(Integer releaseId) {
        releaseRepository.deleteById(releaseId);
    }
}
