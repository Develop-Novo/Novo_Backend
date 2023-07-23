package novo.backend_novo.ServiceTest;

import novo.backend_novo.Domain.Content;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Service.ContentService;
import novo.backend_novo.Service.MemberService;
import novo.backend_novo.Service.StarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.StarDTO.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class StarServiceTest {
    @Autowired
    StarService starService;
    @Autowired
    MemberService memberService;
    @Autowired
    ContentService contentService;

    Member member1 = getMember("name1","email1@gmail.com","password");
    Content content1 = getContent("title1","writer","introduction","price","serialDay",
            LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");
    Member member2 = getMember("name2","email2@gmail.com","password");
    Content content2 = getContent("title2","writer","introduction","price","serialDay",
            LocalDate.now(),"genre","keyword1,keyword2","ageRating","platform");

    @BeforeEach
    void setUp(){
        memberService.join(member1);
        contentService.saveContent(content1);
        memberService.join(member2);
        contentService.saveContent(content2);
    }

    @Test
    void save_and_findById(){
        //given & when
        IdResponse idResponse = getStar(member1,content1,(float)8.5);
        //then
        assertEquals(8.5, starService.getStarInfoWithId(idResponse.getId()).getStar());
    }

    @Test
    void getAllStarsWithContentId(){
        //given
        IdResponse idResponse1 = getStar(member1,content1,(float)8.5);
        IdResponse idResponse2 = getStar(member2,content1, (float)5);
        IdResponse idResponse3 = getStar(member2,content2,(float)7);
        //when
        List<InfoResponse> responseList = starService.getAllStarsWithContentId(content1.getId());
        //then
        assertEquals(2,responseList.size());
    }

    @Test
    void modifyStar(){
        //given
        IdResponse idResponse1 = getStar(member1,content1,(float)8.5);
        //when
        starService.update(idResponse1.getId(),(float)4.5);
        //then
        assertEquals(4.5, starService.getStarInfoWithId(idResponse1.getId()).getStar());
    }

    @Test
    void deleteStar(){
        //given
        IdResponse idResponse1 = getStar(member1,content1,(float)8.5);
        IdResponse idResponse2 = getStar(member2,content1, (float)5);
        //when
        List<InfoResponse> responseList = starService.getAllStarsWithContentId(content1.getId());
        //then
        assertEquals(2,responseList.size());
        //given
        starService.removeStar(idResponse1.getId());
        //when
        List<InfoResponse> modifyList = starService.getAllStarsWithContentId(content1.getId());
        //then
        assertEquals(1, modifyList.size());
    }

    private Member getMember(String name, String email, String password){
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
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

    private IdResponse getStar(Member member, Content content, float star){
        SaveRequest request = SaveRequest.builder()
                .memberId(member.getId())
                .contentId(content.getId())
                .star(star)
                .build();

        return starService.saveStar(request);
    }
}
