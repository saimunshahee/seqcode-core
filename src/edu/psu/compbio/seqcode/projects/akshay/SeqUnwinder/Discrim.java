package edu.psu.compbio.seqcode.projects.akshay.SeqUnwinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.psu.compbio.seqcode.genome.GenomeConfig;
import edu.psu.compbio.seqcode.genome.location.Point;
import edu.psu.compbio.seqcode.genome.location.Region;
import edu.psu.compbio.seqcode.gse.datasets.motifs.WeightMatrix;
import edu.psu.compbio.seqcode.gse.gsebricks.verbs.sequence.SequenceGenerator;
import edu.psu.compbio.seqcode.gse.tools.utils.Args;
import edu.psu.compbio.seqcode.gse.utils.ArgParser;
import edu.psu.compbio.seqcode.gse.utils.Pair;
import edu.psu.compbio.seqcode.gse.utils.io.RegionFileUtilities;
import edu.psu.compbio.seqcode.gse.utils.sequence.SequenceUtils;
import edu.psu.compbio.seqcode.projects.akshay.SeqUnwinder.clusterkmerprofile.ClusterProfiles;
import edu.psu.compbio.seqcode.projects.akshay.utils.MemeER;

public class Discrim {
	
	protected GenomeConfig gcon;
	/** Stores the weights of the K-mers
	 *  Keys are the model name
	 * 	Values are the K-mers weights for a given model */ 
	protected HashMap<String,double[]> kmerweights = new HashMap<String,double[]>();
	/** Model names */
	protected List<String> kmerModelNames = new ArrayList<String>();
	/** All the peaks from all classes */
	protected List<Point> peaks = new ArrayList<Point>();
	/** Peaks extended into regions */
	protected List<Region> regions = new ArrayList<Region>();
	/** Minimum length of K-mer in the model */
	protected int minK;
	/** Maximum length of K-mer in the model */ 
	protected int maxK;
	/** Minimum length to consider for motif finding */
	protected int minM=4;
	/** Maximum length for motif finding */
	protected int maxM=10;
	/** Number K-mers in the model */
	protected int numK;
	/** The base name of the output directory */
	protected String outbase;
	/** The output directory file */
	protected File outdir;
	SequenceGenerator<Region> seqgen = null;
	/** The minimum value of model scan score to consider form motif finding */
	protected double thresold_hills = 0.1;
	/** Window around peaks to scan for finding motifs */
	protected int win=150;
	
	// K-mer hills clustering parameters
	/** The number of hills to consider for clustering and motif finding for a given K-mer model */
	public final int numHills = 1500;
	/** No of iterations of clustering */
	protected int its_CLUS=10;
	/** Number of cluster; ideally each cluster corresponds to a motif */
	protected int numClus_CLUS=3;
	
