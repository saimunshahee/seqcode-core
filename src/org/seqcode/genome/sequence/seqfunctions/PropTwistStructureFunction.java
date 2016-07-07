package org.seqcode.genome.sequence.seqfunctions;

import java.util.HashMap;
import java.util.Map;

/**
 * Scores Propeller Twist (degrees) of a sequence using score defined by Remo Rohs' lab.
 * Source: 
 * 		http://rohslab.cmb.usc.edu/DNAshape/
 *		Zhou,,T., Yang,L., Lu,Y., Dror,I., Dantas Machado,A.C., Ghane,T., Di Felice,R. and Rohs,R. 
 *		DNAshape: a method for the high-throughput prediction of DNA structural features on a genomic scale. 
 *		Nucleic Acids Res. (2013) 41, W56-W62.
 *   
 * @author mahony
 *
 */
public class PropTwistStructureFunction implements SeqFunction{

	//Variables
	final int scoreDimension = 1;
	int scoringOffset = 2;
	int scoreWindowSize = 5;
	boolean isBetweenNucs = false;
	final String[] labels = {"PropT"};
	final String description = "Propeller Twist (Rohs)";
	
	Map<String, Double> structure = new HashMap<String, Double>();
	
	
	public PropTwistStructureFunction(){
		loadStructureValues();
	}
	
	public double[][] score(String seq) throws SeqFunctionException {
		if(seq.length()<scoreWindowSize)
			throw new SeqFunctionException("Sequence too short for PropTwistStructureFunction");
		
		double [][] scores = new double[scoreDimension][seq.length()]; 
		String seqU = seq.toUpperCase();
		for(int i=0; i<seqU.length()-scoreWindowSize+1; i++){
			scores[0][i]=0;
			String kmer = seqU.substring(i, i+scoreWindowSize);
			
			scores[0][i+scoringOffset]=structure.get(kmer);
		}
		
		return scores;
	}

	public int scoreDimension() {
		return scoreDimension;
	}

	public int scoringOffset() {
		return scoringOffset;
	}

	public int scoreWindowSize() {
		return scoreWindowSize;
	}

	public boolean isBetweenNucleotides() {
		return isBetweenNucs;
	}

	public String[] dimensionLabels() {
		return labels;
	}

