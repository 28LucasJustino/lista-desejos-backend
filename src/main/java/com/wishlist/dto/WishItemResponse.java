package com.wishlist.dto;

import com.wishlist.model.ItemStatus;
import com.wishlist.model.Priority;
import com.wishlist.model.WishItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class WishItemResponse {

    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private BigDecimal estimatedPrice;
    private Priority priority;
    private ItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WishItemResponse() {
    }

    public static WishItemResponse fromEntity(WishItem item) {
        WishItemResponse res = new WishItemResponse();
        res.setId(item.getId());
        res.setUserId(item.getUser().getId());
        res.setTitle(item.getTitle());
        res.setDescription(item.getDescription());
        res.setUrl(item.getUrl());
        res.setImageUrl(item.getImageUrl());
        res.setEstimatedPrice(item.getEstimatedPrice());
        res.setPriority(item.getPriority());
        res.setStatus(item.getStatus());
        res.setCreatedAt(item.getCreatedAt());
        res.setUpdatedAt(item.getUpdatedAt());
        return res;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

