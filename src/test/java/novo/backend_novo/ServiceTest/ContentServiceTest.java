package novo.backend_novo.ServiceTest;

import novo.backend_novo.Domain.Content;
import novo.backend_novo.Service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ContentServiceTest {
    @Autowired
    ContentService contentService;

    @Test
    void save_and_findById(){
        //given
        Content content = getContent("title","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        //when
        IdResponse idResponse = contentService.saveContent(content);
        //then
        assertEquals(contentService.getContentInfoWithId(idResponse.getId()).getTitle(),"title");
        assertEquals(contentService.getContentInfoWithId(idResponse.getId()).getKeyword().size(),2);
    }

    @Test
    void findAll(){
        //given
        Content content1 = getContent("title1","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        Content content2 = getContent("title2","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        Content content3 = getContent("title3","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        contentService.saveContent(content1);
        contentService.saveContent(content2);
        contentService.saveContent(content3);
        //when
        List<InfoResponse> contentInfoList = contentService.getAllContents();
        //then
        assertEquals(3,contentInfoList.size());
    }

    @Test
    void modifyContentInfo(){
        //given
        Content content = getContent("title","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        IdResponse idResponse = contentService.saveContent(content);
        List<String> keywords = new ArrayList<>();
        keywords.add("key1");
        UpdateRequest request = UpdateRequest.builder()
                .title("modifyTitle")
                .genre("modifyGenre")
                .keyword(keywords)
                .build();
        //when
        contentService.update(idResponse.getId(),request);
        //then
        assertEquals("modifyTitle",contentService.getContentInfoWithId(idResponse.getId()).getTitle());
        assertEquals("modifyGenre",contentService.getContentInfoWithId(idResponse.getId()).getGenre());
        assertEquals(null,contentService.getContentInfoWithId(idResponse.getId()).getIntroduction());
    }

    @Test
    void deleteContent(){
        //given
        Content content1 = getContent("title1","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        Content content2 = getContent("title2","writer","introduction","price","serialDay",
                LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
        IdResponse idResponse = contentService.saveContent(content1);
        contentService.saveContent(content2);
        assertEquals(2, contentService.getAllContents().size());
        //when
        contentService.removeContent(idResponse.getId());
        //then
        assertEquals(1, contentService.getAllContents().size());
    }

    private Content getContent( String title, String writer, String introduction,
                                String price, String serialDay, LocalDate publishedAt,
                                String genre, String keyword, String ageRating, String platform){
        return  Content.builder()
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
