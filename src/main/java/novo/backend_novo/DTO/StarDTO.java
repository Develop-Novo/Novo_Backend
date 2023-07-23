package novo.backend_novo.DTO;

import jakarta.validation.constraints.Max;
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
        @NotNull @Positive @Max(value = 10)
        private float star;

        @Builder
        public SaveRequest(Long memberId, Long contentId, float star) {
            this.memberId = memberId;
            this.contentId = contentId;
            this.star = star;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest{
        @NotNull @Positive @Max(value = 10)
        private float star;

        @Builder
        public UpdateRequest(float star) {
            this.star = star;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoResponse {
        private Long id;
        private Long memberId;
        private Long contentId;
        private float star;

        public static InfoResponse of(Star star) {
            return new InfoResponse(star.getId(), star.getMember().getId(), star.getContent().getId(), star.getStar());
        }
    }
}
