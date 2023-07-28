package com.wojto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DirectoryContents {

    private int numberOfFiles;
    private long kiloBytes;

    public void addFile() {
        numberOfFiles++;
    }

    public void addKiloBytes(long kiloBytes) {
        this.kiloBytes += kiloBytes;
    }
}
