mvn clean install -Dmaven.test.skip=true
MIRSCRIPT="java -cp target/mir-1.0-SNAPSHOT.jar com.antigenomics.mir.scripts.Examples"

$MIRSCRIPT clonotype-summary-stats -S Human -G TRA -U NT_SPECTRATYPE AA_CDR3_PWM -F VDJtools \
-I src/test/resources/samples/trad_sample.txt.gz -O tests_out/compute-summary-stats.txt