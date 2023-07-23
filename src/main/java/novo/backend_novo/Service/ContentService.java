package novo.backend_novo.Service;

import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Domain.Star;
import novo.backend_novo.Repository.ContentRepository;
import novo.backend_novo.Repository.StarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;

/*작품 저장
* 작품 정보 id로 조회
* 작품 정보 전체 조회
* 작품 수정
* 작품 삭제 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final StarRepository starRepository;

    //작품 저장하기
    @Transactional
    public IdResponse saveContent(Content content){
        contentRepository.save(content);
        return new IdResponse(content.getId());
    }

    //작품 정보 id로 조회하기
    public InfoResponse getContentInfoWithId(Long contentId){
        return contentRepository.findById(contentId)
                .map(InfoResponse::of)
                .orElseThrow(()-> new IllegalArgumentException("작품 정보가 없습니다."));
    }

    //전체 작품 조회
    public List<InfoResponse> getAllContents(){
        return contentRepository.findAll().stream()
                .map(InfoResponse::of)
                .collect(Collectors.toList());
    }

    //작품 수정
    @Transactional
    public void update(Long id, UpdateRequest request){
        Content content = contentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("작품 id가 올바르지 않습니다."));
        content.updateInfo(request);
    }

    //작품 삭제
    @Transactional
    public void removeContent(Long id){
        Content content = contentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("작품 id가 올바르지 않습니다."));

        List<Star> relatedStar = starRepository.findByContentId(id);
        for(int i=0; i<relatedStar.size(); i++){
            starRepository.delete(relatedStar.get(i));
        }

        contentRepository.delete(content);

    }
}
