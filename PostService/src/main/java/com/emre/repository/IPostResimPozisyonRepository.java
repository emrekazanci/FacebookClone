package com.emre.repository;

import com.emre.repository.entity.PostResimPozisyon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostResimPozisyonRepository extends MongoRepository<PostResimPozisyon, String> {
}
