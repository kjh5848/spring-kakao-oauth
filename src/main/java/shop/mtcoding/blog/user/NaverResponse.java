package shop.mtcoding.blog.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

public class NaverResponse {

    @Data
    public static class NaverUserDTO {
        @JsonProperty("response")
        private Response response;

        @Data
        public static class Response {
            private String id;
            private String email;
            private String name;
        }
    }
}