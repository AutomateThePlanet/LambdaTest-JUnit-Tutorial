package junitdemo;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.List;

public class ToDoListAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        return new ToDoList(argumentsAccessor.getString(0), (List<String>)argumentsAccessor.get(1), (List<String>)argumentsAccessor.get(2), argumentsAccessor.getInteger(3));
    }
}
