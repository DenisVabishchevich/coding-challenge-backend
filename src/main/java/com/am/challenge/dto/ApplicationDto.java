package com.am.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto {
    private Long id;
    @Email
    private String eMail;
    @NotEmpty
    private String name;
    @NotEmpty
    private String githubUser;
    @Valid
    @NotEmpty
    private List<PastProjectDto> projects;
}
