package com.apartments.base.owner.models.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageFilterSortOwnerDto {
    @Nullable
    private String pageNumber;

    @Nullable
    private String pageSize;

    @Nullable
    private String surname;

    @Nullable
    private String lastname;

    @Nullable
    private String city;
}