//DEPS com.milaboratory:mir:1.0-SNAPSHOT

import com.milaboratory.mir.probability.parser.HierarchicalModelFormula

val test = HierarchicalModelFormula(listOf("A|B", "B"))

println(test.variables)
println(test.toString())