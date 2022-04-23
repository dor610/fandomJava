package com.fandom.repository;

import com.fandom.model.Post;
import com.fandom.model.PostState;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    public Page<Post> findPostsByAuthor(String author, Pageable pageable);

    @Query(value = "{'_id': ?0}")
    public Post findPost(String id);

    @Query(value = "{'author': ?0, state: ?1}", sort = "{'timestamp': -1}")
    public Page<Post> getPostByAuthorAndState(String author, PostState state, Pageable pageable);

    @Query(value = "{'state': ?0}", sort = "{'timestamp': -1}")
    public Page<Post> getPostByState(PostState state, Pageable pageable);


    public List<Post> findPostsByAuthor(String author);

    //public Page<Post> findPostsByState(PostState state, Pageable pageable);

    public int countPostsByAuthorAndState(String author, PostState state);
}
