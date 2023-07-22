package novo.backend_novo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import novo.backend_novo.Domain.Authority;
import novo.backend_novo.Domain.Member;

public class MemberDTO {
    @Getter @Setter
    public static class JoinRequest {
        private String name;
        @NotEmpty(message = "이메일을 입력하세요.") @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotEmpty(message = "비밀번호를 입력하세요.")
        private String password;
        private Authority authority=Authority.ROLE_USER;
    }

    @Getter @Setter
    public static class loginRequest {
        @NotEmpty(message = "이메일을 입력하세요.") @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotEmpty(message = "비밀번호를 입력하세요.")
        private String password;
    }

    @Getter @Setter
    public static class UpdateRequest{
        @NotEmpty(message = "이름을 입력하세요.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InfoResponse{
        private Long id;
        private String name;
        private String email;
        public static InfoResponse of(Member member){
            return new InfoResponse(member.getId(),member.getName(),member.getEmail());
        }
    }
}
