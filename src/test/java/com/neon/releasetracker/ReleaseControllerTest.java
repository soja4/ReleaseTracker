package com.neon.releasetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neon.releasetracker.controller.ReleaseController;
import com.neon.releasetracker.domain.ReleaseStatus;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.service.ReleaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes=ReleaseTrackerApplication.class)
@WebMvcTest(ReleaseController.class)
public class ReleaseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ReleaseService releaseService;

    private List<ReleaseDto> releasesDto;
    private ReleaseDto releaseDto;

    @BeforeEach
    void setUp(){
        releasesDto = List.of(new ReleaseDto("test 1", "desc 1", ReleaseStatus.CREATED, LocalDate.of(2023, Month.AUGUST, 24)),
                new ReleaseDto("test 2", "desc 2", ReleaseStatus.ON_DEV, LocalDate.of(2023, Month.AUGUST, 25)),
                new ReleaseDto("test 3", "desc 3", ReleaseStatus.ON_PROD, LocalDate.of(2023, Month.AUGUST, 26)));
        releaseDto = new ReleaseDto("test 4", "desc 4", ReleaseStatus.DONE, LocalDate.of(2023, Month.AUGUST, 27));
    }

    @Test
    public void createReleaseTest() throws Exception {

        when(releaseService.createRelease(any(ReleaseDto.class))).thenReturn(releaseDto);

        MvcResult result = mockMvc.perform(post("/release-tracker/releases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(releaseDto).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        assertThat(result).isNotNull();
        String releaseJson = result.getResponse().getContentAsString();
        assertThat(releaseJson).isNotEmpty();
        assertThat(releaseJson).isEqualToIgnoringCase(mapper.writeValueAsString(releaseDto));

    }

    @Test
    public void getReleasesTest() throws Exception {

        when(releaseService.filterAndFindReleases(any(String.class), any(String.class), any(ReleaseStatus.class), any(LocalDate.class))).thenReturn(releasesDto);

        MvcResult result = this.mockMvc.perform(get("/release-tracker/releases")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result).isNotNull();
        String releasesJson = result.getResponse().getContentAsString();
        assertThat(releasesJson).isNotEmpty();
        assertThat(releasesJson).isEqualToIgnoringCase(mapper.writeValueAsString(releasesDto));
    }
    @Test
    public void getReleaseTest() throws Exception {

        when(releaseService.findReleaseById(any(Integer.class))).thenReturn(releaseDto);

        MvcResult result = this.mockMvc.perform(get("/release-tracker/releases/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result).isNotNull();
        String releasesJson = result.getResponse().getContentAsString();
        assertThat(releasesJson).isNotEmpty();
        assertThat(releasesJson).isEqualToIgnoringCase(mapper.writeValueAsString(releaseDto));
    }

    @Test
    public void updateReleaseTest() throws Exception {

        when(releaseService.updateRelease(any(ReleaseDto.class), any(Integer.class))).thenReturn(releaseDto);

        MvcResult result = this.mockMvc.perform(put("/release-tracker/releases/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(releaseDto).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result).isNotNull();
        String releaseJson = result.getResponse().getContentAsString();
        assertThat(releaseJson).isNotEmpty();
        assertThat(releaseJson).isEqualToIgnoringCase(mapper.writeValueAsString(releaseDto));
    }

    @Test
    public void deleteReleaseTest() throws Exception {

        MvcResult result = this.mockMvc.perform(delete("/release-tracker/releases/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result).isNotNull();
        String releaseJson = result.getResponse().getContentAsString();
        assertThat(releaseJson).isNotEmpty();
        assertThat(releaseJson).isEqualToIgnoringCase("Release is deleted");
    }
}
