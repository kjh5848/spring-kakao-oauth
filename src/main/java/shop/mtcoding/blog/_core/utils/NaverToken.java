package shop.mtcoding.blog._core.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shop.mtcoding.blog.user.NaverCodeResponse;

@Controller
public class NaverToken {

    public NaverCodeResponse.TokenDTO getNaverToken(String code, RestTemplate restTemplate) {
        System.out.println("code = " + code);
        HttpEntity<MultiValueMap<String, String>> request = createTokenRequest(code);
        System.out.println("request = " + request);
        ResponseEntity<NaverCodeResponse.TokenDTO> response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                NaverCodeResponse.TokenDTO.class);

        return response.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "hagbQ0neJqK_tY4moEfX");
        body.add("client_secret", "9UeKuj_kcO");
        body.add("code", code);
        body.add("state", "test");

        return new HttpEntity<>(body,headers);
    }


    private HttpEntity<MultiValueMap<String, String>> createUserRequest(String accessToken) {
        System.out.println("accessToken = " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken);

        return new HttpEntity<>(headers);
    }


    public NaverCodeResponse.NaverUserDTO getNaveUser(String accessToken, RestTemplate restTemplate) {

        HttpEntity<MultiValueMap<String, String>> request = createUserRequest(accessToken);
        System.out.println("request = 22" + request);

        ResponseEntity<NaverCodeResponse.NaverUserDTO> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                NaverCodeResponse.NaverUserDTO.class);
        System.out.println("response = " + response.getBody());

        return response.getBody();
    }





}
