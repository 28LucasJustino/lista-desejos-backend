package com.wishlist.service;

import com.wishlist.dto.WishItemRequest;
import com.wishlist.dto.WishItemResponse;
import com.wishlist.exception.ResourceNotFoundException;
import com.wishlist.model.ItemStatus;
import com.wishlist.model.Priority;
import com.wishlist.model.User;
import com.wishlist.model.WishItem;
import com.wishlist.repository.WishItemRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WishItemService {

    private final WishItemRepository wishItemRepository;

    public WishItemService(WishItemRepository wishItemRepository) {
        this.wishItemRepository = wishItemRepository;
    }

    @Transactional(readOnly = true)
    public Page<WishItemResponse> getItems(
            User user,
            String search,
            Priority priority,
            ItemStatus status,
            Pageable pageable
    ) {
        Specification<WishItem> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("user"), user));

            if (search != null && !search.trim().isEmpty()) {
                String pattern = "%" + search.trim().toLowerCase() + "%";
                Predicate titleLike = cb.like(cb.lower(root.get("title")), pattern);
                Predicate descLike = cb.like(cb.lower(root.get("description")), pattern);
                predicates.add(cb.or(titleLike, descLike));
            }

            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return wishItemRepository.findAll(spec, pageable).map(WishItemResponse::fromEntity);
    }

    @Transactional
    public WishItemResponse createItem(User user, WishItemRequest request) {
        WishItem item = new WishItem();
        item.setUser(user);
        item.setTitle(request.getTitle().trim());
        item.setDescription(request.getDescription());
        item.setUrl(request.getUrl());
        item.setImageUrl(request.getImageUrl());
        item.setEstimatedPrice(request.getEstimatedPrice());
        item.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        item.setStatus(request.getStatus() != null ? request.getStatus() : ItemStatus.PENDING);

        WishItem saved = wishItemRepository.save(item);
        return WishItemResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public WishItemResponse getItemById(User user, UUID id) {
        WishItem item = wishItemRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artigo com ID " + id + " não encontrado."));
        return WishItemResponse.fromEntity(item);
    }

    @Transactional
    public WishItemResponse updateItem(User user, UUID id, WishItemRequest request) {
        WishItem item = wishItemRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artigo com ID " + id + " não encontrado."));

        item.setTitle(request.getTitle().trim());
        item.setDescription(request.getDescription());
        item.setUrl(request.getUrl());
        item.setImageUrl(request.getImageUrl());
        item.setEstimatedPrice(request.getEstimatedPrice());
        if (request.getPriority() != null) {
            item.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            item.setStatus(request.getStatus());
        }

        WishItem updated = wishItemRepository.save(item);
        return WishItemResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteItem(User user, UUID id) {
        WishItem item = wishItemRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artigo com ID " + id + " não encontrado."));
        wishItemRepository.delete(item);
    }
}

