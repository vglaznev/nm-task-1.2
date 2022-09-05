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
    public static void main(String[] args) {
        SimpleFunction function = new SimpleFunction(x -> log(x)+10);
        double beginOfInterval = 2;
        double endOfInterval = 1000;
        int numberOfDots = 1000;

        TableFunction tableFunction = FunctionUtil.getTableFunction(function, beginOfInterval, endOfInterval, numberOfDots);
        double max = Arrays.stream(tableFunction.y()).max().getAsDouble();
        double d = 0.2 * max;
        Random random = new Random();


        TableFunction generatedTableFunction = TableFunctionUtils.modifyY(tableFunction, y -> y + (2 * random.nextDouble() - 1) * d / 2);

        TableFunction transformedTableFunction = TableFunctionUtils.modifyX(generatedTableFunction, Math::log) ;
        double sumOfX = Arrays.stream(transformedTableFunction.x()).sum();
        double squaredSumOfX = pow(sumOfX, 2);
        double sumOfY = Arrays.stream(transformedTableFunction.y()).sum();
        double sumOfXSquared = Arrays.stream(transformedTableFunction.x()).map(x -> pow(x, 2)).sum();
        double sumOfMultiXY = IntStream.range(0, transformedTableFunction.x().length)
                .mapToDouble(i -> transformedTableFunction.x()[i] * transformedTableFunction.y()[i])
                .sum();
        double n = transformedTableFunction.x().length;

        double a = (sumOfXSquared*sumOfY - sumOfX*sumOfMultiXY) / (n * sumOfXSquared - squaredSumOfX);
        double b = (sumOfX*sumOfY - n * sumOfMultiXY) / (squaredSumOfX - n * sumOfXSquared);




        Plotter plotter = new Plotter();
        plotter.addDots(generatedTableFunction, "points");
        plotter.addGraphic(FunctionUtil.getTableFunction(function, beginOfInterval, endOfInterval, 100), "function", Color.black);
        plotter.addGraphic(FunctionUtil.getTableFunction(new SimpleFunction(x -> a + b*log(x)), beginOfInterval, endOfInterval, 100), "approx function", Color.green);
        plotter.display();

    }
}
