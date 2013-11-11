package nzgot.ec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jebl.evolution.io.FastaExporter;
import jebl.evolution.io.FastaImporter;
import jebl.evolution.io.ImportException;
import jebl.evolution.sequences.Sequence;
import jebl.evolution.sequences.SequenceType;

/**
 * 
 * @author Thomas Hummel
 */
public class SeqSearch {

	
	List<Sequence> sequencesIn1;
	List<Sequence> sequencesIn2;
	ArrayList<Sequence> sequencesOut = new ArrayList<Sequence>();
	
	/**
	 * searches matching sequence labels in query file to data base file and save the matching 
	 * data base sequences in a fasta file
	 * 
	 * @param filePathIn1 query sequences in fasta format
	 * @param filePathIn2 data base sequences in fasta format
	 * @param filePathOut file path for saving the result as fasta file
	 * @throws IOException
	 * @throws ImportException
	 */
	public void doSearch(String filePathIn1, String filePathIn2, String filePathOut) throws IOException, ImportException {

		File sequenceIn1 = new File(filePathIn1);
		File sequenceIn2 = new File(filePathIn2);
		
		Debugger.log("\nImport: "+filePathIn1);
		FastaImporter sequenceImport1 = new FastaImporter(sequenceIn1 , SequenceType.NUCLEOTIDE);
		sequencesIn1 = sequenceImport1.importSequences();
		
		Debugger.log("\nImport: "+filePathIn2);
		FastaImporter sequenceImport2 = new FastaImporter(sequenceIn2 , SequenceType.NUCLEOTIDE);
		sequencesIn2 = sequenceImport2.importSequences();

		Debugger.log("\nSearch...");
		for (Sequence seqQuery : sequencesIn1) {

			for(Sequence seqDB : sequencesIn2) {
				if (seqQuery.getTaxon().equals(seqDB.getTaxon())) {
					try {
						sequencesOut.add(seqDB);
					}
					catch(NullPointerException e) {
						Debugger.log("Error: " +seqDB.getTaxon().toString());
					}
				}
			}
		}
		Debugger.log("Write...");
		Writer write = new OutputStreamWriter(new FileOutputStream(filePathOut));
		FastaExporter fe = new FastaExporter(write);
		fe.exportSequences(sequencesOut);
		write.flush();
		write.close();
		Debugger.log("Done");
	}

	public static void main(String[] args) throws IOException, ImportException {
		
		final String fileQuery = "/Users/thum167/Documents/Curation/ReRun Clustering/Automatic error correction/iBold/IndirectSoil/IndirectSoil_ibol_corrected.fasta";
		final String fileDB = "/Users/thum167/Documents/Curation/ReRun Clustering/IndirectSoil/IndirectSoil_endTrimmed.fasta";
		final String fileOut = "/Users/thum167/Documents/Curation/ReRun Clustering/Automatic error correction/iBold/IndirectSoil/IndirectSoil_ibol_corrected_Control1.fasta";

		SeqSearch seqSearch = new SeqSearch();
		seqSearch.doSearch(fileQuery, fileDB, fileOut);
	}

}
