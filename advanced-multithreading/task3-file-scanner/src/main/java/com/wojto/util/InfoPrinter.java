package com.wojto.util;

public class InfoPrinter {

    public static void printDonePercentage(int numOfDoneTasks, int numOfAllDirectories) {
        int completePercentage = numOfDoneTasks * 100 / numOfAllDirectories;
        System.out.print("\r" + completePercentage + "%");
    }

    public static void printTotalStatistics(int numOfAllDirectories, int totalNumberOfFiles, double totalKiloBytes) {
        System.out.println("Number of Directories: " + numOfAllDirectories);
        System.out.println("Number of Files: " + totalNumberOfFiles);
        System.out.println("Total size of files: " + String.format("%.2f", totalKiloBytes / 1024) + " MB");
    }
}
