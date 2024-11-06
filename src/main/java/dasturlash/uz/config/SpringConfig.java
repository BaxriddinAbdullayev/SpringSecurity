package dasturlash.uz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchini identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.

        String password = "1234";
        System.out.println("Using generated security password mazgi: " + password);

        UserDetails user = User.builder()
                .username("mazgi")
                .password("{bcrypt}$2a$10$oYGVquTuP1Tw4nU9SJz0xeZZogZte3ajuJQBPDMbYiuo9nHtuBtH6")
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password("{noop}1234")
                .roles("ADMIN")
                .build();

        final DaoAuthenticationProvider authenticationProvider =new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user,user2));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization - Foydalanuvchining tizimdagi huquqlarini tekshirish.
        // Ya'ni foydalanuvchi murojaat qilayotgan API-larni ishlatishga ruxsat bor yoki yo'qligini tekshirish.

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/task").permitAll()
                    .requestMatchers(HttpMethod.GET,"/task/*").permitAll() // /task/ /task/{aa-bb-ss-dd} /task/active
                    .requestMatchers("/task/finished/all").hasAnyRole("USER","ADMIN")
//                    .requestMatchers(HttpMethod.GET,"/task/**").permitAll() // /task/ /task/{aa-bb-ss-dd} /task/active  /task/active/all  /task/active/all/ball/dkfdjf
//                    .requestMatchers("/task/finished/all","/task/my/all").permitAll()
                    .anyRequest()
                    .authenticated();
        }).formLogin(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
