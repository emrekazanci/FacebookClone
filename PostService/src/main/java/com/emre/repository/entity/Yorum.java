package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "yorum")
public class Yorum {
    @Id
    String id;
    String postid;
    String userid;
    String parentid;
    String icerik;
    Long paylasmazamani;
    int begenisayisi;
}
