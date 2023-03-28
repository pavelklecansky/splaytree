package cz.klecansky.splaytree.experiment;

import cz.klecansky.splaytree.tree.SplayTree;
import cz.klecansky.splaytree.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TreeExperiment {

    private static final int NUMBER_OF_EXPERIMENTS = 10000;
    private static final int NUMBER_OF_GENERATED_ELEMENTS = 1023;

    private final File experimentsFolder;

    public TreeExperiment() {
        experimentsFolder = new File("./experiments");
        experimentsFolder.mkdirs();
    }

    public ExperimentResult runExperiments() throws IOException {
        List<Integer> allHeights = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS; i++) {
            int height = runSingleExperiment(i);
            allHeights.add(height);
        }
        return calculateStatistics(allHeights);
    }


    private int runSingleExperiment(int experimentNumber) throws IOException {
        SplayTree<String, String> splayTree = new SplayTree<>();
        List<String> testingData = generateTestingData();
        testingData.forEach(data -> splayTree.put(data, data));
        int height = splayTree.height();
        saveExperimentDataToDisk(experimentNumber, height, testingData);
        return height;
    }

    private void saveExperimentDataToDisk(int experimentNumber, int height, List<String> testingData) throws IOException {
        File newFile = new File(experimentsFolder.getAbsolutePath() + File.separator + "experiment" + experimentNumber + ".txt");
        try (FileWriter fileWriter = new FileWriter(newFile)) {
            fileWriter.append("#height" + System.lineSeparator());
            fileWriter.append(height + System.lineSeparator());
            fileWriter.append("#data" + System.lineSeparator());
            for (String data : testingData) {
                fileWriter.append(data + System.lineSeparator());
            }
        }

    }

    private List<String> generateTestingData() {
        List<String> testingData = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GENERATED_ELEMENTS; i++) {
            testingData.add(Utils.generateProductId());
        }
        return testingData;
    }

    private ExperimentResult calculateStatistics(List<Integer> allHeights) {
        double average = average(allHeights);
        int max = max(allHeights);
        int min = min(allHeights);
        int mode = mode(allHeights);
        List<Double> cumulativeAverage = cumulativeAverage(allHeights);
        return new ExperimentResult(average, max, min, mode, cumulativeAverage);
    }



    private int max(List<Integer> allHeights) {
        return allHeights.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    private int min(List<Integer> allHeights) {
        return allHeights.stream().mapToInt(Integer::intValue).min().getAsInt();
    }

    private int mode(List<Integer> allHeights) {
        Map<Integer, Long> counts = allHeights.stream().mapToInt(Integer::intValue).boxed()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> maxEntry = counts.entrySet().stream()
                .collect(Collectors.maxBy(Map.Entry.comparingByValue()));

        return maxEntry.map(Map.Entry::getKey).get();
    }

    private double average(List<Integer> allHeights) {
        return allHeights.stream().mapToInt(Integer::intValue).average().getAsDouble();
    }

    private List<Double> cumulativeAverage(List<Integer> allHeights) {
        List<Double> averages = new ArrayList<>(allHeights.stream().mapToInt(Integer::intValue).mapToObj(i -> {
                    long sum = i;
                    int count = 1;
                    return new IntSummaryStatistics(sum, count, i, i);
                }).toList()
                .stream()
                .mapToLong(IntSummaryStatistics::getCount)
                .mapToDouble(Double::valueOf)
                .boxed().toList());

        double sum = 0;
        for (int i = 0; i < averages.size(); i++) {
            sum += averages.get(i);
            averages.set(i, sum / (i + 1));
        }

        return averages;
    }

}
