package com.example.test.board.controller;


import com.example.test.Common.payload.SuccessResponse;
import com.example.test.board.dto.PostDTO;
import com.example.test.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;

    @GetMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse getPost(@PathVariable(name = "id") Long id) {
        PostDTO post = postService.getPost(id);

        return SuccessResponse.success(post);

    }

    @PostMapping("/post")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse createPost(@Valid @RequestBody PostDTO postDto){
        PostDTO post =postService.createPost(postDto);
        return SuccessResponse.success(post);
    }

    @PutMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long postId){
        PostDTO postDTO1 = postService.updatePost(postId, postDTO);
        return  SuccessResponse.success(postDTO1);

    }

    @DeleteMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public  SuccessResponse<String>deletePost(@PathVariable(name = "id") Long postId){
        postService.deletePost(postId);
        return SuccessResponse.success(null);


    }




}
