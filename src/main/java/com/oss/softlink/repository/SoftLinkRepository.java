package com.oss.softlink.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftLinkRepository extends JpaRepository<SoftLink, String> {

	Page<SoftLink> findByCustomerId(String customerId, Pageable pageable);
}
