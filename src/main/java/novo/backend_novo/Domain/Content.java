package novo.backend_novo.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novo.backend_novo.DTO.ContentDTO;

import java.time.LocalDate;

import static novo.backend_novo.DTO.ContentDTO.*;

@Entity @Getter
@NoArgsConstructor
public class Content {

    @Id @Column(name="content_id")
    @GeneratedValue
    private Long id;
    private String title; //제목
    private String writer; //작가
    private String introduction; //작품 소개
    private String price; //가격
    private String serialDay; //연재날짜
    private LocalDate publishedAt; //출판일자
    private String genre; //장르
    private String keyword; //키워드(','기준으로 저장)
    private String ageRating; //나이제한
    private String platform; //연재 플랫폼

    @Builder
    public Content( String title, String writer, String introduction,
                    String price, String serialDay, LocalDate publishedAt,
                    String genre, String keyword, String ageRating, String platform) {
        this.title = title;
        this.writer = writer;
        this.introduction = introduction;
        this.price = price;
        this.serialDay = serialDay;
        this.publishedAt = publishedAt;
        this.genre = genre;
        this.keyword = keyword;
        this.ageRating = ageRating;
        this.platform = platform;
    }

    public void updateInfo(UpdateRequest request) {
        this.title = request.getTitle();
        this.writer = request.getWriter();
        this.introduction = request.getIntroduction();
        this.price = request.getPrice();
        this.serialDay = request.getSerialDay();
        this.publishedAt = request.getPublishedAt();
        this.genre = request.getGenre();
        this.keyword = request.getKeyword().toString();
        this.ageRating = request.getAgeRating();
        this.platform = request.getPlatform();
    }
}
