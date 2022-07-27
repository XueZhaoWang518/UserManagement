package com.bezkoder.springjwt.entity;
import com.bezkoder.springjwt.models.ERole;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ERole name;


    public Role() {

    }
}
