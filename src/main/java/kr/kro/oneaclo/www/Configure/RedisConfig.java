package kr.kro.oneaclo.www.Configure;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@NoArgsConstructor
@Configuration
// @EnableRedisRepositories
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(host);
            redisStandaloneConfiguration.setPort(port);
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

            LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);

            return factory;
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure Redis", e);
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
    /*
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration cConfig = LettuceClientConfiguration.builder().readFrom(ReadFrom.MASTER_PREFERRED).useSsl().and().commandTimeout(Duration.ofMillis(60000)).build();
        // LettuceClientConfiguration cConfig = LettuceClientConfiguration.builder().readFrom(ReadFrom.MASTER_PREFERRED).build();
        RedisStandaloneConfiguration sConfig = new RedisStandaloneConfiguration(host, port);
        // config.setPassword(password);
        return new LettuceConnectionFactory(sConfig, cConfig);
    }
    */
}
