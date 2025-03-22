package clovar.howkiki.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    /* S3 클라이언트 객체를 생성하고 구성하는 빈 정의 */
    @Bean
    public AmazonS3 amazonS3() {
        // AWS접근 위해 액세스 키와 시크릿 키를 사용해 BasicAWSCredentials 객체 생성
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // AmazonS3ClientBuilder 를 사용하여 클라이언트 객체 구성
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

}
