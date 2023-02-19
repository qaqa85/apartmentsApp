package com.apartments.base.owner.controller;

import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping("/")
    @Operation(summary = "Add new owner",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The owner id",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")}
    )
    ResponseEntity<Object> addNewOwner(@RequestBody NewOwnerDto ownerDto) {
        var result = ownerService.saveOwner(ownerDto);
        if (result.isInvalid()) {
            return ResponseEntity.badRequest().body(result.getError());
        } else {
            return ResponseEntity.ok(result.get());
        }
    }
}
