package com.example.warehouse.config;

import com.example.warehouse.model.entity.Token;
import com.example.warehouse.util.Contants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;



import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/warehouse/login")    // ← bo CSRF chỉ cho login
                .ignoringRequestMatchers("/warehouse/register")
        );
        // phan quyen truy cap
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/warehouse/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated());
        http.formLogin(withDefaults());
        //cau hinh su dung jwt
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                jwt.jwtAuthenticationConverter(new KeycloakRoleConverter())
        ));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(Contants.REALMS_URL);
    }
    @Bean
    public ProducerFactory<String, Token> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 👈 đổi nếu Kafka khác port
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        JsonSerializer<Token> jsonSerializer = new JsonSerializer<>();
        jsonSerializer.setAddTypeInfo(false); // 👈 để gửi JSON thuần, không @class

        DefaultKafkaProducerFactory<String, Token> factory = new DefaultKafkaProducerFactory<>(props);
        factory.setValueSerializer(jsonSerializer);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Token> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}