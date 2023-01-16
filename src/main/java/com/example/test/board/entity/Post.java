package com.example.test.board.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
@Id
@GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory category;



    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContents(String contents) {
        this.contents = contents;
    }


    public void MappingCategory(PostCategory postCategory){
        this.category =postCategory;
        postCategory.mappingPost(this);
    }



}
