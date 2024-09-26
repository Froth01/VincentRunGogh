package com.vincentrungogh.domain.drawing.controller;

import com.vincentrungogh.domain.drawing.service.DrawingService;
import com.vincentrungogh.domain.drawing.service.dto.response.DrawingListResponseDto;
import com.vincentrungogh.domain.drawing.service.dto.response.DrawingResponseDto;
import com.vincentrungogh.domain.drawing.service.facade.DrawingFacade;
import com.vincentrungogh.domain.route.service.dto.response.FindRouteResponseDto;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import com.vincentrungogh.global.util.CommonSwaggerResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drawings")
@RequiredArgsConstructor
@Slf4j
public class DrawingController {

    private final DrawingFacade drawingFacade;
    private final DrawingService drawingService;

    //드로잉 조회
    @Operation(summary = "드로잉 조회", description = "미완성이거나 완료된 드로잉 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "드로잉 조회에 성공했습니다.",
                    content = @Content(schema = @Schema(implementation = FindRouteResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "조회할 데이터가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "500", description = "드로잉 조회하는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class)))
    })
    @CommonSwaggerResponse.CommonResponses
    @GetMapping("")
    public ResponseEntity<ResultDto> getDrawingList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String type) {
        log.info("드로잉 조회: "+type);

        DrawingListResponseDto responseDto = drawingFacade.getDrawingList(userPrincipal.getId(), type);

        return ResponseEntity.status(HttpStatus.OK).body(ResultDto.res(HttpStatus.OK.value(), "루트 조회에 성공했습니다.", responseDto));
    }

    //특정 드로잉 조회
    @Operation(summary = "특정 드로잉 조회", description = "id를 통한 특정 드로잉 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "드로잉 조회에 성공했습니다.",
                    content = @Content(schema = @Schema(implementation = FindRouteResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "조회할 데이터가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "500", description = "드로잉 조회하는데 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class)))
    })
    @CommonSwaggerResponse.CommonResponses
    @GetMapping("/{drawingId}")
    public ResponseEntity<ResultDto> getDrawing(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable int drawingId) {
        log.info("특정 드로잉 조회: "+drawingId);

        DrawingResponseDto responseDto = drawingService.getDrawing(userPrincipal.getId(), drawingId);

        return ResponseEntity.status(HttpStatus.OK).body(ResultDto.res(HttpStatus.OK.value(), "루트 조회에 성공했습니다.", responseDto));
    }
}
