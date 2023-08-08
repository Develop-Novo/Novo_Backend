package novo.backend_novo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendNovoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendNovoApplication.class, args);
	}

	//CORS 에러 해결
	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")
						.allowedOrigins("http://localhost:8080")
						.maxAge(3600); // 3600초 동안 preflight 결과를 캐시에 저장;
			}
		};
	}
}
