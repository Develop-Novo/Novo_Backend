package novo.backend_novo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import novo.backend_novo.Domain.Member;

public class CommonDTO {
    //Id 반환
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdResponse{
        private Long id;
        public static IdResponse of(Member member){
            return new IdResponse(member.getId());
        }
    }
}
