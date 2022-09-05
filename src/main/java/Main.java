import function.FunctionUtil;
import function.SimpleFunction;
import function.TableFunction;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SimpleFunction function = new SimpleFunction(x -> 2*x+10);
        double beginOfInterval = -10;
        double endOfInterval = 2;
        int numberOfDots = 1000;

        TableFunction tableFunction = FunctionUtil.getTableFunction(function, beginOfInterval, endOfInterval, numberOfDots);
        double max = Arrays.stream(tableFunction.y()).max().getAsDouble();
        double d = 0.2 * max;
        Random random = new Random();


        TableFunction generatedTableFunction = TableFunctionUtils.modifyY(tableFunction, y -> y + (2 * random.nextDouble() - 1) * d / 2);

        Plotter plotter = new Plotter();
        plotter.addDots(generatedTableFunction, "points");
        plotter.addGraphic(FunctionUtil.getTableFunction(function, -10, 2, 100), "function", Color.black);
        plotter.display();

    }
}
