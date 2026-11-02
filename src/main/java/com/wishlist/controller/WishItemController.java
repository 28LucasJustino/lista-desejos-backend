package com.wishlist.controller;

import com.wishlist.dto.WishItemRequest;
import com.wishlist.dto.WishItemResponse;
import com.wishlist.model.ItemStatus;
import com.wishlist.model.Priority;
import com.wishlist.model.User;
import com.wishlist.service.AuthService;
import com.wishlist.service.WishItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@Tag(name = "Wish Items", description = "Gestão de artigos da lista de desejos")
@SecurityRequirement(name = "bearerAuth")
public class WishItemController {

    private final WishItemService wishItemService;
    private final AuthService authService;

    public WishItemController(WishItemService wishItemService, AuthService authService) {
        this.wishItemService = wishItemService;
        this.authService = authService;
    }

    @GetMapping
    @Operation(summary = "Listar artigos", description = "Listagem paginada e filtrável dos artigos do utilizador autenticado")
    public ResponseEntity<Page<WishItemResponse>> getItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) ItemStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            Authentication authentication
    ) {
        User user = authService.getAuthenticatedUser(authentication);

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<WishItemResponse> items = wishItemService.getItems(user, search, priority, status, pageable);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    @Operation(summary = "Criar novo artigo", description = "Adiciona um novo artigo à lista de desejos")
    public ResponseEntity<WishItemResponse> createItem(
            @Valid @RequestBody WishItemRequest request,
            Authentication authentication
    ) {
        User user = authService.getAuthenticatedUser(authentication);
        WishItemResponse response = wishItemService.createItem(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes do artigo", description = "Recupera os detalhes de um artigo específico do utilizador")
    public ResponseEntity<WishItemResponse> getItemById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = authService.getAuthenticatedUser(authentication);
        WishItemResponse response = wishItemService.getItemById(user, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar artigo", description = "Atualiza os parâmetros cadastrais ou estado do artigo")
    public ResponseEntity<WishItemResponse> updateItem(
            @PathVariable UUID id,
            @Valid @RequestBody WishItemRequest request,
            Authentication authentication
    ) {
        User user = authService.getAuthenticatedUser(authentication);
        WishItemResponse response = wishItemService.updateItem(user, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artigo", description = "Remove um artigo da lista de desejos")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        User user = authService.getAuthenticatedUser(authentication);
        wishItemService.deleteItem(user, id);
        return ResponseEntity.noContent().build();
    }
}

