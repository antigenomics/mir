package com.milaboratory.mir.probability.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils.*;

public final class BlockNameInfo {
    private final String blockName;
    private final List<String> variables;
    private final List<String> parentVariables;

    public static BlockNameInfo fromBlockName(String blockName) {
        var splitBlock = blockName.split(REGEX_CONDITIONAL_SEPARATOR);
        return new BlockNameInfo(
                blockName,
                Arrays.asList(splitBlock[0].split(VARIABLE_SEPARATOR)),
                splitBlock.length > 1 ? Arrays.asList(splitBlock[1].split(VARIABLE_SEPARATOR)) :
                        Collections.emptyList()
        );
    }

    private BlockNameInfo(String blockName, List<String> variables, List<String> parentVariables) {
        this.blockName = blockName;
        this.variables = variables;
        this.parentVariables = parentVariables;
    }

    public String getBlockName() {
        return blockName;
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

    public boolean isMultivariate() {
        return variables.size() > 1;
    }
}
