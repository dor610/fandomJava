package com.fandom.repository;

import com.fandom.model.Comment;
import com.fandom.model.PostState;
import com.fandom.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

    public int countCommentByTargetId(String targetId);

    public Page<Comment> getCommentsByTargetIdAndState(String targetId, PostState state, Pageable pageable);

    public Comment findCommentByAuthorAndTargetId(String author, String targetId);
}
