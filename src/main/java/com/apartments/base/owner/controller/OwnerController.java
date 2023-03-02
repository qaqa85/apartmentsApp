package com.apartments.base.owner.controller;

import com.apartments.base.owner.models.dto.EditOwnerDto;
import com.apartments.base.owner.models.dto.NewOwnerDto;
import com.apartments.base.owner.models.dto.PageFilterSortOwnerDto;
import com.apartments.base.owner.service.OwnerService;
import com.apartments.base.utils.model.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    @Operation(summary = "Add new owner",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully saved, id returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "[Invalid lastname]"))
                    )}
    )
    ResponseEntity<Object> addNewOwner(@RequestBody NewOwnerDto ownerDto) {
        var response = ownerService.saveOwner(ownerDto);
        if (response.isInvalid()) {
            return errorMessageToResponse(response.getError());
        } else {
            return ResponseEntity.ok(response.get());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit existing owner",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully edited"),
                    @ApiResponse(responseCode = "404", description = "Owner not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "[Owner not found]"))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "[Invalid lastname]"))
                    )}
    )
    ResponseEntity<Object> editOwner(@RequestBody EditOwnerDto editOwnerDto, @PathVariable(required = false) String id) {
        var response = ownerService.editOwner(editOwnerDto, id);
        if (response.isInvalid()) {
            return errorMessageToResponse(response.getError());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get single owner",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully executed"),
                    @ApiResponse(responseCode = "404", description = "Owner not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "[Owner not found]"))),
            }
    )
    ResponseEntity<Object> getOwner(@PathVariable(required = false) String id) {
        var response = ownerService.getOwner(id);
        if (response.isInvalid()) {
            return errorMessageToResponse(response.getError());
        } else {
            return ResponseEntity.ok(response.get());
        }
    }

    @GetMapping
    @Operation(summary = "Get owners by pages",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully executed")
        }
    )
    ResponseEntity<Object> getOwners(@Valid PageFilterSortOwnerDto requirements) {
        return ResponseEntity.ok().body(ownerService.getOwners(requirements));
    }


    private ResponseEntity<Object> errorMessageToResponse(ErrorMessage errorMessage) {
        return ResponseEntity.status(errorMessage.errorType().code).body(errorMessage.errorMessages());
    }
}
