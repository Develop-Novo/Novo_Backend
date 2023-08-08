package novo.backend_novo.Service;

import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Domain.Member;
import novo.backend_novo.Domain.Star;
import novo.backend_novo.Repository.ContentRepository;
import novo.backend_novo.Repository.MemberRepository;
import novo.backend_novo.Repository.StarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.StarDTO.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StarService {
    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;

    @Transactional
    private void renewRating(Long contentId){
        List<Star> starList = starRepository.findByContentId(contentId);
        Content content = contentRepository.findById(contentId)
                .orElseThrow(()-> new IllegalArgumentException("작품 id가 올바르지 않습니다."));
        int sum = 0;
        for(int i=0; i<starList.size(); i++){
            sum += starList.get(i).getStar();
        }
        content.updateRating((float)sum/starList.size());
    }

    //평점 저장
    @Transactional
    public IdResponse saveStar(SaveRequest request){
        if(starRepository.existsByContentIdAndMemberId(request.getContentId(),request.getMemberId()))
            throw new IllegalArgumentException("이미 존재하는 평점입니다.");
        Member member = memberRepository.findById(request.getMemberId())
                        .orElseThrow(()-> new IllegalArgumentException("회원 id가 올바르지 않습니다."));
        Content content = contentRepository.findById(request.getContentId())
                        .orElseThrow(()-> new IllegalArgumentException("작품 id가 올바르지 않습니다."));
        Star star = Star.builder()
                .star((float)request.getStar())
                .member(member)
                .content(content)
                .build();
        starRepository.save(star);
        renewRating(request.getContentId());
        return new IdResponse(star.getId());
    }

    //평점 정보 id로 조회
    public InfoResponse getStarInfoWithId(Long starId){
        return starRepository.findById(starId)
                .map(InfoResponse::of)
                .orElseThrow(()-> new IllegalArgumentException("평점 id 정보가 올바르지 않습니다."));
    }

    //해당 작품의 평점 전체 조회
    public List<InfoResponse> getAllStarsWithContentId(Long contentId){
        return starRepository.findByContentId(contentId).stream()
                .map(InfoResponse::of)
                .collect(Collectors.toList());
    }

    //평점 수정
    @Transactional
    public void update(Long id, float modifyStar){
        Star star = starRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("평점 id 정보가 올바르지 않습니다."));
        star.updateStar(modifyStar);
        renewRating(star.getContent().getId());
    }

    //평점 삭제
    @Transactional
    public void removeStar(Long id){
        Star star = starRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("평점 id 정보가 올바르지 않습니다."));
        starRepository.delete(star);
        renewRating(star.getContent().getId());
    }

}
