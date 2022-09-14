import function.FunctionUtil;
import function.SimpleFunction;
import function.TableFunction;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class Main {
    private static final int PLOTTER_RESOLUTION = 1000;

    private static double[] coefficientsCalculation(TableFunction tableFunction) {
        double sumOfX = Arrays.stream(tableFunction.x()).sum();
        double squaredSumOfX = pow(sumOfX, 2);
        double sumOfY = Arrays.stream(tableFunction.y()).sum();
        double sumOfXSquared = Arrays.stream(tableFunction.x()).map(x -> pow(x, 2)).sum();
        double sumOfMultiXY = IntStream.range(0, tableFunction.x().length)
                .mapToDouble(i -> tableFunction.x()[i] * tableFunction.y()[i])
                .sum();
        double n = tableFunction.x().length;

        double a = (sumOfXSquared * sumOfY - sumOfX * sumOfMultiXY) / (n * sumOfXSquared - squaredSumOfX);
        double b = (sumOfX * sumOfY - n * sumOfMultiXY) / (squaredSumOfX - n * sumOfXSquared);

        return new double[]{a, b};
    }

    private static TableFunction generateScatter(TableFunction tableFunction){
        double max = Arrays.stream(tableFunction.y()).max().getAsDouble();
        double d = 0.2 * max;
        Random random = new Random();

        return TableFunctionUtils.modifyY(tableFunction, y -> y + (2 * random.nextDouble() - 1) * d / 2);
    }

    public static void main(String[] args) {
        SimpleFunction function = new SimpleFunction(x -> log(x) + 10);
        double beginOfInterval = 2;
        double endOfInterval = 1000;
        int numberOfDots = 20;

        TableFunction tableFunction = FunctionUtil.getTableFunction(function, beginOfInterval, endOfInterval, numberOfDots);
        TableFunction generatedTableFunction = generateScatter(tableFunction);
        TableFunction transformedTableFunction = TableFunctionUtils.modifyX(generatedTableFunction, Math::log);

        double[] coefficients= coefficientsCalculation(transformedTableFunction);
        SimpleFunction approximationFunction = new SimpleFunction(x -> coefficients[0] + coefficients[1] * log(x));

        Plotter plotter = new Plotter();
        plotter.addDots(generatedTableFunction, "Таблично заданная функция", Color.blue);
        plotter.addGraphic(FunctionUtil.getTableFunction(function, beginOfInterval, endOfInterval, PLOTTER_RESOLUTION),
                "Исходная функция",
                Color.black);
        plotter.addGraphic(FunctionUtil.getTableFunction(approximationFunction, beginOfInterval, endOfInterval, PLOTTER_RESOLUTION),
                "Аппроксимирующая функция",
                Color.green);
        plotter.display();
    }
}
