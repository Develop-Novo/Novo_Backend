package novo.backend_novo.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Star {

    @Id
    @Column(name="star_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int star;

    @Builder
    public Star(Content content, Member member, int star) {
        this.content = content;
        this.member = member;
        this.star = star;
    }

    public void updateStar(int modifyStar) {
        this.star = modifyStar;
    }
}
