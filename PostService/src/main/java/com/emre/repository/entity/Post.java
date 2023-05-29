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
@Document(collection = "post") //Bu bir nosql döküman tipi. ilişkiselde entity ile işaretlerken burda bununla..
public class Post {
    @Id
    String id;
    String userid;
    Long paylasimzamani;
    /**
     * Integer -> boşsa null döner
     * int -> boşsa, default 0 döner.
     */
    int begenisayisi;
    String aciklama;
    int yorumsayisi;
}
