package com.wojto.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserAccount {

    @Id
    private String id;

    @Field
    private String email;
    @Field
    private String fullName;
    @Field
    private LocalDateTime birthDate;
    @Field
    private Gender gender;

    enum Gender {
        MALE, FEMALE, OTHER;
    }
}
