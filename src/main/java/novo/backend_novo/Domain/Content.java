package novo.backend_novo.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating; //나이제한
    @Enumerated(EnumType.STRING)
    private Platform platform; //연재 플랫폼

    @Builder
    public Content( String title, String writer, String introduction,
                    String price, String serialDay, LocalDate publishedAt,
                    String genre, String keyword, AgeRating ageRating, Platform platform) {
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
}
