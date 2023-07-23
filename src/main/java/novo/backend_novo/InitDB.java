package novo.backend_novo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import novo.backend_novo.Domain.Authority;
import novo.backend_novo.Domain.Content;
import novo.backend_novo.Domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit(){
            System.out.println("<<<<<<<<<<<  INITIALIZE DATABASE  >>>>>>>>>>>>" );
            System.out.println("====  INITIALIZE MEMBER DATABASE  ====" );
            Member member1 = getMember("김민수","minsoo234@gmail.com","alstn98!");
            Member member2 = getMember("박지은","jieunPark1@naver.com","wlkdmf?1");
            Member member3 = getMember("임수연","tndus124@gmail.com","aalsfjk091i4@");
            Member member4 = getMember("김도형","dododo000@hanmail.net","sdfkja2lkw");
            Member member5 = getMember("박지성","jjjjjji@gmail.com","wklgjwlgjt!3@f");
            Member member6 = getMember("구종만","mimi222@gmail.com","9999j@ng");
            Member member7 = getMember("이동현","dhdhdh46488@gmail.com","ehgdugdlWkd");
            Member member8 = getMember("김윤하","yoonhaWkd@naver.com","aslkjf12k@");
            Member member9 = getMember("허유정","imyou@hanmail.net","wwjddlwDPQm$");
            Member member10 = getMember("박찬경","changyeong@gmail.com","chaneeeeeeee2");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);
            em.persist(member7);
            em.persist(member8);
            em.persist(member9);
            em.persist(member10);

            System.out.println("====  INITIALIZE CONTENT DATABASE  ====" );
            Content content1 = getContent("화산귀환","비가","대 화산파 13대 제자. 천하삼대검수(天下三代劍手). 매화검존(梅花劍尊) 청명(靑明)\n" +
                    "천하를 혼란에 빠뜨린 고금제일마 천마(天魔)의 목을 치고 십만대산의 정상에서 영면. 백 년의 시간을 뛰어넘어 아이의 몸으로 다시 살아나다. ","100원/회차 당",
                    "월,화,수,목,금", LocalDate.of(2019,4,25),"무협","생존,생환,성장,이능력","전체이용가","네이버 시리즈");
            Content content2 = getContent("전지적 독자 시점","싱숑","[오직 나만이, 이 세계의 결말을 알고 있다.]\n" +
                    "무려 3149편에 달하는 장편 판타지 소설, '멸망한 세계에서 살아남는 세 가지 방법'이 현실이 되어버렸다. \n" +
                    "그리고 그 작품을 완독한 이는 단 한 명뿐이었다.","100원/회차 당","일,월,수,금",LocalDate.of(2018,5,21),
                    "현대판타지","게임,독자,소설","전체이용가","네이버 시리즈");
            Content content3 = getContent("데뷔 못 하면 죽는 병 걸림","100원/회차 당","4년차 공시생,\n" +
                    "낯선 몸에 빙의해 3년 전으로 돌아왔다.\n" +
                    "그리고 그의 눈앞에 나타난 갑작스러운 상태창의 협박!\n" +
                    "[돌발!]\n" +
                    "[상태이상 : '데뷔가 아니면 죽음을' 발생!]\n" +
                    "돌연사 위협 때문에\n" +
                    "팔자에도 없던 아이돌에 도전하게 된\n" +
                    "주인공의 대환장 일지.\n","100원/회차 당","월,화,수,목,금",LocalDate.of(2021,1,11),
                    "현대판타지","전문직,빙의,회귀,천재,노력,성장,가수","전체이용가","카카오 페이지");
            Content content4 = getContent("사내 맞선","해화","안 예쁜 곳 빼고 다 예쁜 평범한 회사원, 신하리.\n" +
                    "돈이 궁해, 결혼하기 싫어하는 친구 대신 맞선을 봤다. 목표는 거절!\n" +
                    "맞선 소리가 쏙 들어가도록 나쁜 인상을 줄 것!","100원/회차 당","월,수,금",LocalDate.of(2017,8,03),
                    "로맨스","로맨스,직장,상사,맞선,재벌3세","전체이용가","카카오 페이지");
            Content content5 = getContent("남주의 전부인이 되려 했는데","정푸른","Q. 어릴 적 첫사랑을 재회했습니다. 근데 날 1년간 찾았다고 하고, 나도 모르는 내 애를 데리고 왔네요.\n" +
                    "막장 같은 이 상황이 내 이야기라는 게 믿을 수가 없다.\n" +
                    "아카데미 시절 대공자를 짝사랑했다. 하지만 신분의 격차로 깔끔히 포기하고 졸업 후 귀농했다.\n" +
                    "그 뒤로 다신 볼 수 없을 줄 알았는데.","100원/회차 당","월,화,수,목,금",LocalDate.of(2023,07,20),
                    "로맨스판타지","기억상실,육아물,집착남,능력녀,외유내강,동거물","전체이용가","카카오 페이지");
            em.persist(content1);
            em.persist(content2);
            em.persist(content3);
            em.persist(content4);
            em.persist(content5);

            System.out.println("<<<<<<<<<<<  END INITIALIZE DATABASE  >>>>>>>>>>>>" );
        }

        private Member getMember(String name, String email, String password){
            return Member.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .authority(Authority.ROLE_USER)
                    .build();
        }

        private Content getContent( String title, String writer, String introduction,
                                    String price, String serialDay, LocalDate publishedAt,
                                    String genre, String keyword, String ageRating, String platform){
            return  Content.builder()
                    .title(title)
                    .writer(writer)
                    .introduction(introduction)
                    .price(price)
                    .serialDay(serialDay)
                    .publishedAt(publishedAt)
                    .genre(genre)
                    .keyword(keyword)
                    .ageRating(ageRating)
                    .platform(platform)
                    .build();
        }

    }
}
