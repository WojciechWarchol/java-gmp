package com.wojto.model;

import lombok.*;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
@Builder @Getter @Setter
public class Message {

    @NonNull
    private final String content;

    @Override
    public String toString() {
        return content;
    }
}