	public String scoreDescription() {
		return description;
	}

	
	private void loadStructureValues(){
		structure.put("AAAAA",  -16.51);
		structure.put("AAAAT",  -14.89);
		structure.put("AAAAG",  -14.68);
		structure.put("AAAAC",  -14.47);
		structure.put("AAATA",  -12.63);
		structure.put("AAATT",  -14.95);
		structure.put("AAATG",  -11.76);
		structure.put("AAATC",  -12.27);
		structure.put("AAAGA",  -10.71);
		structure.put("AAAGT",  -11.68);
		structure.put("AAAGG",  -9.21);
		structure.put("AAAGC",  -10.58);
		structure.put("AAACA",  -13.05);
		structure.put("AAACT",  -12.78);
		structure.put("AAACG",  -13.15);
		structure.put("AAACC",  -12.41);
		structure.put("AATAA",  -9.65);
		structure.put("AATAT",  -10.43);
		structure.put("AATAG",  -11.61);
		structure.put("AATAC",  -10.45);
		structure.put("AATTA",  -12.20);
		structure.put("AATTT",  -14.95);
		structure.put("AATTG",  -11.93);
		structure.put("AATTC",  -11.18);
		structure.put("AATGA",  -9.02);
		structure.put("AATGT",  -10.82);
		structure.put("AATGG",  -8.98);
		structure.put("AATGC",  -9.59);
		structure.put("AATCA",  -9.91);
		structure.put("AATCT",  -10.31);
		structure.put("AATCG",  -9.51);
		structure.put("AATCC",  -9.67);
		structure.put("AAGAA",  -3.46);
		structure.put("AAGAT",  -3.55);
		structure.put("AAGAG",  -3.22);
		structure.put("AAGAC",  -3.82);
		structure.put("AAGTA",  -4.26);
		structure.put("AAGTT",  -6.26);
		structure.put("AAGTG",  -4.48);
		structure.put("AAGTC",  -4.07);
		structure.put("AAGGA",  -1.33);
		structure.put("AAGGT",  -1.48);
		structure.put("AAGGG",  -0.14);
		structure.put("AAGGC",  -1.50);
		structure.put("AAGCA",  -1.47);
		structure.put("AAGCT",  -1.56);
		structure.put("AAGCG",  -1.91);
		structure.put("AAGCC",  -1.55);
		structure.put("AACAA",  -10.17);
		structure.put("AACAT",  -10.22);
		structure.put("AACAG",  -9.26);
		structure.put("AACAC",  -9.40);
		structure.put("AACTA",  -6.07);
		structure.put("AACTT",  -6.26);
		structure.put("AACTG",  -5.42);
		structure.put("AACTC",  -5.23);
		structure.put("AACGA",  -9.62);
		structure.put("AACGT",  -10.39);
		structure.put("AACGG",  -10.04);
		structure.put("AACGC",  -9.91);
		structure.put("AACCA",  -8.86);
		structure.put("AACCT",  -8.15);
		structure.put("AACCG",  -7.97);
		structure.put("AACCC",  -7.68);
		structure.put("ATAAA",  -12.37);
		structure.put("ATAAT",  -11.27);
		structure.put("ATAAG",  -10.54);
		structure.put("ATAAC",  -10.51);
		structure.put("ATATA",  -8.95);
		structure.put("ATATT",  -10.43);
		structure.put("ATATG",  -8.33);
		structure.put("ATATC",  -8.30);
		structure.put("ATAGA",  -7.03);
		structure.put("ATAGT",  -8.26);
		structure.put("ATAGG",  -6.87);
		structure.put("ATAGC",  -6.84);
		structure.put("ATACA",  -8.93);
		structure.put("ATACT",  -8.21);
		structure.put("ATACG",  -9.05);
		structure.put("ATACC",  -8.36);
		structure.put("ATTAA",  -11.32);
		structure.put("ATTAT",  -11.27);
		structure.put("ATTAG",  -11.58);
		structure.put("ATTAC",  -11.34);
		structure.put("ATTTA",  -13.87);
		structure.put("ATTTT",  -14.89);
		structure.put("ATTTG",  -13.36);
		structure.put("ATTTC",  -12.09);
		structure.put("ATTGA",  -10.92);
		structure.put("ATTGT",  -11.94);
		structure.put("ATTGG",  -10.72);
		structure.put("ATTGC",  -10.60);
		structure.put("ATTCA",  -10.83);
		structure.put("ATTCT",  -10.15);
		structure.put("ATTCG",  -10.56);
		structure.put("ATTCC",  -9.61);
		structure.put("ATGAA",  -6.07);
		structure.put("ATGAT",  -6.15);
		structure.put("ATGAG",  -6.05);
		structure.put("ATGAC",  -6.37);
		structure.put("ATGTA",  -7.40);
		structure.put("ATGTT",  -10.22);
		structure.put("ATGTG",  -8.83);
		structure.put("ATGTC",  -7.10);
		structure.put("ATGGA",  -3.37);
		structure.put("ATGGT",  -4.33);
		structure.put("ATGGG",  -2.37);
		structure.put("ATGGC",  -3.12);
		structure.put("ATGCA",  -3.73);
		structure.put("ATGCT",  -3.68);
		structure.put("ATGCG",  -4.03);
		structure.put("ATGCC",  -3.12);
		structure.put("ATCAA",  -7.12);
		structure.put("ATCAT",  -6.15);
		structure.put("ATCAG",  -6.55);
		structure.put("ATCAC",  -6.53);
		structure.put("ATCTA",  -3.20);
		structure.put("ATCTT",  -3.55);
		structure.put("ATCTG",  -2.70);
		structure.put("ATCTC",  -2.78);
		structure.put("ATCGA",  -7.08);
		structure.put("ATCGT",  -6.85);
		structure.put("ATCGG",  -7.15);
		structure.put("ATCGC",  -6.96);
		structure.put("ATCCA",  -5.70);
		structure.put("ATCCT",  -5.29);
		structure.put("ATCCG",  -4.49);
		structure.put("ATCCC",  -4.91);
		structure.put("AGAAA",  -10.55);
		structure.put("AGAAT",  -10.15);
		structure.put("AGAAG",  -10.30);
		structure.put("AGAAC",  -10.59);
		structure.put("AGATA",  -8.23);
		structure.put("AGATT",  -10.31);
		structure.put("AGATG",  -7.85);
		structure.put("AGATC",  -8.23);
		structure.put("AGAGA",  -6.62);
		structure.put("AGAGT",  -7.83);
		structure.put("AGAGG",  -6.38);
		structure.put("AGAGC",  -6.50);
		structure.put("AGACA",  -8.49);
		structure.put("AGACT",  -7.81);
		structure.put("AGACG",  -8.81);
		structure.put("AGACC",  -8.04);
		structure.put("AGTAA",  -7.55);
		structure.put("AGTAT",  -8.21);
		structure.put("AGTAG",  -8.16);
		structure.put("AGTAC",  -8.16);
		structure.put("AGTTA",  -10.74);
		structure.put("AGTTT",  -12.78);
		structure.put("AGTTG",  -11.29);
		structure.put("AGTTC",  -10.45);
		structure.put("AGTGA",  -7.10);
		structure.put("AGTGT",  -8.48);
		structure.put("AGTGG",  -7.40);
		structure.put("AGTGC",  -7.68);
		structure.put("AGTCA",  -8.11);
		structure.put("AGTCT",  -7.81);
		structure.put("AGTCG",  -8.15);
		structure.put("AGTCC",  -7.34);
		structure.put("AGGAA",  -4.88);
		structure.put("AGGAT",  -5.29);
		structure.put("AGGAG",  -4.81);
		structure.put("AGGAC",  -5.39);
		structure.put("AGGTA",  -5.43);
		structure.put("AGGTT",  -8.15);
		structure.put("AGGTG",  -6.64);
		structure.put("AGGTC",  -5.67);
		structure.put("AGGGA",  -1.76);
		structure.put("AGGGT",  -2.62);
		structure.put("AGGGG",  -1.32);
		structure.put("AGGGC",  -1.88);
		structure.put("AGGCA",  -2.38);
		structure.put("AGGCT",  -2.34);
		structure.put("AGGCG",  -2.82);
		structure.put("AGGCC",  -2.20);
		structure.put("AGCAA",  -3.36);
		structure.put("AGCAT",  -3.68);
		structure.put("AGCAG",  -3.60);
		structure.put("AGCAC",  -3.79);
		structure.put("AGCTA",  -1.30);
		structure.put("AGCTT",  -1.56);
		structure.put("AGCTG",  -1.38);
		structure.put("AGCTC",  -0.83);
		structure.put("AGCGA",  -3.56);
		structure.put("AGCGT",  -3.93);
		structure.put("AGCGG",  -3.78);
		structure.put("AGCGC",  -3.87);
		structure.put("AGCCA",  -2.76);
		structure.put("AGCCT",  -2.34);
		structure.put("AGCCG",  -3.04);
		structure.put("AGCCC",  -2.48);
		structure.put("ACAAA",  -12.03);
		structure.put("ACAAT",  -11.94);
		structure.put("ACAAG",  -10.72);
		structure.put("ACAAC",  -11.70);
		structure.put("ACATA",  -8.52);
		structure.put("ACATT",  -10.82);
		structure.put("ACATG",  -10.15);
		structure.put("ACATC",  -8.65);
		structure.put("ACAGA",  -7.00);
		structure.put("ACAGT",  -8.12);
		structure.put("ACAGG",  -6.78);
		structure.put("ACAGC",  -7.34);
		structure.put("ACACA",  -9.33);
		structure.put("ACACT",  -8.48);
		structure.put("ACACG",  -9.43);
		structure.put("ACACC",  -8.57);
		structure.put("ACTAA",  -8.10);
		structure.put("ACTAT",  -8.26);
		structure.put("ACTAG",  -7.44);
		structure.put("ACTAC",  -8.16);
		structure.put("ACTTA",  -9.96);
		structure.put("ACTTT",  -11.68);
		structure.put("ACTTG",  -10.21);
		structure.put("ACTTC",  -8.83);
		structure.put("ACTGA",  -7.10);
		structure.put("ACTGT",  -8.12);
		structure.put("ACTGG",  -7.24);
		structure.put("ACTGC",  -7.79);
		structure.put("ACTCA",  -7.50);
		structure.put("ACTCT",  -7.83);
		structure.put("ACTCG",  -7.78);
		structure.put("ACTCC",  -6.75);
		structure.put("ACGAA",  -6.66);
		structure.put("ACGAT",  -6.85);
		structure.put("ACGAG",  -6.50);
		structure.put("ACGAC",  -6.61);
		structure.put("ACGTA",  -7.92);
		structure.put("ACGTT",  -10.39);
		structure.put("ACGTG",  -8.78);
		structure.put("ACGTC",  -7.92);
		structure.put("ACGGA",  -3.36);
		structure.put("ACGGT",  -4.79);
		structure.put("ACGGG",  -2.81);
		structure.put("ACGGC",  -3.97);
		structure.put("ACGCA",  -4.25);
		structure.put("ACGCT",  -3.93);
		structure.put("ACGCG",  -4.73);
		structure.put("ACGCC",  -3.63);
		structure.put("ACCAA",  -4.69);
		structure.put("ACCAT",  -4.33);
		structure.put("ACCAG",  -4.44);
		structure.put("ACCAC",  -5.00);
		structure.put("ACCTA",  -1.54);
		structure.put("ACCTT",  -1.48);
		structure.put("ACCTG",  -1.68);
		structure.put("ACCTC",  -0.88);
		structure.put("ACCGA",  -4.29);
		structure.put("ACCGT",  -4.79);
		structure.put("ACCGG",  -5.03);
		structure.put("ACCGC",  -4.55);
		structure.put("ACCCA",  -3.38);
		structure.put("ACCCT",  -2.62);
		structure.put("ACCCG",  -3.53);
		structure.put("ACCCC",  -2.37);
		structure.put("TAAAA",  -13.29);
		structure.put("TAAAT",  -13.87);
		structure.put("TAAAG",  -12.85);
		structure.put("TAAAC",  -13.54);
		structure.put("TAATA",  -9.98);
		structure.put("TAATT",  -12.20);
		structure.put("TAATG",  -10.52);
		structure.put("TAATC",  -10.25);
		structure.put("TAAGA",  -8.51);
		structure.put("TAAGT",  -9.96);
		structure.put("TAAGG",  -8.13);
		structure.put("TAAGC",  -8.92);
		structure.put("TAACA",  -11.35);
		structure.put("TAACT",  -10.74);
		structure.put("TAACG",  -11.31);
		structure.put("TAACC",  -10.83);
		structure.put("TATAA",  -7.14);
		structure.put("TATAT",  -8.95);
		structure.put("TATAG",  -7.69);
		structure.put("TATAC",  -7.88);
		structure.put("TATTA",  -9.98);
		structure.put("TATTT",  -12.63);
		structure.put("TATTG",  -9.70);
		structure.put("TATTC",  -10.01);
		structure.put("TATGA",  -7.00);
		structure.put("TATGT",  -8.52);
		structure.put("TATGG",  -6.69);
		structure.put("TATGC",  -8.11);
		structure.put("TATCA",  -7.32);
		structure.put("TATCT",  -8.23);
		structure.put("TATCG",  -7.63);
		structure.put("TATCC",  -7.47);
		structure.put("TAGAA",  -3.73);
		structure.put("TAGAT",  -3.20);
		structure.put("TAGAG",  -3.22);
		structure.put("TAGAC",  -3.62);
		structure.put("TAGTA",  -3.97);
		structure.put("TAGTT",  -6.07);
		structure.put("TAGTG",  -4.24);
		structure.put("TAGTC",  -3.90);
		structure.put("TAGGA",  -1.39);
		structure.put("TAGGT",  -1.54);
		structure.put("TAGGG",  -0.56);
		structure.put("TAGGC",  -1.23);
		structure.put("TAGCA",  -1.60);
		structure.put("TAGCT",  -1.30);
		structure.put("TAGCG",  -1.63);
		structure.put("TAGCC",  -1.22);
		structure.put("TACAA",  -7.17);
		structure.put("TACAT",  -7.40);
		structure.put("TACAG",  -6.49);
		structure.put("TACAC",  -6.58);
		structure.put("TACTA",  -3.97);
		structure.put("TACTT",  -4.26);
		structure.put("TACTG",  -4.20);
		structure.put("TACTC",  -3.05);
		structure.put("TACGA",  -6.48);
		structure.put("TACGT",  -7.92);
		structure.put("TACGG",  -6.70);
		structure.put("TACGC",  -7.22);
		structure.put("TACCA",  -5.96);
		structure.put("TACCT",  -5.43);
		structure.put("TACCG",  -5.99);
		structure.put("TACCC",  -5.01);
		structure.put("TTAAA",  -10.70);
		structure.put("TTAAT",  -11.32);
		structure.put("TTAAG",  -9.57);
		structure.put("TTAAC",  -10.44);
		structure.put("TTATA",  -7.14);
		structure.put("TTATT",  -9.65);
		structure.put("TTATG",  -8.06);
		structure.put("TTATC",  -7.66);
		structure.put("TTAGA",  -6.61);
		structure.put("TTAGT",  -8.10);
		structure.put("TTAGG",  -5.77);
		structure.put("TTAGC",  -6.12);
		structure.put("TTACA",  -7.55);
		structure.put("TTACT",  -7.55);
		structure.put("TTACG",  -7.71);
		structure.put("TTACC",  -7.21);
		structure.put("TTTAA",  -10.70);
		structure.put("TTTAT",  -12.37);
		structure.put("TTTAG",  -11.18);
		structure.put("TTTAC",  -11.89);
		structure.put("TTTTA",  -13.29);
		structure.put("TTTTT",  -16.51);
		structure.put("TTTTG",  -13.79);
		structure.put("TTTTC",  -13.16);
		structure.put("TTTGA",  -10.28);
		structure.put("TTTGT",  -12.03);
		structure.put("TTTGG",  -9.32);
		structure.put("TTTGC",  -10.88);
		structure.put("TTTCA",  -10.81);
		structure.put("TTTCT",  -10.55);
		structure.put("TTTCG",  -10.58);
		structure.put("TTTCC",  -10.09);
		structure.put("TTGAA",  -5.98);
		structure.put("TTGAT",  -7.12);
		structure.put("TTGAG",  -6.36);
		structure.put("TTGAC",  -7.07);
		structure.put("TTGTA",  -7.17);
		structure.put("TTGTT",  -10.17);
		structure.put("TTGTG",  -8.23);
		structure.put("TTGTC",  -7.48);
		structure.put("TTGGA",  -2.74);
		structure.put("TTGGT",  -4.69);
		structure.put("TTGGG",  -3.45);
		structure.put("TTGGC",  -3.85);
		structure.put("TTGCA",  -3.80);
		structure.put("TTGCT",  -3.36);
		structure.put("TTGCG",  -4.14);
		structure.put("TTGCC",  -3.14);
		structure.put("TTCAA",  -5.98);
		structure.put("TTCAT",  -6.07);
		structure.put("TTCAG",  -5.95);
		structure.put("TTCAC",  -6.21);
		structure.put("TTCTA",  -3.73);
		structure.put("TTCTT",  -3.46);
		structure.put("TTCTG",  -3.52);
		structure.put("TTCTC",  -2.40);
		structure.put("TTCGA",  -5.99);
		structure.put("TTCGT",  -6.66);
		structure.put("TTCGG",  -6.08);
		structure.put("TTCGC",  -6.08);
		structure.put("TTCCA",  -5.20);
		structure.put("TTCCT",  -4.88);
		structure.put("TTCCG",  -5.36);
		structure.put("TTCCC",  -4.39);
		structure.put("TGAAA",  -10.81);
		structure.put("TGAAT",  -10.83);
		structure.put("TGAAG",  -10.24);
		structure.put("TGAAC",  -10.38);
		structure.put("TGATA",  -7.32);
		structure.put("TGATT",  -9.91);
		structure.put("TGATG",  -8.13);
		structure.put("TGATC",  -7.81);
		structure.put("TGAGA",  -6.62);
		structure.put("TGAGT",  -7.50);
		structure.put("TGAGG",  -6.36);
		structure.put("TGAGC",  -6.75);
		structure.put("TGACA",  -8.28);
		structure.put("TGACT",  -8.11);
		structure.put("TGACG",  -8.47);
		structure.put("TGACC",  -7.96);
		structure.put("TGTAA",  -7.55);
		structure.put("TGTAT",  -8.93);
		structure.put("TGTAG",  -8.60);
		structure.put("TGTAC",  -8.00);
		structure.put("TGTTA",  -11.35);
		structure.put("TGTTT",  -13.05);
		structure.put("TGTTG",  -11.69);
		structure.put("TGTTC",  -10.91);
		structure.put("TGTGA",  -7.82);
		structure.put("TGTGT",  -9.33);
		structure.put("TGTGG",  -7.33);
		structure.put("TGTGC",  -8.30);
		structure.put("TGTCA",  -8.28);
		structure.put("TGTCT",  -8.49);
		structure.put("TGTCG",  -8.25);
		structure.put("TGTCC",  -7.71);
		structure.put("TGGAA",  -5.20);
		structure.put("TGGAT",  -5.70);
		structure.put("TGGAG",  -5.45);
		structure.put("TGGAC",  -5.78);
		structure.put("TGGTA",  -5.96);
		structure.put("TGGTT",  -8.86);
		structure.put("TGGTG",  -7.00);
		structure.put("TGGTC",  -6.17);
		structure.put("TGGGA",  -2.42);
		structure.put("TGGGT",  -3.38);
		structure.put("TGGGG",  -2.02);
		structure.put("TGGGC",  -2.77);
		structure.put("TGGCA",  -2.98);
		structure.put("TGGCT",  -2.76);
		structure.put("TGGCG",  -2.98);
		structure.put("TGGCC",  -2.54);
		structure.put("TGCAA",  -3.80);
		structure.put("TGCAT",  -3.73);
		structure.put("TGCAG",  -3.68);
		structure.put("TGCAC",  -3.74);
		structure.put("TGCTA",  -1.60);
		structure.put("TGCTT",  -1.47);
		structure.put("TGCTG",  -1.72);
		structure.put("TGCTC",  -0.94);
		structure.put("TGCGA",  -3.81);
		structure.put("TGCGT",  -4.25);
		structure.put("TGCGG",  -4.13);
		structure.put("TGCGC",  -3.94);
		structure.put("TGCCA",  -2.98);
		structure.put("TGCCT",  -2.38);
		structure.put("TGCCG",  -3.11);
		structure.put("TGCCC",  -2.52);
		structure.put("TCAAA",  -10.28);
		structure.put("TCAAT",  -10.92);
		structure.put("TCAAG",  -9.98);
		structure.put("TCAAC",  -10.22);
		structure.put("TCATA",  -7.00);
		structure.put("TCATT",  -9.02);
		structure.put("TCATG",  -9.26);
		structure.put("TCATC",  -7.37);
		structure.put("TCAGA",  -6.32);
		structure.put("TCAGT",  -7.10);
		structure.put("TCAGG",  -5.31);
		structure.put("TCAGC",  -6.36);
		structure.put("TCACA",  -7.82);
		structure.put("TCACT",  -7.10);
		structure.put("TCACG",  -8.08);
		structure.put("TCACC",  -7.04);
		structure.put("TCTAA",  -6.61);
		structure.put("TCTAT",  -7.03);
		structure.put("TCTAG",  -6.98);
		structure.put("TCTAC",  -6.38);
		structure.put("TCTTA",  -8.51);
		structure.put("TCTTT",  -10.71);
		structure.put("TCTTG",  -9.12);
		structure.put("TCTTC",  -8.18);
		structure.put("TCTGA",  -6.32);
		structure.put("TCTGT",  -7.00);
		structure.put("TCTGG",  -6.09);
		structure.put("TCTGC",  -6.69);
		structure.put("TCTCA",  -6.62);
		structure.put("TCTCT",  -6.62);
		structure.put("TCTCG",  -6.80);
		structure.put("TCTCC",  -6.10);
		structure.put("TCGAA",  -5.99);
		structure.put("TCGAT",  -7.08);
		structure.put("TCGAG",  -6.72);
		structure.put("TCGAC",  -6.68);
		structure.put("TCGTA",  -6.48);
		structure.put("TCGTT",  -9.62);
		structure.put("TCGTG",  -7.95);
		structure.put("TCGTC",  -7.11);
		structure.put("TCGGA",  -3.04);
		structure.put("TCGGT",  -4.29);
		structure.put("TCGGG",  -2.92);
		structure.put("TCGGC",  -3.40);
		structure.put("TCGCA",  -3.81);
		structure.put("TCGCT",  -3.56);
		structure.put("TCGCG",  -4.27);
		structure.put("TCGCC",  -2.99);
		structure.put("TCCAA",  -2.74);
		structure.put("TCCAT",  -3.37);
		structure.put("TCCAG",  -2.98);
		structure.put("TCCAC",  -3.48);
		structure.put("TCCTA",  -1.39);
		structure.put("TCCTT",  -1.33);
		structure.put("TCCTG",  -0.90);
		structure.put("TCCTC",  -0.44);
		structure.put("TCCGA",  -3.04);
		structure.put("TCCGT",  -3.36);
		structure.put("TCCGG",  -3.19);
		structure.put("TCCGC",  -3.15);
		structure.put("TCCCA",  -2.42);
		structure.put("TCCCT",  -1.76);
		structure.put("TCCCG",  -2.56);
		structure.put("TCCCC",  -1.44);
		structure.put("GAAAA",  -13.16);
		structure.put("GAAAT",  -12.09);
		structure.put("GAAAG",  -11.93);
		structure.put("GAAAC",  -12.76);
		structure.put("GAATA",  -10.01);
		structure.put("GAATT",  -11.18);
		structure.put("GAATG",  -9.43);
		structure.put("GAATC",  -9.46);
		structure.put("GAAGA",  -8.18);
		structure.put("GAAGT",  -8.83);
		structure.put("GAAGG",  -7.38);
		structure.put("GAAGC",  -8.01);
		structure.put("GAACA",  -10.91);
		structure.put("GAACT",  -10.45);
		structure.put("GAACG",  -11.06);
		structure.put("GAACC",  -10.08);
		structure.put("GATAA",  -7.66);
		structure.put("GATAT",  -8.30);
		structure.put("GATAG",  -8.88);
		structure.put("GATAC",  -8.06);
		structure.put("GATTA",  -10.25);
		structure.put("GATTT",  -12.27);
		structure.put("GATTG",  -10.33);
		structure.put("GATTC",  -9.46);
		structure.put("GATGA",  -7.37);
		structure.put("GATGT",  -8.65);
		structure.put("GATGG",  -7.48);
		structure.put("GATGC",  -7.60);
		structure.put("GATCA",  -7.81);
		structure.put("GATCT",  -8.23);
		structure.put("GATCG",  -7.79);
		structure.put("GATCC",  -7.56);
		structure.put("GAGAA",  -2.40);
		structure.put("GAGAT",  -2.78);
		structure.put("GAGAG",  -2.54);
		structure.put("GAGAC",  -3.46);
		structure.put("GAGTA",  -3.05);
		structure.put("GAGTT",  -5.23);
		structure.put("GAGTG",  -3.31);
		structure.put("GAGTC",  -3.11);
		structure.put("GAGGA",  -0.44);
		structure.put("GAGGT",  -0.88);
		structure.put("GAGGG",  -0.03);
		structure.put("GAGGC",  -0.70);
		structure.put("GAGCA",  -0.94);
		structure.put("GAGCT",  -0.83);
		structure.put("GAGCG",  -1.32);
		structure.put("GAGCC",  -0.53);
		structure.put("GACAA",  -7.48);
		structure.put("GACAT",  -7.10);
		structure.put("GACAG",  -6.88);
		structure.put("GACAC",  -7.25);
		structure.put("GACTA",  -3.90);
		structure.put("GACTT",  -4.07);
		structure.put("GACTG",  -3.96);
		structure.put("GACTC",  -3.11);
		structure.put("GACGA",  -7.11);
		structure.put("GACGT",  -7.92);
		structure.put("GACGG",  -7.15);
		structure.put("GACGC",  -7.38);
		structure.put("GACCA",  -6.17);
		structure.put("GACCT",  -5.67);
		structure.put("GACCG",  -5.99);
		structure.put("GACCC",  -5.33);
		structure.put("GTAAA",  -11.89);
		structure.put("GTAAT",  -11.34);
		structure.put("GTAAG",  -10.31);
		structure.put("GTAAC",  -10.83);
		structure.put("GTATA",  -7.88);
		structure.put("GTATT",  -10.45);
		structure.put("GTATG",  -8.42);
		structure.put("GTATC",  -8.06);
		structure.put("GTAGA",  -6.38);
		structure.put("GTAGT",  -8.16);
		structure.put("GTAGG",  -6.19);
		structure.put("GTAGC",  -6.96);
		structure.put("GTACA",  -8.00);
		structure.put("GTACT",  -8.16);
		structure.put("GTACG",  -8.84);
		structure.put("GTACC",  -8.12);
		structure.put("GTTAA",  -10.44);
		structure.put("GTTAT",  -10.51);
		structure.put("GTTAG",  -10.65);
		structure.put("GTTAC",  -10.83);
		structure.put("GTTTA",  -13.54);
		structure.put("GTTTT",  -14.47);
		structure.put("GTTTG",  -13.10);
		structure.put("GTTTC",  -12.76);
		structure.put("GTTGA",  -10.22);
		structure.put("GTTGT",  -11.70);
		structure.put("GTTGG",  -9.80);
		structure.put("GTTGC",  -10.75);
		structure.put("GTTCA",  -10.38);
		structure.put("GTTCT",  -10.59);
		structure.put("GTTCG",  -10.48);
		structure.put("GTTCC",  -9.91);
		structure.put("GTGAA",  -6.21);
		structure.put("GTGAT",  -6.53);
		structure.put("GTGAG",  -6.50);
		structure.put("GTGAC",  -6.82);
		structure.put("GTGTA",  -6.58);
		structure.put("GTGTT",  -9.40);
		structure.put("GTGTG",  -8.16);
		structure.put("GTGTC",  -7.25);
		structure.put("GTGGA",  -3.48);
		structure.put("GTGGT",  -5.00);
		structure.put("GTGGG",  -2.94);
		structure.put("GTGGC",  -4.03);
		structure.put("GTGCA",  -3.74);
		structure.put("GTGCT",  -3.79);
		structure.put("GTGCG",  -4.13);
		structure.put("GTGCC",  -3.41);
		structure.put("GTCAA",  -7.07);
		structure.put("GTCAT",  -6.37);
		structure.put("GTCAG",  -6.43);
		structure.put("GTCAC",  -6.82);
		structure.put("GTCTA",  -3.62);
		structure.put("GTCTT",  -3.82);
		structure.put("GTCTG",  -3.97);
		structure.put("GTCTC",  -3.46);
		structure.put("GTCGA",  -6.68);
		structure.put("GTCGT",  -6.61);
		structure.put("GTCGG",  -6.74);
		structure.put("GTCGC",  -6.90);
		structure.put("GTCCA",  -5.78);
		structure.put("GTCCT",  -5.39);
		structure.put("GTCCG",  -6.07);
		structure.put("GTCCC",  -5.28);
		structure.put("GGAAA",  -10.09);
		structure.put("GGAAT",  -9.61);
		structure.put("GGAAG",  -9.44);
		structure.put("GGAAC",  -9.91);
		structure.put("GGATA",  -7.47);
		structure.put("GGATT",  -9.67);
		structure.put("GGATG",  -7.65);
		structure.put("GGATC",  -7.56);
		structure.put("GGAGA",  -6.10);
		structure.put("GGAGT",  -6.75);
		structure.put("GGAGG",  -5.75);
		structure.put("GGAGC",  -6.07);
		structure.put("GGACA",  -7.71);
		structure.put("GGACT",  -7.34);
		structure.put("GGACG",  -8.11);
		structure.put("GGACC",  -7.55);
		structure.put("GGTAA",  -7.21);
		structure.put("GGTAT",  -8.36);
		structure.put("GGTAG",  -8.07);
		structure.put("GGTAC",  -8.12);
		structure.put("GGTTA",  -10.83);
		structure.put("GGTTT",  -12.41);
		structure.put("GGTTG",  -4.14);
		structure.put("GGTTC",  -10.08);
		structure.put("GGTGA",  -7.04);
		structure.put("GGTGT",  -8.57);
		structure.put("GGTGG",  -7.11);
		structure.put("GGTGC",  -7.89);
		structure.put("GGTCA",  -7.96);
		structure.put("GGTCT",  -8.04);
		structure.put("GGTCG",  -8.09);
		structure.put("GGTCC",  -7.55);
		structure.put("GGGAA",  -4.39);
		structure.put("GGGAT",  -4.91);
		structure.put("GGGAG",  -4.60);
		structure.put("GGGAC",  -5.28);
		structure.put("GGGTA",  -5.01);
		structure.put("GGGTT",  -7.68);
		structure.put("GGGTG",  -5.89);
		structure.put("GGGTC",  -5.33);
		structure.put("GGGGA",  -1.44);
		structure.put("GGGGT",  -2.37);
		structure.put("GGGGG",  -1.03);
		structure.put("GGGGC",  -2.13);
		structure.put("GGGCA",  -2.52);
		structure.put("GGGCT",  -2.48);
		structure.put("GGGCG",  -2.89);
		structure.put("GGGCC",  -1.93);
		structure.put("GGCAA",  -3.14);
		structure.put("GGCAT",  -3.12);
		structure.put("GGCAG",  -3.20);
		structure.put("GGCAC",  -3.41);
		structure.put("GGCTA",  -1.22);
		structure.put("GGCTT",  -1.55);
		structure.put("GGCTG",  -1.20);
		structure.put("GGCTC",  -0.53);
		structure.put("GGCGA",  -2.99);
		structure.put("GGCGT",  -3.63);
		structure.put("GGCGG",  -3.13);
		structure.put("GGCGC",  -3.39);
		structure.put("GGCCA",  -2.54);
		structure.put("GGCCT",  -2.20);
		structure.put("GGCCG",  -2.72);
		structure.put("GGCCC",  -1.93);
		structure.put("GCAAA",  -10.88);
		structure.put("GCAAT",  -10.60);
		structure.put("GCAAG",  -9.90);
		structure.put("GCAAC",  -10.75);
		structure.put("GCATA",  -8.11);
		structure.put("GCATT",  -9.59);
		structure.put("GCATG",  -9.43);
		structure.put("GCATC",  -7.60);
		structure.put("GCAGA",  -6.69);
		structure.put("GCAGT",  -7.79);
		structure.put("GCAGG",  -5.92);
		structure.put("GCAGC",  -6.76);
		structure.put("GCACA",  -8.30);
		structure.put("GCACT",  -7.68);
		structure.put("GCACG",  -8.40);
		structure.put("GCACC",  -7.89);
		structure.put("GCTAA",  -6.12);
		structure.put("GCTAT",  -6.84);
		structure.put("GCTAG",  -6.54);
		structure.put("GCTAC",  -6.96);
		structure.put("GCTTA",  -8.92);
		structure.put("GCTTT",  -10.58);
		structure.put("GCTTG",  -8.63);
		structure.put("GCTTC",  -8.01);
		structure.put("GCTGA",  -6.36);
		structure.put("GCTGT",  -7.34);
		structure.put("GCTGG",  -5.87);
		structure.put("GCTGC",  -6.76);
		structure.put("GCTCA",  -6.75);
		structure.put("GCTCT",  -6.50);
		structure.put("GCTCG",  -6.78);
		structure.put("GCTCC",  -6.07);
		structure.put("GCGAA",  -6.08);
		structure.put("GCGAT",  -6.96);
		structure.put("GCGAG",  -6.31);
		structure.put("GCGAC",  -6.90);
		structure.put("GCGTA",  -7.22);
		structure.put("GCGTT",  -9.91);
		structure.put("GCGTG",  -7.98);
		structure.put("GCGTC",  -7.38);
		structure.put("GCGGA",  -3.15);
		structure.put("GCGGT",  -4.55);
		structure.put("GCGGG",  -2.75);
		structure.put("GCGGC",  -3.73);
		structure.put("GCGCA",  -3.94);
		structure.put("GCGCT",  -3.87);
		structure.put("GCGCG",  -4.22);
		structure.put("GCGCC",  -3.39);
		structure.put("GCCAA",  -3.85);
		structure.put("GCCAT",  -3.12);
		structure.put("GCCAG",  -3.34);
		structure.put("GCCAC",  -4.03);
		structure.put("GCCTA",  -1.23);
		structure.put("GCCTT",  -1.50);
		structure.put("GCCTG",  -1.28);
		structure.put("GCCTC",  -0.70);
		structure.put("GCCGA",  -3.40);
		structure.put("GCCGT",  -3.97);
		structure.put("GCCGG",  -3.58);
		structure.put("GCCGC",  -3.73);
		structure.put("GCCCA",  -2.77);
		structure.put("GCCCT",  -1.88);
		structure.put("GCCCG",  -2.73);
		structure.put("GCCCC",  -2.13);
		structure.put("CAAAA",  -13.79);
		structure.put("CAAAT",  -13.36);
		structure.put("CAAAG",  -12.85);
		structure.put("CAAAC",  -13.10);
		structure.put("CAATA",  -9.70);
		structure.put("CAATT",  -11.93);
		structure.put("CAATG",  -10.42);
		structure.put("CAATC",  -10.33);
		structure.put("CAAGA",  -9.12);
		structure.put("CAAGT",  -10.21);
		structure.put("CAAGG",  -9.16);
		structure.put("CAAGC",  -8.63);
		structure.put("CAACA",  -11.69);
		structure.put("CAACT",  -11.29);
		structure.put("CAACG",  -11.71);
		structure.put("CAACC",  -4.14);
		structure.put("CATAA",  -8.06);
		structure.put("CATAT",  -8.33);
		structure.put("CATAG",  -8.20);
		structure.put("CATAC",  -8.42);
		structure.put("CATTA",  -10.52);
		structure.put("CATTT",  -11.76);
		structure.put("CATTG",  -10.42);
		structure.put("CATTC",  -9.43);
		structure.put("CATGA",  -9.26);
		structure.put("CATGT",  -10.15);
		structure.put("CATGG",  -9.01);
		structure.put("CATGC",  -9.43);
		structure.put("CATCA",  -8.13);
		structure.put("CATCT",  -7.85);
		structure.put("CATCG",  -8.32);
		structure.put("CATCC",  -7.65);
		structure.put("CAGAA",  -3.52);
		structure.put("CAGAT",  -2.70);
		structure.put("CAGAG",  -3.06);
		structure.put("CAGAC",  -3.97);
		structure.put("CAGTA",  -4.20);
		structure.put("CAGTT",  -5.42);
		structure.put("CAGTG",  -4.52);
		structure.put("CAGTC",  -3.96);
		structure.put("CAGGA",  -0.90);
		structure.put("CAGGT",  -1.68);
		structure.put("CAGGG",  -0.14);
		structure.put("CAGGC",  -1.28);
		structure.put("CAGCA",  -1.72);
		structure.put("CAGCT",  -1.38);
		structure.put("CAGCG",  -1.72);
		structure.put("CAGCC",  -1.20);
		structure.put("CACAA",  -8.23);
		structure.put("CACAT",  -8.83);
		structure.put("CACAG",  -8.12);
		structure.put("CACAC",  -8.16);
		structure.put("CACTA",  -4.24);
		structure.put("CACTT",  -4.48);
		structure.put("CACTG",  -4.52);
		structure.put("CACTC",  -3.31);
		structure.put("CACGA",  -7.95);
		structure.put("CACGT",  -8.78);
		structure.put("CACGG",  -8.46);
		structure.put("CACGC",  -7.98);
		structure.put("CACCA",  -7.00);
		structure.put("CACCT",  -6.64);
		structure.put("CACCG",  -6.56);
		structure.put("CACCC",  -5.89);
		structure.put("CTAAA",  -11.18);
		structure.put("CTAAT",  -11.58);
		structure.put("CTAAG",  -10.31);
		structure.put("CTAAC",  -10.65);
		structure.put("CTATA",  -7.69);
		structure.put("CTATT",  -11.61);
		structure.put("CTATG",  -8.20);
		structure.put("CTATC",  -8.88);
		structure.put("CTAGA",  -6.98);
		structure.put("CTAGT",  -7.44);
		structure.put("CTAGG",  -6.51);
		structure.put("CTAGC",  -6.54);
		structure.put("CTACA",  -8.60);
		structure.put("CTACT",  -8.16);
		structure.put("CTACG",  -8.43);
		structure.put("CTACC",  -8.07);
		structure.put("CTTAA",  -9.57);
		structure.put("CTTAT",  -10.54);
		structure.put("CTTAG",  -10.31);
		structure.put("CTTAC",  -10.31);
		structure.put("CTTTA",  -12.85);
		structure.put("CTTTT",  -14.68);
		structure.put("CTTTG",  -12.85);
		structure.put("CTTTC",  -11.93);
		structure.put("CTTGA",  -9.98);
		structure.put("CTTGT",  -10.72);
		structure.put("CTTGG",  -9.71);
		structure.put("CTTGC",  -9.90);
		structure.put("CTTCA",  -10.24);
		structure.put("CTTCT",  -10.30);
		structure.put("CTTCG",  -9.80);
		structure.put("CTTCC",  -9.44);
		structure.put("CTGAA",  -5.95);
		structure.put("CTGAT",  -6.55);
		structure.put("CTGAG",  -6.08);
		structure.put("CTGAC",  -6.43);
		structure.put("CTGTA",  -6.49);
		structure.put("CTGTT",  -9.26);
		structure.put("CTGTG",  -8.12);
		structure.put("CTGTC",  -6.88);
		structure.put("CTGGA",  -2.98);
		structure.put("CTGGT",  -4.44);
		structure.put("CTGGG",  -2.47);
		structure.put("CTGGC",  -3.34);
		structure.put("CTGCA",  -3.68);
		structure.put("CTGCT",  -3.60);
		structure.put("CTGCG",  -3.85);
		structure.put("CTGCC",  -3.20);
		structure.put("CTCAA",  -6.36);
		structure.put("CTCAT",  -6.05);
		structure.put("CTCAG",  -6.08);
		structure.put("CTCAC",  -6.50);
		structure.put("CTCTA",  -3.22);
		structure.put("CTCTT",  -3.22);
		structure.put("CTCTG",  -3.06);
		structure.put("CTCTC",  -2.54);
		structure.put("CTCGA",  -6.72);
		structure.put("CTCGT",  -6.50);
		structure.put("CTCGG",  -6.12);
		structure.put("CTCGC",  -6.31);
		structure.put("CTCCA",  -5.45);
		structure.put("CTCCT",  -4.81);
		structure.put("CTCCG",  -5.07);
		structure.put("CTCCC",  -4.60);
		structure.put("CGAAA",  -10.58);
		structure.put("CGAAT",  -10.56);
		structure.put("CGAAG",  -9.80);
		structure.put("CGAAC",  -10.48);
		structure.put("CGATA",  -7.63);
		structure.put("CGATT",  -9.51);
		structure.put("CGATG",  -8.32);
		structure.put("CGATC",  -7.79);
		structure.put("CGAGA",  -6.80);
		structure.put("CGAGT",  -7.78);
		structure.put("CGAGG",  -6.32);
		structure.put("CGAGC",  -6.78);
		structure.put("CGACA",  -8.25);
		structure.put("CGACT",  -8.15);
		structure.put("CGACG",  -8.49);
		structure.put("CGACC",  -8.09);
		structure.put("CGTAA",  -7.71);
		structure.put("CGTAT",  -9.05);
		structure.put("CGTAG",  -8.43);
		structure.put("CGTAC",  -8.84);
		structure.put("CGTTA",  -11.31);
		structure.put("CGTTT",  -13.15);
		structure.put("CGTTG",  -11.71);
		structure.put("CGTTC",  -11.06);
		structure.put("CGTGA",  -8.08);
		structure.put("CGTGT",  -9.43);
		structure.put("CGTGG",  -7.87);
		structure.put("CGTGC",  -8.40);
		structure.put("CGTCA",  -8.47);
		structure.put("CGTCT",  -8.81);
		structure.put("CGTCG",  -8.49);
		structure.put("CGTCC",  -8.11);
		structure.put("CGGAA",  -5.36);
		structure.put("CGGAT",  -4.49);
		structure.put("CGGAG",  -5.07);
		structure.put("CGGAC",  -6.07);
		structure.put("CGGTA",  -5.99);
		structure.put("CGGTT",  -7.97);
		structure.put("CGGTG",  -6.56);
		structure.put("CGGTC",  -5.99);
		structure.put("CGGGA",  -2.56);
		structure.put("CGGGT",  -3.53);
		structure.put("CGGGG",  -1.95);
		structure.put("CGGGC",  -2.73);
		structure.put("CGGCA",  -3.11);
		structure.put("CGGCT",  -3.04);
		structure.put("CGGCG",  -3.17);
		structure.put("CGGCC",  -2.72);
		structure.put("CGCAA",  -4.14);
		structure.put("CGCAT",  -4.03);
		structure.put("CGCAG",  -3.85);
		structure.put("CGCAC",  -4.13);
		structure.put("CGCTA",  -1.63);
		structure.put("CGCTT",  -1.91);
		structure.put("CGCTG",  -1.72);
		structure.put("CGCTC",  -1.32);
		structure.put("CGCGA",  -4.27);
		structure.put("CGCGT",  -4.73);
		structure.put("CGCGG",  -4.25);
		structure.put("CGCGC",  -4.22);
		structure.put("CGCCA",  -2.98);
		structure.put("CGCCT",  -2.82);
		structure.put("CGCCG",  -3.17);
		structure.put("CGCCC",  -2.89);
		structure.put("CCAAA",  -9.32);
		structure.put("CCAAT",  -10.72);
		structure.put("CCAAG",  -9.71);
		structure.put("CCAAC",  -9.80);
		structure.put("CCATA",  -6.69);
		structure.put("CCATT",  -8.98);
		structure.put("CCATG",  -9.01);
		structure.put("CCATC",  -7.48);
		structure.put("CCAGA",  -6.09);
		structure.put("CCAGT",  -7.24);
		structure.put("CCAGG",  -5.54);
		structure.put("CCAGC",  -5.87);
		structure.put("CCACA",  -7.33);
		structure.put("CCACT",  -7.40);
		structure.put("CCACG",  -7.87);
		structure.put("CCACC",  -7.11);
		structure.put("CCTAA",  -5.77);
		structure.put("CCTAT",  -6.87);
		structure.put("CCTAG",  -6.51);
		structure.put("CCTAC",  -6.19);
		structure.put("CCTTA",  -8.13);
		structure.put("CCTTT",  -9.21);
		structure.put("CCTTG",  -9.16);
		structure.put("CCTTC",  -7.38);
		structure.put("CCTGA",  -5.31);
		structure.put("CCTGT",  -6.78);
		structure.put("CCTGG",  -5.54);
		structure.put("CCTGC",  -5.92);
		structure.put("CCTCA",  -6.36);
		structure.put("CCTCT",  -6.38);
		structure.put("CCTCG",  -6.32);
		structure.put("CCTCC",  -5.75);
		structure.put("CCGAA",  -6.08);
		structure.put("CCGAT",  -7.15);
		structure.put("CCGAG",  -6.12);
		structure.put("CCGAC",  -6.74);
		structure.put("CCGTA",  -6.70);
		structure.put("CCGTT",  -10.04);
		structure.put("CCGTG",  -8.46);
		structure.put("CCGTC",  -7.15);
		structure.put("CCGGA",  -3.19);
		structure.put("CCGGT",  -5.03);
		structure.put("CCGGG",  -3.26);
		structure.put("CCGGC",  -3.58);
		structure.put("CCGCA",  -4.13);
		structure.put("CCGCT",  -3.78);
		structure.put("CCGCG",  -4.25);
		structure.put("CCGCC",  -3.13);
		structure.put("CCCAA",  -3.45);
		structure.put("CCCAT",  -2.37);
		structure.put("CCCAG",  -2.47);
		structure.put("CCCAC",  -2.94);
		structure.put("CCCTA",  -0.56);
		structure.put("CCCTT",  -0.14);
		structure.put("CCCTG",  -0.14);
		structure.put("CCCTC",  -0.03);
		structure.put("CCCGA",  -2.92);
		structure.put("CCCGT",  -2.81);
		structure.put("CCCGG",  -3.26);
		structure.put("CCCGC",  -2.75);
		structure.put("CCCCA",  -2.02);
		structure.put("CCCCT",  -1.32);
		structure.put("CCCCG",  -1.95);
		structure.put("CCCCC",  -1.03);
	}
}
