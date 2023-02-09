package com.apartments.base.owner.controller;

import com.apartments.base.owner.models.NewOwnerDto;
import com.apartments.base.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    ResponseEntity<Object> addNewOwner(@RequestBody NewOwnerDto ownerDto) {
        var result = ownerService.saveOwner(ownerDto);
        if (result.isInvalid()) {
            return ResponseEntity.badRequest().body(result.getError());
        } else {
            return ResponseEntity.ok(result.get());
        }
    }

}
