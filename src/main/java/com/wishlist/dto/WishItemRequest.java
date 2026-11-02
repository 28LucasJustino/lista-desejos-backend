package com.wishlist.dto;

import com.wishlist.model.ItemStatus;
import com.wishlist.model.Priority;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class WishItemRequest {

    @NotBlank(message = "O título do artigo é obrigatório")
    @Size(max = 255, message = "O título não pode ter mais de 255 caracteres")
    private String title;

    private String description;

    @Size(max = 2048, message = "O URL não pode ter mais de 2048 caracteres")
    private String url;

    @Size(max = 2048, message = "O URL da imagem não pode ter mais de 2048 caracteres")
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "O preço estimado deve ser maior ou igual a zero")
    private BigDecimal estimatedPrice;

    private Priority priority = Priority.MEDIUM;

    private ItemStatus status = ItemStatus.PENDING;

    public WishItemRequest() {
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
}

