package novo.backend_novo.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novo.backend_novo.Controller.ContentController;
import novo.backend_novo.Controller.ExceptionController;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: Exception Test Case
@SpringBootTest
public class ContentControllerTest {

    private MockMvc mockMvc;
    @MockBean private ContentService contentService;
    @Autowired private ObjectMapper objectMapper;

    Content content1 = Content.builder() .title("title1").genre("fantasy").build();
    Content content2 = Content.builder() .title("title2").genre("fantasy").build();
    Content content3 = Content.builder() .title("title3").genre("fantasy").build();
    InfoResponse infoResponse1 = InfoResponse.of(content1);
    InfoResponse infoResponse2 = InfoResponse.of(content2);
    InfoResponse infoResponse3 = InfoResponse.of(content3);
    List<InfoResponse> contentList = new ArrayList<>();

    @BeforeEach
    void setUp(@Autowired ContentController contentController) {
        //MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(contentController)
                .setControllerAdvice(ExceptionController.class)
                .build();
        contentList.add(infoResponse1);
        contentList.add(infoResponse2);
        contentList.add(infoResponse3);
    }

    public String toJsonString(Content content) throws JsonProcessingException {
        return objectMapper.writeValueAsString(content);
    }
    public String toJsonString(SaveRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
    public String toJsonString(UpdateRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    /*작품 등록*/
    @Test
    @DisplayName("content save")
    void save() throws Exception{
        //given
        List<String> keywords = new ArrayList<>();
        keywords.add("key1");
        keywords.add("key2");

        SaveRequest request = SaveRequest.builder()
                .title("title").keyword(keywords).genre("fantasy").build();
        String object = toJsonString(request);
        MockMultipartFile json = new MockMultipartFile("request", "jsondata", "application/json",
                object.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile image1 = new MockMultipartFile("json", "", "application/json",
                "{src/main/resources/static/testImg1.jpg}".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("json", "", "application/json",
                "{src/main/resources/static/testImg2.jpg}".getBytes());

        given(contentService.saveContent(any(),any(),any())).willReturn(new IdResponse(1L));

        //when
        ResultActions actions = mockMvc.perform(multipart("/content/new")
                .file(json)
                .file(image1)
                .file(image2)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists());
    }

    /*작품 정보 id로 조회*/
    @Test
    @DisplayName("find By Id")
    void findById() throws Exception{
        //given
        given(contentService.getContentInfoWithId(any())).willReturn(infoResponse1);
        //when
        ResultActions actions = mockMvc.perform(get("/content/id/1"));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(infoResponse1.getTitle()))
                .andExpect(jsonPath("$.data.genre").value(infoResponse1.getGenre()));

    }

    /*작품 정보 전체 조회*/
   @Test
    @DisplayName("find All")
    void findAll() throws Exception{
        //given
        given(contentService.getAllContents()).willReturn(contentList);
        //when
        ResultActions actions = mockMvc.perform(get("/content/all"));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(contentList.size()));
    }

    /*작품 수정*/
    @Test
    @DisplayName("modify content")
    void modifyContent() throws Exception{
        //given
        UpdateRequest request = UpdateRequest.builder().title("modify").build();
        String object = toJsonString(request);
        //when
        ResultActions actions = mockMvc.perform(put("/content/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk());
    }

    /*작품 삭제*/
    @Test
    @DisplayName("delete content")
    void deleteContent() throws Exception{
        //given
        //when
        ResultActions actions = mockMvc.perform(delete("/content/id/1"));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk());
    }
}
