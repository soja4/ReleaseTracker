package com.neon.releasetracker.repository;

import com.neon.releasetracker.domain.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Integer> {
}