	// Meme parameters
	protected String MEMEpath;
	protected String MEMEargs = " -dna -mod zoops -revcomp -nostatus ";
	protected int MEMEminw = 6;
	protected int MEMEmaxw = 11;
	protected int MEMEnmotifs = 3;
	protected int MEMEwin = 16;
	protected String[] randomSequences = new String[5000];
	
	
	// Settors
	public void setWin(int w){win=w;}
	public void setMinM(int m){minM = m;}
	public void setMaxM(int m){maxM = m;}
	public void setKmerMin(int k){minK = k;}
	public void setKmerMax(int k){maxK = k;}
	public void setNumK(){
		numK = 0;
		for(int k=minK; k<=maxK; k++ ){
			numK += (int)Math.pow(4, k);
		}
	}
	public void setKmerWeights(String weightFileName) throws NumberFormatException, IOException{
		BufferedReader reader = new BufferedReader(new FileReader(weightFileName));
        String line;
        boolean header = false;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String[] words = line.split("\t");
            if(words[0].charAt(0) == '#' || words[0].contains("Variable") || words[0].contains("Class")){
            	header = true;
            	for(int i=1; i<words.length; i++){
            		words[i] = words[i].replace('&', '_');
            		kmerModelNames.add(words[i]);
            		kmerweights.put(words[i], new double[numK]);
            	}
            }else{
            	if(!header){
            		System.err.println("Please provide a header in the K-mer weight file");
            		System.exit(1);
            	}
            	int ind = getKmerBaseInd(words[0]) + RegionFileUtilities.seq2int(words[0]);
            	for(int i = 1; i < words.length; i++ ){
            		kmerweights.get(kmerModelNames.get(i-1))[ind] = Double.parseDouble(words[i]);
            	}
            }
           
        }reader.close();
	}
	public void setPeaks(String peaksFileName){
		peaks.addAll(RegionFileUtilities.loadPeaksFromPeakFile(gcon.getGenome(), peaksFileName, win));
		regions.addAll(RegionFileUtilities.loadRegionsFromPeakFile(gcon.getGenome(), peaksFileName, win));
	}
	public void setNumKmerClusters(int c){numClus_CLUS = c;}
	public void setNumClusteringItrs(int itrs){its_CLUS = itrs;}
	public void setOutbase(String o){outbase=o;}
	public void setRandomRegs(){
		// Generate random sequences later need for meme analysis to assess motifs
		
		List<Region> randomRegions = MemeER.randomRegionPick(gcon.getGenome(), null, MemeER.MOTIF_FINDING_NEGSEQ,win);
		for(int i=0; i<randomRegions.size(); i++){
			randomSequences[i] = seqgen.execute(randomRegions.get(i));
		}
	}
	// Settors for MEME params
	public void setMEMEPath(String s){MEMEpath = s;}
	public void setMEMEminW(int w){MEMEminw = w;}
	public void setMEMEmaxW(int w){MEMEmaxw = w;}
	public void setMEMEsearchWin(int w){MEMEwin = w;}
	public void setMEMEnmotifs(int n){MEMEnmotifs = n;}
	public void setHillThreshold(double t){thresold_hills = t;}
	
	
	@SuppressWarnings("unchecked")
	public Discrim(GenomeConfig gc) {
		gcon = gc;
		seqgen = gcon.getSequenceGenerator();
	}
	
	public void execute() throws IOException{
		for(String s : kmerweights.keySet()){
			KmerModelScanner scanner = new KmerModelScanner(s);
			scanner.execute();
		}
		
	}
	
	
	//Slave methods
	public void makeOutPutDirs(String out_name){
		//Test if output directory already exists. If it does,  recursively delete contents
		outdir =  new File(out_name);
		if(outdir.exists())
			deleteDirectory(outdir);
		outbase = outdir.getName();
		//(re)make the output directory
		outdir.mkdirs();
		
		for(String s : kmerModelNames){
			File memeDir = new File(outdir.getAbsoluteFile()+File.separator+s+File.separator+"meme");
			memeDir.mkdirs();
			File kmerProfDir = new File(outdir.getAbsoluteFile()+File.separator+s+File.separator+"kmer_profiles");
			kmerProfDir.mkdirs();
		}

	}
	
	public boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
	    }
		return( path.delete() );
	}
	
	// Gettors
	public int getKmerBaseInd(String kmer){
		int baseInd = 0;
		for(int k=minK; k<kmer.length(); k++){
			baseInd += (int)Math.pow(4, k);
		}
		return baseInd;
	}
	
	
	
	public class KmerModelScanner {
		
		protected String kmerModelName;
		protected List<Region> posHills = new ArrayList<Region>();
		protected ArrayList<int[]> profiles = new ArrayList<int[]>();
		protected List<Integer> clusterAssignment = new ArrayList<Integer>();
		protected HashMap<Integer,String> posHillsToIndex =new HashMap<Integer,String>();
		protected HashMap<Integer,Double> posHillScores =new HashMap<Integer,Double>();
		protected File basedir_profiles;
		protected File basedir_meme;
		
		/** 
		 * Constructor
		 */
		public KmerModelScanner(String modName) {
			setKmerModName(modName);
			basedir_profiles = new File(outdir.getAbsoluteFile()+File.separator+kmerModelName+File.separator+"kmer_profiles");
			basedir_meme = new File(outdir.getAbsoluteFile()+File.separator+kmerModelName+File.separator+"meme");
		}
		
		
		public void execute() throws IOException{
			// First, find, cluster and print the hills
			clusterKmerProfilesAtMountains();
			if(posHills.size() > 100){
				System.err.println("Finished clustering K-mer profiles for model: "+kmerModelName);

				// Now do meme search on each clusters separately
				String memeargs = MEMEargs+" -nmotifs "+MEMEnmotifs + " -minw "+MEMEminw+" -maxw "+MEMEmaxw;
				MemeER meme = new MemeER(MEMEpath, memeargs);
				for(int c=0; c<numClus_CLUS; c++){ // Over each cluster
					System.err.println("Loading sequences for meme analysis : "+kmerModelName+ "Cluster"+c);
					List<String> seqs = new ArrayList<String>();
					for(int p=0; p<posHills.size(); p++){
						if(clusterAssignment.get(p) == c){
							Region tmpR = posHills.get(p).getMidpoint().expand(MEMEwin/2);
							String s = seqgen.execute(tmpR);
							if(MemeER.lowercaseFraction(s)<=MemeER.MOTIF_FINDING_ALLOWED_REPETITIVE){
								seqs.add(s);
							}
						}
					}
					List<WeightMatrix> selectedMotifs = new ArrayList<WeightMatrix>();
					File meme_outFile = new File(basedir_meme+File.separator+"Cluster-"+Integer.toString(c)+"_meme");

					System.err.println("Running meme now");
					Pair<List<WeightMatrix>,List<WeightMatrix>> matrices = meme.execute(seqs, meme_outFile, false);
					System.err.println("Finished running meme, Now evaluating motif significance");
					List<WeightMatrix> wm = matrices.car();
					List<WeightMatrix> fm = matrices.cdr();
					if(wm.size()>0){
						//Evaluate the significance of the discovered motifs
						double rocScores[] = meme.motifROCScores(wm,seqs,randomSequences);
						System.err.println("MEME results for:" );
						for(int w=0; w<fm.size(); w++){
							if(fm.get(w)!=null){
								System.err.println("\t"+fm.get(w).getName()+"\t"+ WeightMatrix.getConsensus(fm.get(w))+"\tROC:"+String.format("%.2f",rocScores[w]));
							}
							if(rocScores[w] > MemeER.MOTIF_MIN_ROC){
								selectedMotifs.add(fm.get(w));
							}
						}
					}

					// Print ROC values into a file names "Motifs.ROC"
					File motifs_roc = new File(basedir_meme+File.separator+"Cluster-"+Integer.toString(c)+"_motifs.roc");
					FileWriter fw = new FileWriter(motifs_roc);
					BufferedWriter bw = new BufferedWriter(fw);

					for(int m =0; m<selectedMotifs.size(); m++){
						String out = WeightMatrix.printTransfacMatrix(selectedMotifs.get(m),"Motif_"+Integer.toString(m));
						bw.write(out);
					}
					bw.close();
				}
			}
		
		}
		
		
		//Settors
		public void setKmerModName(String name){kmerModelName = name;}
		
		//Gettors
		public int getKmerBaseInd(String kmer){
			int len = kmer.length();
			int baseInd = 0;
			for(int k=minK; k<kmer.length(); k++){
				baseInd += (int)Math.pow(4, k);
			}
			return baseInd;
		}
		
		//Fillers
		private void fillHills() throws IOException{
			List<Pair<Region,Double>> hills= new ArrayList<Pair<Region,Double>>();
			hills= findHills();
			List<Pair<Region,Double>> top_hills = sorthills(hills);
			int index=0;
			HashMap<String,Integer> addedHills = new HashMap<String,Integer>();
			for(Pair<Region,Double> pr : top_hills){
				if(!addedHills.containsKey(pr.car().getLocationString())){
					posHills.add(pr.car());
					posHillsToIndex.put(index,pr.car().getLocationString() );
					posHillScores.put(index++, pr.cdr());
					addedHills.put(pr.car().getLocationString(), 1);
				}
			}
			profiles = getProfilesAtPeaks(posHills);
		}
		
		private List<Pair<Region,Double>> sorthills(List<Pair<Region,Double>> hlls){
			List<Pair<Region,Double>> ret = new ArrayList<Pair<Region,Double>>();
			HillsIndexComparator comp = new HillsIndexComparator(hlls);
			Integer[] indicies = comp.createIndexArray();
			Arrays.sort(indicies, comp);
			
			for(int i=0; i<Math.min(hlls.size(),numHills); i++){
				ret.add(hlls.get(indicies[i]));
			}
			
			return ret;
		}
		
		/**
		 * Prints the mountain composition
		 * @param useCache
		 * @param genpath
		 * @param oddsThresh
		 */
		public void clusterKmerProfilesAtMountains() throws IOException{
			fillHills();
			System.err.println("No of hills for K-mer model "+kmerModelName+" are :"+posHills.size());
			if(posHills.size() > 100){
				ClusterProfiles clusterManager = new ClusterProfiles(its_CLUS,numClus_CLUS,profiles,posHillsToIndex,minK,maxK,posHillScores,basedir_profiles);
				clusterAssignment = clusterManager.execute();
			}
		}
		
		private ArrayList<int[]> getProfilesAtPeaks(List<Region> rs){
			ArrayList<int[]> ret = new ArrayList<int[]>();
			
			for(Region r: rs){
				int numK = 0;
				for(int k=minK; k<=maxK; k++){
					numK += (int)Math.pow(4, k);
				}
				int[] pfl = new int[numK];
				String seq = seqgen.execute(r).toUpperCase();
				for(int k=minK; k<=maxK; k++){
					for(int i=0; i<(seq.length()-k+1); i++){
						String currk = seq.substring(i, i+k);
						String revcurrk = SequenceUtils.reverseComplement(currk);
						int currKInt = RegionFileUtilities.seq2int(currk);
						int revcurrKInt = RegionFileUtilities.seq2int(revcurrk);
						int kmer = currKInt<revcurrKInt ? currKInt : revcurrKInt;
						int baseind = this.getKmerBaseInd(currk);	
						pfl[baseind+kmer]++;
					}
				}
				ret.add(pfl);
			}
			
			return ret;
		}
		
		
		
		/**
		 * Finds mountains for a given list of regions and given scoring threshold
		 * Odds threshold can be -ve when scanning the model at negPeaks. +ve when scanning the model at posPeaks
		 * Also populates the 
		 * @param useCache
		 * @param genpath
		 * @param oddsThresh
		 */
		private List<Pair<Region,Double>> findHills(){
			
			// Detects if scanning is done for the positive or neg set from the sign of oddsThresh
			//int classDetector = oddsThresh>0 ? 1:-1;
			
			List<Pair<Region,Double>> ret = new ArrayList<Pair<Region,Double>>();
			
			for(Region r : regions){
				String seq = seqgen.execute(r).toUpperCase();
				if(seq.contains("N"))
					continue;
				List<Pair<Region,Double>> mountains = new ArrayList<Pair<Region,Double>>();
				for(int l=minM; l<=maxM; l++){
					for(int i=0; i<(seq.length()-l+1); i++){
						String motif = seq.substring(i, i+l);
						double score=0.0;
						for(int k=minK; k<=maxK; k++){
							for(int j=0; j<motif.length()-k+1; j++){
								String currk = motif.substring(j, j+k);
								String revcurrk = SequenceUtils.reverseComplement(currk);
								int  currKInt = RegionFileUtilities.seq2int(currk);
								int  revCurrKInt = RegionFileUtilities.seq2int(revcurrk);
								int kmer = currKInt<revCurrKInt ? currKInt : revCurrKInt;
								int baseInd = this.getKmerBaseInd(currk);
								score = score+kmerweights.get(kmerModelName)[baseInd+kmer];
							}
						}
						
						Region hill = new Region(gcon.getGenome(),r.getChrom(),r.getStart()+i,r.getStart()+i+l-1);
						
						Iterator<Pair<Region,Double>> it = mountains.iterator();
						boolean add=true;
						while(it.hasNext() && add){
							Pair<Region,Double> pr = it.next();
							Region currHill = pr.car();
							Double currScore = pr.cdr();
							if(currHill.overlaps(hill) && currScore<score){
								it.remove();
								add=true;
							}else if(currHill.overlaps(hill) && currScore> score){
								add=false;
							}
						}
						if(add && score > thresold_hills){
							mountains.add(new Pair<Region,Double>(hill,score));
						}
						
					}
				}
				ret.addAll(mountains);	
			}
			return ret;
		}
		
		public class HillsIndexComparator implements Comparator<Integer>{
			
			List<Pair<Region,Double>> hill;
			
			public HillsIndexComparator(List<Pair<Region,Double>> h) {
				hill = h;
			}
			
			public Integer[] createIndexArray(){
				Integer[] indexes = new Integer[hill.size()];
				for(int i=0; i<indexes.length; i++){
					indexes[i] = i;
				}
				return indexes;
			}

			@Override
			public int compare(Integer o1, Integer o2) {
				return -1*(hill.get(o1).cdr().compareTo(hill.get(o2).cdr()));
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException{
		ArgParser ap = new ArgParser(args);
		GenomeConfig gc = new GenomeConfig(args);
		Discrim runner = new Discrim(gc);
		
		// Size of the window to scan the K-mer models
		int win = Args.parseInteger(args, "win", 150);
		runner.setWin(win);
		
		// Minimum length of the region to find hills
		int minM = Args.parseInteger(args, "minM", 4);
		runner.setMinM(minM);
		
		// Maximum length of the region to find hills
		int maxM = Args.parseInteger(args, "maxM", 10);
		runner.setMaxM(maxM);
		
		// Length of the smallest K-mer in the K-mer models
		int minK = Args.parseInteger(args, "minK", 4);
		runner.setKmerMin(minK);
		
		// Length of the largest K-mer in the K-mer models
		int maxK = Args.parseInteger(args, "maxK", 6);
		runner.setKmerMax(maxK);
		
		runner.setNumK();
		
		// K-mer models file / weights file
		String weights = Args.parseString(args, "weights", null);
		if(weights == null){
			System.err.println("Provide weights file");
			System.exit(1);
		}
		runner.setKmerWeights(weights);
		
		// Name of the output folder to write all the output file names
		String out = Args.parseString(args, "out", "out");
		runner.setOutbase(out);
		
		runner.makeOutPutDirs(out);
		
		// Threshold for calling hills
		double threshold = Args.parseDouble(args, "hillsThres", 0.1);
		runner.setHillThreshold(threshold);
		
		// Peaks file name
		String peaksfilename = Args.parseString(args, "peaks", null);
		if(peaksfilename == null){
			System.err.println("Provide peaks file");
			System.exit(1);
		}
		runner.setPeaks(peaksfilename);
		
		// Set number of clusters while clustering K-mer profiles
		int numClus = Args.parseInteger(args, "numClusters", 3);
		runner.setNumKmerClusters(numClus);
		
		// Seq number of iterations for K-means clustering of K-mer profiles
		int itrs_Clus = Args.parseInteger(args, "itrsClustering", 10);
		runner.setNumClusteringItrs(itrs_Clus);
		
		runner.setRandomRegs();
		
		// Path to MEME binary
		String memePATH = Args.parseString(args, "memePath", null);
		if(memePATH == null){
			System.err.println("Provide meme path");
			System.exit(1);
		}
		runner.setMEMEPath(memePATH);
		
		// minimum size of motif for meme
		int memeMinW = Args.parseInteger(args, "MEME_minW", 6);
		runner.setMEMEminW(memeMinW);
		
		//maximum size of motif for meme
		int memeMaxW = Args.parseInteger(args, "MEME_maxW", 11);
		runner.setMEMEmaxW(memeMaxW);
		
		//Size of the focussed meme search win
		int memeWin = Args.parseInteger(args, "memeSearchWin", 15);
		runner.setMEMEsearchWin(memeWin);
	
		
		runner.execute();

	}
}
