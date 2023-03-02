package com.apartments.base.owner.controller;

import com.apartments.base.owner.models.dto.PageFilterSortOwnerDto;
import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "classpath:sql/clearOwners.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:sql/fillOwnersTable.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class OwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("should return page zero with two owners")
    void shouldReturnPageZeroWithTwoOwners() throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                "0",
                "2",
                null,
                null,
                null
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.pageable.pageSize").value("2"))
                .andExpect(jsonPath("$.pageable.pageNumber").value("0"));
    }

    @ParameterizedTest(name = "should return page zero with twenty owners when pageSize input is \"{0}\"")
    @ValueSource(strings = {"0", "-10", "21"})
    @NullAndEmptySource
    void shouldReturnPageZeroWithTwentyOwnersWhenPageSizeInputIsMissingOrInvalid(String pageSize) throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                null,
                pageSize,
                null,
                null,
                null
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(20));
    }

    @ParameterizedTest(name = "should return page zero when pageNumber input is \"{0}\"")
    @ValueSource(strings = {"-1"})
    @NullAndEmptySource
    void shouldReturnPageZeroWhenPageNumberIsInvalid(String pageNumber) throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                pageNumber,
                null,
                null,
                null,
                null
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }

    @Test
    @DisplayName("should return owners with surname starts from A")
    void shouldReturnOwnersWithSurnameStartsFromA() throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                null,
                null,
                "A",
                null,
                null
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].surname", Every.everyItem(Matchers.startsWith("A"))));
    }

    @Test
    @DisplayName("should return owners with surname starts from Mi")
    void shouldReturnOwnersWithLastnameStartsFromMi() throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                null,
                null,
                null,
                "Mi",
                null
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].lastname", Every.everyItem(Matchers.startsWith("Mi"))));
    }

    @ParameterizedTest(name = "should return owners with requested city {0}")
    @ValueSource(strings = {"Gdansk", "Tokyo", "Prague"})
    void shouldReturnOwnersWithRequestedCity(String city) throws Exception {
        PageFilterSortOwnerDto dto = new PageFilterSortOwnerDto(
                null,
                null,
                null,
                null,
                city
        );

        mockMvc.perform(get("/api/v1/owner")
                        .param("pageNumber", dto.getPageNumber())
                        .param("pageSize", dto.getPageSize())
                        .param("surname", dto.getSurname())
                        .param("lastname", dto.getLastname())
                        .param("city", dto.getCity()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                .andExpect(jsonPath("$.content[*].city", Every.everyItem(Matchers.is(city))));
    }
}