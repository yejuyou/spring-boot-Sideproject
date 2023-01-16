package com.example.test;


import com.example.test.board.dto.PostDTO;
import com.example.test.board.entity.PostCategory;
import com.example.test.board.repository.PostCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestPostCategory {

    @Autowired
    private PostCategoryRepository postCategoryRepository;


    @Test
    public void addCategory()
    {
        PostCategory postCategory = PostCategory.builder()
                .name("책제목")
                .value("인문")
                .build();


        final PostCategory postResult = postCategoryRepository.save(postCategory);

        PostDTO postDTO = PostDTO.builder()
                .Category(postResult.getName())
                .Category(postResult.getValue())
                .build();

        System.out.println("postDTO = " + postDTO);
    }




}
