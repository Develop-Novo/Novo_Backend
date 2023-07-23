package novo.backend_novo.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novo.backend_novo.Controller.ExceptionController;
import novo.backend_novo.Controller.MemberController;
import novo.backend_novo.DTO.MemberDTO;
import novo.backend_novo.Domain.Authority;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.MemberDTO.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//TODO: Exception Test Case
@SpringBootTest
public class MemberControllerTest {

    private MockMvc mockMvc;
    @MockBean private MemberService memberService;
    @Autowired private ObjectMapper objectMapper;

    Member member1 = getMember("member1","email1@gmail.com","password");
    Member member2 = getMember("member2","email2@gmail.com","password");
    Member member3 = getMember("member3","email3@gmail.com","password");
    InfoResponse infoResponse1 = InfoResponse.of(member1);
    InfoResponse infoResponse2 = InfoResponse.of(member2);
    InfoResponse infoResponse3 = InfoResponse.of(member3);
    List<InfoResponse> memberList = new ArrayList<>();

    IdResponse idResponse = new IdResponse(1L);

    @BeforeEach
    void setUp(@Autowired MemberController memberController){
        //MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(ExceptionController.class)
                .build();

        memberList.add(infoResponse1);
        memberList.add(infoResponse2);
        memberList.add(infoResponse3);
    }

    public String toJsonString(Member member) throws JsonProcessingException{
        return objectMapper.writeValueAsString(member);
    }
    public String toJsonString(JoinRequest request) throws JsonProcessingException{
        return objectMapper.writeValueAsString(request);
    }
    public String toJsonString(loginRequest request) throws JsonProcessingException{
        return objectMapper.writeValueAsString(request);
    }
    public String toJsonString(UpdateRequest request) throws JsonProcessingException{
        return objectMapper.writeValueAsString(request);
    }

    /*회원가입*/
    @Test
    @DisplayName("member join")
    void save() throws Exception{
        //given
        JoinRequest joinRequest = JoinRequest.builder()
                .name("name1").email("email@naver.com")
                .password("password").authority(Authority.ROLE_USER).build();
        String object = toJsonString(joinRequest);
        given(memberService.join(any())).willReturn(idResponse);

        //when
        ResultActions actions = mockMvc.perform(post("/member/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists());
    }

    /*로그인*/
    @Test
    @DisplayName("login")
    void login() throws Exception{
        //given
        loginRequest loginRequest = MemberDTO.loginRequest.builder()
                .email("jeongwoon@gmail.com").password("password").build();
        String object = toJsonString(loginRequest);

        //when
        ResultActions actions = mockMvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk());
    }

    /*id로 회원 조회*/
    @Test
    @DisplayName("find By Id")
    void findById() throws Exception{
        //given
        given(memberService.getMemberInfoWithId(any())).willReturn(infoResponse1);

        //when
        ResultActions actions = mockMvc.perform(get("/member/id/1"));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(member1.getName()))
                .andExpect(jsonPath("$.data.email").value(member1.getEmail()));
    }

    /*email로 회원 조회*/
    @Test
    @DisplayName("find By Email")
    void findByEmail() throws Exception{
        //given
        given(memberService.getMemberInfoWithEmail(any())).willReturn(infoResponse1);

        //when
        ResultActions actions = mockMvc.perform(get("/member/email/email1@gmail.com"));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(member1.getName()))
                .andExpect(jsonPath("$.data.email").value(member1.getEmail()));
    }

    /*전체 회원 조회*/
    @Test
    @DisplayName("find All")
    void findAll() throws Exception{
        //given
        given(memberService.getAllMembers()).willReturn(memberList);

        //when
        ResultActions actions = mockMvc.perform(get("/member/all"));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(memberList.size()));
    }

    /*회원 정보 수정*/
    @Test
    @DisplayName("modify member info")
    void modifyMemberInfo() throws Exception{
        //given
        UpdateRequest request = UpdateRequest.builder().name("modify").build();
        String object = toJsonString(request);

        //when
        ResultActions actions = mockMvc.perform(put("/member/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk());
    }

    /*회원 탈퇴*/
    @Test
    @DisplayName("delete member")
    void deleteMember() throws Exception{
        //given
        //when
        ResultActions actions = mockMvc.perform(delete("/member/id/1"));
        //then
        actions
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Member getMember(String name, String email, String password){
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
