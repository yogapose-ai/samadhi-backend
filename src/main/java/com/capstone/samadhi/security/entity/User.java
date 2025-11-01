package com.capstone.samadhi.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String profile;
    private String nickname;
    private String gender;
    private LocalDate birth;
    private float height;
    private float weight;

    public User(String id, String profile) {
        this.id = id;
        this.profile = profile;
    }
}
