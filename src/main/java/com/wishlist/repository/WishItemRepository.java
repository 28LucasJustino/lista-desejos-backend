package com.wishlist.repository;

import com.wishlist.model.WishItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishItemRepository extends JpaRepository<WishItem, UUID>, JpaSpecificationExecutor<WishItem> {
    Optional<WishItem> findByIdAndUserId(UUID id, UUID userId);
    Page<WishItem> findAll(Specification<WishItem> spec, Pageable pageable);
}

