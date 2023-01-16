package com.example.test.board.repository;

import com.example.test.board.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory,Long> {
    PostCategory findByName(String name);
}
