package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/oauth/callback")
    public ResponseEntity<?> oauthCallback(@RequestParam("accessToken") String kakaoAccessToken) {
        System.out.println("스프링에서 받은 카카오토큰" + kakaoAccessToken);

        String blogAccessToken = userService.카카오로그인(kakaoAccessToken);
        //토큰을 받는 이유는 내가 만들어서 주는 토큰이기 때문에 이제는 카카오에게 계속 통신하면서 물어보지 않아도 되면 이 토큰으로 오는 요청은 이제 서버에서 검증을 할 수 있다.

        return ResponseEntity.ok().header("Authorization","Bearer " + blogAccessToken).body(new ApiUtil(null));
    }


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {
        String jwt = userService.로그인(reqDTO);
        return ResponseEntity.ok().header("Authorization", "Bearer "+jwt).body(new ApiUtil(null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil(null));
    }
}
