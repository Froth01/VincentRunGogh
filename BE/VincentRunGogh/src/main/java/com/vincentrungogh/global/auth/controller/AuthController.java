package com.vincentrungogh.global.auth.controller;


import com.vincentrungogh.global.auth.service.AuthService;
import com.vincentrungogh.global.auth.service.dto.request.LoginRequest;
import com.vincentrungogh.global.auth.service.dto.request.SignupRequest;
import com.vincentrungogh.global.auth.service.dto.response.LoginResponse;
import com.vincentrungogh.global.util.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "사용자 로그인하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인에 성공하셨습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 페이지입니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "500", description = "로그인에 실패하셨습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class)))
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        log.info("AuthController : 로그인 시작");
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultDto.res(HttpStatus.OK.value(),
                        "로그인에 성공하셨습니다." , response));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        authService.signup(signupRequest);
        return ResponseEntity.ok("");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        authService.signup(signupRequest);
        return ResponseEntity.ok("");
    }
}
