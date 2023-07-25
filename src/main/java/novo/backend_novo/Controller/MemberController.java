package novo.backend_novo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Response.BasicResponse;
import novo.backend_novo.Response.CommonResponse;
import novo.backend_novo.Service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.MemberDTO.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "회원", description = "회원 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping(path = "/test")
    public String test(){
        return "재배포 성공";
    }

    /*회원가입*/
    @Operation(summary = "회원가입")
    @PostMapping(path = "/new",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> join(
            @RequestBody @Valid JoinRequest request
    ){
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .authority(request.getAuthority())
                .build();

        return ResponseEntity.ok()
                .body(new CommonResponse<IdResponse>(memberService.join(member)));
    }

    /*로그인*/
    @Operation(summary = "로그인")
    @PostMapping(path = "/login",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> login(
            @RequestBody @Valid loginRequest request
    ){
        memberService.login(request.getEmail(),request.getPassword());
        return ResponseEntity.ok().build();
    }

    /*id로 회원 조회*/
    @Operation(summary = "id로 회원 조회")
    @GetMapping(path = "/id/{member-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findById(
            @PathVariable("member-id") Long memberId
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<InfoResponse>(memberService.getMemberInfoWithId(memberId)));
    }

    /*email로 회원 조회*/
    @Operation(summary = "email로 회원 조회")
    @GetMapping(path = "/email/{email}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findByEmail(
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<InfoResponse>(memberService.getMemberInfoWithEmail(email)));
    }

    /*전체 회원 조회*/
    @Operation(summary = "전체 회원 조회")
    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findAll()
    {
        return ResponseEntity.ok()
                .body(new CommonResponse<List>(memberService.getAllMembers()));
    }

    /*회원 정보 수정*/
    @Operation(summary = "회원 정보 수정")
    @PutMapping(path = "/id/{member-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> update(
            @PathVariable("member-id") Long memberId,
            @RequestBody @Valid UpdateRequest request
    ){
        memberService.update(memberId, request.getName());
        return ResponseEntity.ok().build();
    }

    /*회원 탈퇴*/
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping(path = "/id/{member-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> remove(
            @PathVariable("member-id") Long memberId
    ){
        memberService.removeMember(memberId);
        return ResponseEntity.ok().build();
    }
}
