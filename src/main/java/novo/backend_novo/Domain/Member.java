package novo.backend_novo.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity @Getter
@NoArgsConstructor
public class Member {

    @Id @Column(name="member_id")
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private String img;

    @CreationTimestamp
    private Timestamp createdAt;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member (String name, String email, String password, Authority authority){
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority =  authority;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void updateImg(String img){ this.img = img; }
}
