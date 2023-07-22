package novo.backend_novo.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Platform {
    //[문피아, 조아라, 리디북스, 네이버시리즈, 카카오페이지]
    MUNPIA, JOARA, RIDI_BOOKS, NAVER_SERIES, KAKAO_PAGE;
    @JsonCreator
    public static Platform fromPlatform(String val){
        for(Platform platform : Platform.values()){
            if(platform.name().equals(val))
                return platform;
        }
        return null;
    }
}
