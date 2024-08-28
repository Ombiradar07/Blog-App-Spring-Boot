package com.osmblog.Repository;

import com.osmblog.Entities.Category;
import com.osmblog.Entities.Post;
import com.osmblog.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category,Pageable pageable);

    Page<Post> findByPostTitleContaining(String postTitle, Pageable pageable);

}
