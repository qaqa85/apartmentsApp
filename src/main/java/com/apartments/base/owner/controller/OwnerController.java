package com.apartments.base.owner.controller;

import com.apartments.base.owner.models.EditOwnerDto;
import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.service.OwnerService;
import com.apartments.base.utils.models.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
        var result = ownerService.saveOwner(ownerDto);
        if (result.isInvalid()) {
            return errorMessageToResponse(result.getError());
        } else {
            return ResponseEntity.ok(result.get());
        }
    }

    @PutMapping
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
    ResponseEntity<Object> editOwner(@RequestBody EditOwnerDto editOwnerDto, @RequestParam String id) {
        var result = ownerService.editOwner(editOwnerDto, id);
        if (result.isInvalid()) {
            return errorMessageToResponse(result.getError());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private ResponseEntity<Object> errorMessageToResponse(ErrorMessage errorMessage) {
        return ResponseEntity.status(errorMessage.errorType().code).body(errorMessage.errorMessages());
    }
}
