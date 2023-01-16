package com.example.test.board.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostDTO {

    private Long id;
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String contents;
    private Long viewCount;
    private String createdAt;

    @NotBlank(message = "카테고리를 선택해주세요")
    private String Category;
}
