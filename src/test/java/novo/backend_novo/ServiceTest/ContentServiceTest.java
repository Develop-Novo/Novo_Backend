package novo.backend_novo.ServiceTest;

import novo.backend_novo.Service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ContentServiceTest {
    @Autowired ContentService contentService;

    MockMultipartFile image1 = new MockMultipartFile("json", "", "application/json",
            "{src/main/resources/static/testImg1.jpg}".getBytes());
    MockMultipartFile image2 = new MockMultipartFile("json", "", "application/json",
            "{src/main/resources/static/testImg2.jpg}".getBytes());

    @Test
    void save_and_findById() throws IOException {
        //given
        List<String> keywords = new ArrayList<>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        SaveRequest request1 = getContent("title1","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        //when
        IdResponse idResponse = contentService.saveContent(request1,image1,image2);
        //then
        assertEquals(contentService.getContentInfoWithId(idResponse.getId()).getTitle(),"title1");
        assertEquals(contentService.getContentInfoWithId(idResponse.getId()).getKeyword().size(),2);
    }

   @Test
    void findAll() throws IOException {
        //given
        List<InfoResponse> initInfoList = contentService.getAllContents();
        List<String> keywords = new ArrayList<>();
        keywords.add("keyword1");
        keywords.add("keyword2");

        SaveRequest request1 = getContent("title1","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        SaveRequest request2 = getContent("title2","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        SaveRequest request3 = getContent("title3","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");

        contentService.saveContent(request1,image1,image2);
        contentService.saveContent(request2,image1,image2);
        contentService.saveContent(request3,image1,image2);

        //when
        List<InfoResponse> contentInfoList = contentService.getAllContents();
        //then
        assertEquals(3+initInfoList.size(),contentInfoList.size());
    }

    @Test
    void modifyContentInfo() throws IOException {
        //given
        List<String> keywords = new ArrayList<>();
        SaveRequest request1 = getContent("title1","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        IdResponse idResponse = contentService.saveContent(request1,image1, image2);
        List<String> mkeywords = new ArrayList<>();
        keywords.add("key1");
        UpdateRequest request = UpdateRequest.builder()
                .title("modifyTitle")
                .genre("modifyGenre")
                .keyword(mkeywords)
                .build();
        //when
        contentService.update(idResponse.getId(),request);
        //then
        assertEquals("modifyTitle",contentService.getContentInfoWithId(idResponse.getId()).getTitle());
        assertEquals("modifyGenre",contentService.getContentInfoWithId(idResponse.getId()).getGenre());
        assertEquals(null,contentService.getContentInfoWithId(idResponse.getId()).getIntroduction());
    }

    @Test
    void deleteContent() throws IOException {
        //given
        List<InfoResponse> initInfoList = contentService.getAllContents();
        List<String> keywords = new ArrayList<>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        SaveRequest request1 = getContent("title1","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        SaveRequest request2 = getContent("title2","writer","introduction","price","serialDay",
                "publishedAt","genre",keywords,"ageRating","platform");
        IdResponse idResponse = contentService.saveContent(request1,image1,image2);
        IdResponse idResponse2 = contentService.saveContent(request2, image1, image2);
        assertEquals(2+initInfoList.size(), contentService.getAllContents().size());
        //when
        contentService.removeContent(idResponse.getId());
        //then
        assertEquals(1+initInfoList.size(), contentService.getAllContents().size());
    }

    private SaveRequest getContent( String title, String writer, String introduction,
                                String price, String serialDay, String publishedAt,
                                String genre, List<String> keyword, String ageRating, String platform){
        return  SaveRequest.builder()
                .title(title)
                .writer(writer)
                .introduction(introduction)
                .price(price)
                .serialDay(serialDay)
                .publishedAt(publishedAt)
                .genre(genre)
                .keyword(keyword)
                .ageRating(ageRating)
                .platform(platform)
                .build();
    }
}
