package cz.klecansky.splaytree.experiment;

import java.util.List;

public record ExperimentResult(double average, int max, int min, int mode, List<Double> cumulativeAverage) {
}
