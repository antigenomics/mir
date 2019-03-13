package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.structure.TestStructureCache;
import com.antigenomics.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MarkupAnnotationWriterTest {
    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new MarkupAnnotationWriter(os)) {
            var mapper = new PeptideMhcComplexMapper(
                    new SimpleExhaustiveMapperFactory<>(
                            AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
            );
            writer.accept(mapper.map(TestStructureCache.get("1ao7")).get());
        }

        System.out.println(os.toString());

        Assert.assertEquals(
                "pdb.id\tchain.id\tregion.type\tregion.start\tregion.end\tregion.sequence\n" +
                        "1ao7_al\tD\tFR1\t0\t25\tKEVEQNSGPLSVPEGAIASLNCTYS\n" +
                        "1ao7_al\tD\tCDR1\t25\t31\tDRGSQS\n" +
                        "1ao7_al\tD\tFR2\t31\t48\tFFWYRQYSGKSPELIMS\n" +
                        "1ao7_al\tD\tCDR2\t48\t54\tIYSNGD\n" +
                        "1ao7_al\tD\tFR3\t54\t87\tKEDGRFTAQLNKASQYVSLLIRDSQPSDSATYL\n" +
                        "1ao7_al\tD\tCDR3\t87\t100\tCAVTTDSWGKLQF\n" +
                        "1ao7_al\tD\tFR4\t100\t110\tGAGTQVVVTP\n" +
                        "1ao7_al\tE\tFR1\t0\t24\tGVTQTPKFQVLKTGQSMTLQCAQD\n" +
                        "1ao7_al\tE\tCDR1\t24\t29\tMNHEY\n" +
                        "1ao7_al\tE\tFR2\t29\t46\tMSWYRQDPGMGLRLIHY\n" +
                        "1ao7_al\tE\tCDR2\t46\t52\tSVGAGI\n" +
                        "1ao7_al\tE\tFR3\t52\t88\tTDQGEVPNGYNVSRSTTEDFPLRLLSAAPSQTSVYF\n" +
                        "1ao7_al\tE\tCDR3\t88\t104\tCASRPGLAGGRPEQYF\n" +
                        "1ao7_al\tE\tFR4\t104\t113\tGPGTRLTVT\n" +
                        "1ao7_al\tC\tPEPTIDE\t0\t9\tLLFGYPVYV\n" +
                        "1ao7_al\tA\tSIGNAL\t0\t0\t\n" +
                        "1ao7_al\tA\tREGION1\t0\t90\tGSHSMRYFFTSVSRPGRGEPRFIAVGYVDDTQFVRFDSDAASQRMEPRAPWIEQEGPEYWDGETRKVKAHSQTHRVDLGTLRGYYNQSEA\n" +
                        "1ao7_al\tA\tREGION2\t90\t182\tGSHTVQRMYGCDVGSDWRFLRGYHQYAYDGKDYIALKEDLRSWTAADMAAQTTKHKWEAAHVAEQLRAYLEGTCVEWLRRYLENGKETLQRT\n" +
                        "1ao7_al\tA\tREGION3\t182\t274\tDAPKTHMTHHAVSDHEATLRCWALSFYPAEITLTWQRDGEDQTQDTELVETRPAGDGTFQKWAAVVVPSGQEQRYTCHVQHEGLPKPLTLRW\n" +
                        "1ao7_al\tA\tMEMBRANE\t274\t274\t\n" +
                        "1ao7_al\tB\tSIGNAL\t1\t1\t\n" +
                        "1ao7_al\tB\tREGION1\t1\t1\t\n" +
                        "1ao7_al\tB\tREGION2\t1\t1\t\n" +
                        "1ao7_al\tB\tREGION3\t1\t1\t\n" +
                        "1ao7_al\tB\tMEMBRANE\t1\t100\tIQRTPKIQVYSRHPAENGKSNFLNCYVSGFHPSDIEVDLLKNGERIEKVEHSDLSFSKDWSFYLLYCTEFTPTEKDEYACRVNHVTLSQPCIVKWDRDM\n",
                os.toString());
    }
}