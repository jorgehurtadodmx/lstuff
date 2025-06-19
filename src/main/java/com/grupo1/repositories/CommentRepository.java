package com.grupo1.repositories;

import com.grupo1.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCommentContainsIgnoreCase(String comment);
}