package novo.backend_novo.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novo.backend_novo.Domain.Star;

public class StarDTO {

    @Getter
    @NoArgsConstructor
    public static class SaveRequest {
        @NotNull
        private Long memberId;
        @NotNull
        private Long contentId;
        @NotNull @Positive @DecimalMax(value = "10")
        private int star;

        @Builder
        public SaveRequest(Long memberId, Long contentId, int star) {
            this.memberId = memberId;
            this.contentId = contentId;
            this.star = star;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest{
        @NotNull @Positive @DecimalMax(value = "10")
        private int star;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoResponse {
        private Long id;
        private Long memberId;
        private Long contentId;
        private int star;

        public static InfoResponse of(Star star) {
            return new InfoResponse(star.getId(), star.getMember().getId(), star.getContent().getId(), star.getStar());
        }
    }
}
