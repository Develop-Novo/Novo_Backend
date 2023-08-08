package novo.backend_novo.ServiceTest;

import novo.backend_novo.DTO.CommonDTO;
import novo.backend_novo.DTO.ContentDTO;
import novo.backend_novo.DTO.StarDTO;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Service.ContentService;
import novo.backend_novo.Service.MemberService;
import novo.backend_novo.Service.StarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.StarDTO.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StarServiceTest {

    @Autowired StarService starService;
    @Autowired MemberService memberService;
    @Autowired ContentService contentService;

    Member member1 = Member.builder()
            .name("member1")
            .email("email1@gmail.com")
            .password("password")
            .build();
    Member member2 = Member.builder()
            .name("member2")
            .email("email2@gmail.com")
            .password("password")
            .build();
    ContentDTO.SaveRequest content1 = ContentDTO.SaveRequest.builder()
            .title("content1")
            .genre("genre")
            .build();

    Long member1Id = null;
    Long member2Id = null;
    Long content1Id = null;

    MockMultipartFile image1 = new MockMultipartFile("json", "", "application/json",
            "{src/main/resources/static/testImg1.jpg}".getBytes());
    MockMultipartFile image2 = new MockMultipartFile("json", "", "application/json",
            "{src/main/resources/static/testImg2.jpg}".getBytes());

    @BeforeEach
    void setUp() throws IOException {
        IdResponse idResponse1 = memberService.join(member1);
        IdResponse idResponse2 = memberService.join(member2);
        IdResponse idResponse3 = contentService.saveContent(content1,image1,image2);

        member1Id = idResponse1.getId();
        member2Id = idResponse2.getId();
        content1Id = idResponse3.getId();
    }

    @Test
    void save_and_findById(){
        //given
        SaveRequest request1 = getStar(member1Id, content1Id, (float)5.3);
        //when
        IdResponse starId = starService.saveStar(request1);
        //then
        assertEquals(starService.getStarInfoWithId(starId.getId()).getStar(),(float)5.3);
    }

    @Test
    void findAll(){
        //given
        List<InfoResponse> initInfoList = starService.getAllStarsWithContentId(content1Id);

        SaveRequest request1 = getStar(member1Id, content1Id, (float)8);
        SaveRequest request2 = getStar(member2Id, content1Id, (float)3);

        starService.saveStar(request1);
        starService.saveStar(request2);

        //when
        List<InfoResponse> starInfoList = starService.getAllStarsWithContentId(content1Id);

        //then
        assertEquals(2+ initInfoList.size(), starInfoList.size());
    }

    @Test
    void modifyStarInfo() {
        //given
        SaveRequest request1 = getStar(member1Id, content1Id, (float)8);
        IdResponse starId = starService.saveStar(request1);
        UpdateRequest request = UpdateRequest.builder().star((float)1).build();
        //when
        starService.update(starId.getId(),request.getStar());
        //then
        assertEquals(starService.getStarInfoWithId(starId.getId()).getStar(),1);
    }

    @Test
    void deleteStar(){
        //given
        List<InfoResponse> initInfoList = starService.getAllStarsWithContentId(content1Id);

        SaveRequest request1 = getStar(member1Id, content1Id, (float)8);
        SaveRequest request2 = getStar(member2Id, content1Id, (float)3);

        IdResponse starId1 = starService.saveStar(request1);
        IdResponse starId2 = starService.saveStar(request2);
        List<InfoResponse> starInfoList = starService.getAllStarsWithContentId(content1Id);
        assertEquals(2+initInfoList.size(), starInfoList.size());

        //when
        starService.removeStar(starId1.getId());
        starInfoList = starService.getAllStarsWithContentId(content1Id);

        //then
        assertEquals(1+initInfoList.size(), starInfoList.size());
    }

    private SaveRequest getStar(Long memberId, Long contentId, float star) {
        return SaveRequest.builder()
                .memberId(memberId)
                .contentId(contentId)
                .star(star)
                .build();
    }
}
