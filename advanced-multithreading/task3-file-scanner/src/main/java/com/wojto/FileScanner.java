package com.wojto;

import com.wojto.model.DirectoryContents;
import com.wojto.task.DirectoryAnalysisTask;
import com.wojto.util.InfoPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
public class FileScanner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(FileScanner.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(FileScanner.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {

        File file = new File(args[0]);
        LOG.info("Provided file path: " + file.getPath());
        List<File> allDirectories = null;
        List<Future<DirectoryContents>> directoryCheckResults = null;

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        createCancellingThread();

        if (file.isDirectory()) {
            LOG.info("Provided file is a directory. Extracting all subdirectories.");

            allDirectories = getAllDirectories(file);
            LOG.info("List of subdirectories created. Proceeding to create threads calculating contents.");

            directoryCheckResults = submitTasksAndGetListOfFutures(allDirectories, executor);
            LOG.info("All threads created. Calculating...");
            System.out.println("Press \"C\" to cancel calculating.c");
        } else {
            LOG.error("Given file is not a directory.");
            System.exit(1);
        }

        int numOfDoneTasks = 0;
        int numOfAllDirectories = allDirectories.size();
        while (numOfDoneTasks < numOfAllDirectories) {
            numOfDoneTasks = 0;
            for (Future<DirectoryContents> future : directoryCheckResults) {
                if (future.isDone())
                    numOfDoneTasks++;
            }
            InfoPrinter.printDonePercentage(numOfDoneTasks, numOfAllDirectories);
        }

        LOG.info("Directory scanning complete. Summing up results.");

        int totalNumberOfFiles = 0;
        long totalKiloBytes = 0;

        try {
            for (Future<DirectoryContents> future : directoryCheckResults) {
                totalNumberOfFiles += future.get().getNumberOfFiles();
                totalKiloBytes += future.get().getKiloBytes();
            }
        } catch (Exception e) {
            LOG.error("Something went wrong during summing up Directory Contents.");
            System.exit(2);
        }

        LOG.info("Calculations complete. Printing results.");

        InfoPrinter.printTotalStatistics(numOfAllDirectories, totalNumberOfFiles, (double) totalKiloBytes);
        System.exit(0);
    }

    private static void createCancellingThread() {
        Thread cancelThread = new Thread(() -> {
            while (true) {
                char input = System.console().readLine().charAt(0);
                if (input == 'c') {
                    System.exit(0);
                }
            }
        });
        cancelThread.setDaemon(true);
        cancelThread.start();
    }

    private static List<Future<DirectoryContents>> submitTasksAndGetListOfFutures(List<File> allDirectories, ExecutorService executor) {
        List<Future<DirectoryContents>> directoryCheckResults;
        directoryCheckResults = new ArrayList<>();
        for (File directory : allDirectories) {
            directoryCheckResults.add(executor.submit(new DirectoryAnalysisTask(directory)));
        }
        return directoryCheckResults;
    }

    public static List<File> getAllDirectories(File directory) {
        List<File> directories = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    directories.add(file);
                    directories.addAll(getAllDirectories(file));
                }
            }
        }
        return directories;
    }

}