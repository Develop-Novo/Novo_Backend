package novo.backend_novo.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import novo.backend_novo.Domain.Content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentDTO {

    @Getter
    @NoArgsConstructor
    public static class SaveRequest{
        @NotEmpty
        private String title;
        private String writer;
        private String introduction;
        private String price;
        private String serialDay;
        private String publishedAt;
        private String genre;
        private List<String> keyword;
        private String ageRating = "전체이용가";
        private String platform;
        private String link;

        @Builder
        public SaveRequest(String title, String writer, String introduction,
                           String price, String serialDay, String publishedAt,
                           String genre, List<String> keyword, String ageRating,
                           String platform, String link) {
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
            this.link = link;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PlatformRequest{
        private String platform;
        @Builder
        public PlatformRequest(String platform) {
            this.platform = platform;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest{
        private String title;
        private String writer;
        private String introduction;
        private String price;
        private String serialDay;
        private String publishedAt;
        private String genre;
        private List<String> keyword;
        private String ageRating = "전체이용가";
        private String platform;
        private String link;

        @Builder
        public UpdateRequest(String title, String writer, String introduction,
                             String price, String serialDay, String publishedAt,
                             String genre, List<String> keyword, String ageRating,
                             String platform, String link) {
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
            this.link = link;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoResponse{
        private Long id;
        private String title;
        private String writer;
        private String introduction;
        private String price;
        private String serialDay;
        private String publishedAt;
        private String genre;
        private List<String> keyword;
        private String ageRating;
        private String platform;
        private float rating;
        private String coverImg;
        private String detailImg;
        private String link;

        public static InfoResponse of(Content content){
            List<String> keywords = new ArrayList<>();
            if(content.getKeyword()!=null) {
                keywords = Arrays.asList(content.getKeyword().split(","));
            }

            return new InfoResponse(content.getId(),content.getTitle(), content.getWriter(), content.getIntroduction(),
                    content.getPrice(), content.getSerialDay(), content.getPublishedAt(), content.getGenre(), keywords,
                    content.getAgeRating(), content.getPlatform(), content.getRating(), content.getCoverImg(),
                    content.getDetailImg(),content.getLink());
        }
    }
}
