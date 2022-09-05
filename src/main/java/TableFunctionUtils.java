import function.TableFunction;

import java.util.Arrays;
import java.util.function.UnaryOperator;

public class TableFunctionUtils {
    public static TableFunction modifyX(TableFunction function, UnaryOperator<Double> transform) {
        return new TableFunction(transformArray(function.x().clone(), transform), function.y().clone());
    }

    public static TableFunction modifyY(TableFunction function, UnaryOperator<Double> transform) {
        return new TableFunction(function.x().clone(), transformArray(function.y().clone(), transform));
    }

    private static double[] transformArray(double[] array, UnaryOperator<Double> transform) {
        return Arrays.stream(array).map(transform::apply).toArray();
    }
}
