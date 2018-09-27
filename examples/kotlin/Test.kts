//DEPS com.milaboratory:mir:1.0-SNAPSHOT

import com.milaboratory.core.sequence.AminoAcidSequence
import com.milaboratory.mir.probability.parser.HierarchicalModelFormula
import com.milaboratory.mir.rearrangement.RearrangementModelUtils
import com.milaboratory.mir.rearrangement.RearrangementTemplate
import com.milaboratory.mir.segment.Gene
import com.milaboratory.mir.segment.Species
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils

val mdl = HierarchicalModelFormula(listOf("A|B", "B"))

println(mdl.variables)
println(mdl.toString())

var segmLib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
println(segmLib)

val rearrMdl = RearrangementModelUtils.loadMuruganModel(segmLib)

for (i in 0 until 1000) {
    println(AminoAcidSequence.translateFromCenter((rearrMdl.generate() as RearrangementTemplate).toRearrangement().cdr3))
}