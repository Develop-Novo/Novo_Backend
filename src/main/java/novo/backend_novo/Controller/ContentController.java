package novo.backend_novo.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Response.BasicResponse;
import novo.backend_novo.Response.CommonResponse;
import novo.backend_novo.Service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;

    /*작품 등록*/
    @PostMapping(path = "/new",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> save(
            @RequestBody @Valid SaveRequest request
    ){
        String keyword = "";
        if(request.getKeyword()!=null) {
            for (String key : request.getKeyword())
                keyword += key + ",";
        }
        Content content = Content.builder()
                .title(request.getTitle())
                .writer(request.getWriter())
                .introduction(request.getIntroduction())
                .price(request.getPrice())
                .serialDay(request.getSerialDay())
                .publishedAt(request.getPublishedAt())
                .genre(request.getGenre())
                .keyword(keyword)
                .ageRating(request.getAgeRating())
                .platform(request.getPlatform())
                .build();
        return ResponseEntity.ok()
                .body(new CommonResponse<IdResponse>(contentService.saveContent(content)));
    }

    /*작품 정보 id로 조회*/
    @GetMapping(path = "/id/{content-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findById(
            @PathVariable("content-id") Long contentId
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<InfoResponse>(contentService.getContentInfoWithId(contentId)));
    }

    /*작품 정보 전체 조회*/
    @GetMapping(path = "/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findAll()
    {
        return ResponseEntity.ok()
                .body(new CommonResponse<List>(contentService.getAllContents()));
    }

    /*작품 수정*/
    @PutMapping(path = "/id/{content-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> update(
            @PathVariable("content-id") Long contentId,
            @RequestBody @Valid UpdateRequest request
    ){
        contentService.update(contentId,request);
        return ResponseEntity.ok().build();
    }

    /*작품 삭제*/
    @DeleteMapping(path = "/id/{content-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> remove(
            @PathVariable("content-id") Long contentId
    ){
        contentService.removeContent(contentId);
        return ResponseEntity.ok().build();
    }
}
