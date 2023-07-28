package com.wojto.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import java.util.UUID;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sport {

    @Id
    @GeneratedValue(strategy = UNIQUE)
    private String id;

    @Field
    private String sportName;

    public static Sport of(String sportName) {
        Sport sport = new Sport();
        sport.setSportName(sportName);
        sport.setId(UUID.randomUUID().toString());
        return sport;
    }
}
