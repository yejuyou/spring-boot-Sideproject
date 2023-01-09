package com.example.test;

import com.example.test.board.dto.PostDTO;
import com.example.test.board.entity.Post;
import com.example.test.board.repository.PostRepository;
import com.example.test.board.service.PostService;
import com.example.test.util.custom_exception.PostNotFound;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;



    @Test
    public void addPost() {

        final Post post = Post.builder()
                .title("title-2023년-1월-5일-XPTMXM")
                .contents("contents-2023년-1월-5일-XPTMXM")
                .createAt(LocalDateTime.now())
                .build();


        final Post result = postRepository.save(post);

        PostDTO postDto = PostDTO.builder().
                title(result.getTitle())
                .contents(result.getContents())
                .createdAt(String.valueOf(result.getCreateAt()))
                .build();

        System.out.println("postDto = " + postDto);
    }


    @Test
    public void UpdatePost() {

        Post postTitle = postRepository.findById(3L).get();
        Post postContent = postRepository.findById(3L).get();
        postTitle.changeTitle("title change");
        postContent.changeContents("contents change");

        final Post post = postRepository.save(postTitle);
        final Post postCotent = postRepository.save(postContent);

        PostDTO postDto = PostDTO.builder().
                title(String.valueOf(postTitle))
                .contents(String.valueOf(postCotent))
                .createdAt(String.valueOf(post.getCreateAt()))
                .build();

        System.out.println("postDto = " + postDto);


    }


    @Test
    public void deletePost() {

        Post postNo = postRepository.findById(3L).get();
        postRepository.delete(postNo);

        assertThatThrownBy(() -> {
            postService.getPost(3L);
        }).isInstanceOf(PostNotFound.class)
                .hasMessage("해당 포스트가 존재하지 않습니다.");
    }


    @Test
    void contextLoads() {
    }

}
