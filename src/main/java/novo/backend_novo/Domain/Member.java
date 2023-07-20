package novo.backend_novo.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static novo.backend_novo.Domain.Authority.ROLE_USER;

@Entity @Getter
@NoArgsConstructor
public class Member {

    @Id @Column(name="member_id")
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority = ROLE_USER; //default = user

    @Builder
    public Member (String name, String email, String password, Authority authority){
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority =  authority;
    }

    public void updateInfo(String name) {
        this.name = name;
    }
}
