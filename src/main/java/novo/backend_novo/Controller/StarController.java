package novo.backend_novo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novo.backend_novo.Response.BasicResponse;
import novo.backend_novo.Response.CommonResponse;
import novo.backend_novo.Service.StarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static novo.backend_novo.DTO.CommonDTO.*;
import static novo.backend_novo.DTO.StarDTO.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "평점", description = "평점 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/star")
public class StarController {
    private final StarService starService;

    /*평점 등록*/
    @Operation(summary = "평점 등록")
    @PostMapping(path = "/new",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> save(
            @RequestBody @Valid SaveRequest request
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<IdResponse>(starService.saveStar(request)));
    }

    /*id로 평점 조회*/
    @Operation(summary = "id로 평점 조회")
    @GetMapping(path = "/id/{star-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findById(
            @PathVariable("star-id") Long starId
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<InfoResponse>(starService.getStarInfoWithId(starId)));
    }

    /*해당 작품의 모든 평점 조회*/
    @Operation(summary = "해당 소설의 모든 평점 조회")
    @GetMapping(path = "/contentId/{content-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findByContentId(
            @PathVariable("content-id") Long contentId
    ){
        return ResponseEntity.ok()
                .body(new CommonResponse<List<InfoResponse>>(starService.getAllStarsWithContentId(contentId)));
    }

    /*평점 수정*/
    @Operation(summary = "평점 수정")
    @PutMapping(path = "/id/{star-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> modifyStar(
            @RequestBody @Valid UpdateRequest request,
            @PathVariable("star-id") Long starId
    ){
        starService.update(starId, (float)request.getStar());
        return ResponseEntity.ok().build();
    }

    /*평점 삭제*/
    @Operation(summary = "평점 삭제")
    @DeleteMapping(path = "/id/{star-id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> deleteStar(
            @PathVariable("star-id") Long starId
    ){
        starService.removeStar(starId);
        return ResponseEntity.ok().build();
    }

}
