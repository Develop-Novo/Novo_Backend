package novo.backend_novo.Service;


import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.MemberDTO.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    //이메일로 유저 정보 찾아오기
    public InfoResponse getMemberInfoWithEmail(String email){
        return memberRepository.findByEmail(email)
                .map(InfoResponse::of)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
    }

    //member Id로 유저 정보 찾아오기
    public InfoResponse getMemberInfoWithId(Long memberId){
        return memberRepository.findById(memberId)
                .map(InfoResponse::of)
                .orElseThrow(()-> new IllegalArgumentException("회원 정보가 없습니다."));
    }

    //모든 member 반환하기
    public List<InfoResponse> getAllMembers(){
        return  memberRepository.findAll().stream()
                .map(InfoResponse::of)
                .collect(Collectors.toList());
    }

    //회원가입(임시)
    @Transactional
    public IdResponse join(Member member){
        if(memberRepository.existsByEmail(member.getEmail()))
            throw new IllegalArgumentException("중복된 이메일입니다.");
        memberRepository.save(member);
        return new IdResponse(member.getId());
    }

    //로그인(임시)
    @Transactional
    public void login(String email, String password){
        Member compMember = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if(!compMember.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 옳지 않습니다.");
    }

    //회원 이름 수정
    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("회원 id가 올바르지 않습니다."));
        member.updateName(name);
    }

    //회원 탈퇴
    @Transactional
    public void removeMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("회원 id가 올바르지 않습니다."));
        memberRepository.delete(member);
    }

    //프로필 이미지 설정
    @Transactional
    public void saveProfile(Long id, MultipartFile profileImg) throws IOException {
        String uuid = null;
        if(profileImg.isEmpty())
            throw new IOException("파일이 올바르지 않습니다.");
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("회원 id가 올바르지 않습니다."));
        uuid = imageService.uploadImage(profileImg);
        uuid = imageService.processImage(uuid);
        member.updateImg(uuid);
    }
}
