package com.example.test.board.service;

import com.example.test.board.dto.PostDTO;
import com.example.test.board.entity.Post;
import com.example.test.board.repository.PostCategoryRepository;
import com.example.test.board.repository.PostRepository;
import com.example.test.util.custom_exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final PostCategoryRepository postCategoryRepository;

    public PostDTO getPost(Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post findPost = byId.orElseThrow(() -> new PostNotFound("해당 포스트가 존재하지 않습니다."));


        return PostDTO.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .contents(findPost.getContents())
                .createdAt(findPost.getCreateAt().toString())
                .build();


    }

    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .contents(postDTO.getContents())
                .category(postCategoryRepository.findByName(postDTO.getCategory()))
                .createAt(LocalDateTime.now())
                .build();

        Post save = postRepository.save(post);
        PostDTO postDTOResponse = PostDTO.builder()
                .id(save.getId())
                .build();

        return postDTOResponse;

    }

    @Transactional
    public PostDTO updatePost(Long postId, PostDTO postDTO) {

        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.orElseThrow(() ->new PostNotFound("해당 포스트가 존재하지 않습니다."));
          post.changeTitle(postDTO.getTitle());
          post.changeContents(postDTO.getContents());

        return PostDTO.builder()
                  .id(post.getId())
                  .build();
    }
@Transactional
    public void deletePost(Long postId) {

        Optional<Post>byId =postRepository.findById(postId);
        Post post =byId.orElseThrow(()->new PostNotFound("해당 포스트가 존재하지 않습니다."));
         postRepository.delete(post);
    }

}
