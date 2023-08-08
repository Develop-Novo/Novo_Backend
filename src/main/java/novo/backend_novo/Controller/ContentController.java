package novo.backend_novo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novo.backend_novo.Response.BasicResponse;
import novo.backend_novo.Response.CommonResponse;
import novo.backend_novo.Service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.ContentDTO.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "작품", description = "작품 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;

    /*작품 등록*/
    @Operation(summary = "소설 등록")
    @PostMapping(path = "/new",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> save (
            @RequestPart(value = "request") @Valid SaveRequest request,
            @RequestPart(value = "coverImg", required = false) MultipartFile coverImg,
            @RequestPart(value = "detailImg", required = false) MultipartFile detailImg
            ) throws IOException {
        return ResponseEntity.ok()
                .body(new CommonResponse<IdResponse>(contentService.saveContent(request,coverImg, detailImg)));
    }

    /*작품 정보 id로 조회*/
    @Operation(summary = "소설 정보 id로 조회")
    @GetMapping(path = "/id/{content-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findById(
            @PathVariable("content-id") Long contentId
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<InfoResponse>(contentService.getContentInfoWithId(contentId)));
    }

    /*작품 플랫폼으로 조회*/
    @Operation(summary = "소설 정보 플랫폼으로 조회")
    @GetMapping(path = "/platform/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findByPlatform(
            @RequestBody @Valid PlatformRequest request
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<List>(contentService.getContentInfoWithPlatform(request.getPlatform())));
    }

    /*작품 정보 전체 조회*/
    @Operation(summary = "소설 정보 전체 조회")
    @GetMapping(path = "/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findAll()
    {
        return ResponseEntity.ok()
                .body(new CommonResponse<List>(contentService.getAllContents()));
    }

    /*작품 수정*/
    @Operation(summary = "소설 정보 수정")
    @PutMapping(path = "/id/{content-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> update(
            @PathVariable("content-id") Long contentId,
            @RequestBody @Valid UpdateRequest request
    ){
        contentService.update(contentId,request);
        return ResponseEntity.ok().build();
    }

    /*작품 삭제*/
    @Operation(summary = "소설 삭제")
    @DeleteMapping(path = "/id/{content-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> remove(
            @PathVariable("content-id") Long contentId
    ){
        contentService.removeContent(contentId);
        return ResponseEntity.ok().build();
    }
}
