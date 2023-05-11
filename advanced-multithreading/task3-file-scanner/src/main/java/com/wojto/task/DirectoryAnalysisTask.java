package com.wojto.task;

import com.wojto.model.DirectoryContents;

import java.io.File;
import java.util.concurrent.Callable;

public class DirectoryAnalysisTask implements Callable<DirectoryContents> {

    private File directory;

    public DirectoryAnalysisTask(File directory) {
        this.directory = directory;
    }

    @Override
    public DirectoryContents call() throws Exception {

        DirectoryContents directoryContents = new DirectoryContents();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    directoryContents.addFile();
                    directoryContents.addKiloBytes(file.length() / 1024 );
                }
            }
        }

        return directoryContents;
    }
}
