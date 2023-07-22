package novo.backend_novo.ServiceTest;

import novo.backend_novo.Domain.Member;
import novo.backend_novo.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.MemberDTO.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void join_and_findById(){
        //given
        Member member = Member.builder()
                .name("name")
                .email("email@gmail.com")
                .password("password")
                .build();
        //when
        IdResponse idResponse = memberService.join(member);
        //then
        assertEquals(memberService.getMemberInfoWithId(idResponse.getId()).getName(),"name");
        assertEquals(memberService.getMemberInfoWithId(idResponse.getId()).getEmail(),"email@gmail.com");
    }
    @Test
    void join_and_findByEmail(){
        //given
        Member member = getMember("name","email@gmail.com","password");
        //when
        IdResponse idResponse = memberService.join(member);
        //then
        assertEquals(memberService.getMemberInfoWithEmail("email@gmail.com").getName(),"name");
        assertEquals(memberService.getMemberInfoWithEmail("email@gmail.com").getEmail(),"email@gmail.com");
    }
    @Test
    void findAll(){
        //given
        Member member1 = getMember("name1","email1@gmail.com","password");
        Member member2 = getMember("name2","email2@gmail.com","password");
        Member member3 = getMember("name3","email3@gmail.com","password");
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //when
        List<InfoResponse> memberInfoList = memberService.getAllMembers();
        //then
        assertEquals(3, memberInfoList.size());
    }

    @Test
    void modifyMemberName(){
        //given
        Member member = getMember("name1","email1@gmail.com","password");
        IdResponse idResponse = memberService.join(member);
        //when
        memberService.update(idResponse.getId(),"eman");
        //then
        assertEquals("eman",memberService.getMemberInfoWithId(idResponse.getId()).getName());
    }

    @Test
    void deleteMember(){
        //given
        Member member1 = getMember("name1","email1@gmail.com","password");
        Member member2 = getMember("name2","email2@gmail.com","password");
        Member member3 = getMember("name3","email3@gmail.com","password");
        IdResponse idResponse =  memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);
        assertEquals(3, memberService.getAllMembers().size());
        //when
        memberService.removeMember(idResponse.getId());
        //then
        assertEquals(2, memberService.getAllMembers().size());
    }

    private Member getMember(String name, String email, String password){
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
