package com.am.challenge.dto;

import com.am.challenge.domain.Capacity;
import com.am.challenge.domain.EmploymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PastProjectDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private EmploymentMode employmentMode;
    @NotNull
    private Capacity capacity;
    @NotNull
    private Duration duration;
    @NotNull
    private Integer startYear;
    @NotNull
    private String role;
    @NotNull
    private Integer teamSize;
    private String repositoryUrl;
    private String lifeUrl;
}
