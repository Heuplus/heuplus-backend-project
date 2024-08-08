package com.bola.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_setting")
/*
   Setting entity holds a user's setting data
*/
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String key;
    private String value;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
