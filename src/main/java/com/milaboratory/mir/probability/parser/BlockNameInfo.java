package com.milaboratory.mir.probability.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.*;

public class BlockNameInfo {
    private final List<String> variables;
    private final List<String> parentVariables;

    public static BlockNameInfo fromBlockName(String blockName) {
        var splitBlock = blockName.split(REGEX_CONDITIONAL_SEPARATOR);
        return new BlockNameInfo(
                Arrays.asList(splitBlock[0].split(VARIABLE_SEPARATOR)),
                Arrays.asList(splitBlock[1].split(VARIABLE_SEPARATOR))
        );
    }

    private BlockNameInfo(List<String> variables, List<String> parentVariables) {
        this.variables = variables;
        this.parentVariables = parentVariables;
    }

    public List<String> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public List<String> getParentVariables() {
        return Collections.unmodifiableList(parentVariables);
    }

    public boolean isConditional() {
        return !parentVariables.isEmpty();
    }
}
