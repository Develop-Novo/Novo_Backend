package novo.backend_novo.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Authority {
     ROLE_USER, ROLE_ADMIN;
     @JsonCreator
     public static Authority fromAuthority(String val){
          for(Authority authority : Authority.values()){
               if(authority.name().equals(val))
                    return authority;
          }
          return null;
     }
}
