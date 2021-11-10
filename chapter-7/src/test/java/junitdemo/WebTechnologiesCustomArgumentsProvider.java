package junitdemo;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class WebTechnologiesCustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("AngularJS", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk", "Buy Batteries"), List.of("Buy Ketchup", "Buy House"), 3),
                Arguments.of("React", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk", "Buy Batteries"), List.of("Buy Paper", "Buy Milk", "Buy Batteries"), 2),
                Arguments.of("Vue.js", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk", "Buy Batteries"), List.of("Buy Paper", "Buy Milk", "Buy Batteries"), 2),
                Arguments.of("Angular 2.0", List.of("Buy Ketchup", "Buy House", "Buy Paper", "Buy Milk", "Buy Batteries"), List.of(), 5)
        );
    }
}
