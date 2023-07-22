package novo.backend_novo.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum AgeRating {
    //default = AllAges
    AllAges, AdultOnly; //전체 이용가, 청소년 이용 불가
    @JsonCreator
    public static AgeRating fromAgeRating(String val){
        for(AgeRating ageRating : AgeRating.values()){
            if(ageRating.name().equals(val))
                return ageRating;
        }
        return null;
    }
}
