package com.example.test.board.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostDTO {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private Long viewCount;
    private String createdAt;

}
