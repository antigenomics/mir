package com.milaboratory.mir.rearrangement.parser;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModel;
import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;

import java.io.FileInputStream;
import java.io.IOException;

public final class MuruganModeParserUtils {
    public static final String PATH = "murugan_models";

    public static final String
            V_NAME = "v_choice",
            J_NAME = "j_choice",
            D_NAME = "d_choice";

    public static final String
            V_FORMULA = "v_choice",
            J_FORMULA = "j_choice|v_choice",
            V_DEL_FORMULA = "v_3_del|v_choice",
            J_DEL_FORMULA = "j_5_del|j_choice",
            VJ_INS_SZ_FORMULA = "vj_ins",
            VJ_INS_DINUCL_FORMULA = "vj_dinucl",
            D_FORMULA = "d_gene|j_choice,v_choice",
            D_DEL5_FORMULA = "d_5_del|d_gene",
            D_DEL3_FORMULA = "d_3_del|d_5_del,d_gene",
            VD_INS_SZ_FORMULA = "vd_ins",
            DJ_INS_SZ_FORMULA = "dj_ins",
            VD_INS_DINUCL_FORMULA = "vd_dinucl",
            DJ_INS_DINUCL_FORMULA = "dj_dinucl";

    private static String getFormulaStr(Gene gene) {
        switch (gene) {
            case TRA:
                return "P(v_choice)" +
                        "P(j_choice|v_choice)" +
                        "P(v_3_del|v_choice)" +
                        "P(j_5_del|j_choice)" +
                        "P(vj_ins)" +
                        "P(vj_dinucl)";
            case TRB:
                return "P(v_choice)" +
                        "P(j_choice)" +
                        "P(d_gene|j_choice)" +
                        "P(v_3_del|v_choice)" +
                        "P(j_5_del|j_choice)" +
                        "P(d_5_del|d_gene)" +
                        "P(d_3_del|d_5_del,d_gene)" +
                        "P(vd_ins)" +
                        "P(vd_dinucl)" +
                        "P(dj_ins)" +
                        "P(dj_dinucl)";
            case IGH:
                return "P(v_choice)" +
                        "P(j_choice|v_choice)" +
                        "P(d_gene|j_choice,v_choice)" +
                        "P(v_3_del|v_choice)" +
                        "P(j_5_del|j_choice)" +
                        "P(d_5_del|d_gene)" +
                        "P(d_3_del|d_5_del,d_gene)" +
                        "P(vd_ins)" +
                        "P(vd_dinucl)" +
                        "P(dj_ins)" +
                        "P(dj_dinucl)";
            default:
                throw new IllegalArgumentException("No rearrangement model for " + gene);
        }
    }

    public static HierarchicalModelFormula getFormula(Gene gene) {
        return HierarchicalModelFormula.fromString(getFormulaStr(gene));
    }

    private MuruganModeParserUtils() {

    }

    public static MuruganModel getModelFromResources(Species species, Gene gene) throws IOException {
        MuruganModelStream muruganModelStream = getResourceStream(species, gene);
        return MuruganModelParser.load(muruganModelStream.getParams(),
                muruganModelStream.getMarginals(),
                getFormula(gene), species, gene);
    }

    public static MuruganModel getModelFromPath(String paramsPath, String marginalsPath,
                                                Species species, Gene gene) throws IOException {
        return MuruganModelParser.load(new FileInputStream(paramsPath),
                new FileInputStream(marginalsPath),
                getFormula(gene), species, gene);
    }

    static MuruganModelStream getResourceStream(Species species, Gene gene) throws IOException {
        return new MuruganModelStream(
                CommonUtils.getResourceAsStream(PATH + "/" + species.getCode() + "_" + gene.getCode() + "_params.txt"),
                CommonUtils.getResourceAsStream(PATH + "/" + species.getCode() + "_" + gene.getCode() + "_marginals.txt")
        );
    }
}
