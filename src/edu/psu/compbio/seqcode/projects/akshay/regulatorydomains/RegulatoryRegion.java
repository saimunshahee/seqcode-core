package edu.psu.compbio.seqcode.projects.akshay.regulatorydomains;

import java.util.ArrayList;
import java.util.List;

import edu.psu.compbio.seqcode.genome.location.Point;
import edu.psu.compbio.seqcode.gse.datasets.motifs.WeightMatrix;
import edu.psu.compbio.seqcode.gse.gsebricks.verbs.motifs.WeightMatrixScoreProfile;
import edu.psu.compbio.seqcode.gse.gsebricks.verbs.motifs.WeightMatrixScorer;
import edu.psu.compbio.seqcode.projects.seed.features.Feature;

/**
 * 
 * @author akshaykakumanu
 *
 */
public abstract class RegulatoryRegion implements Comparable<RegulatoryRegion>{
	private Point peak;
	private List<Point> homotypic;
	private GeneDomain targetGene;
	private List<Double> motifsLogOdds;
	// Binding dynamics index if any (Can be fold-change of ChIP intensity between two developmental time-points at the ChIP peak)
	protected double bindingDynamicsIndex; 
	protected double bindingStrength;
	// Number of binding events per 100bp genomic region
	private double homotypicIndex;
	// Length of the regulatory region
	private int win;
	private String seq;
	
	 public RegulatoryRegion(Point p, double pStrength, double pDynamics, int w, List<WeightMatrix> motifs, String seq) {
		 this(p, pStrength, w, motifs, seq);
		 bindingDynamicsIndex = pDynamics;
	}
	
	 public RegulatoryRegion(Point p, double pStrength, int w, List<WeightMatrix> motifs, String s){
		 peak = p;
		 bindingStrength = pStrength;
		 win = w;
		 seq =s;
		 scanMotifs(motifs,s);
		 homotypic = new ArrayList<Point>();
		 
	 }
	
	 private void scanMotifs(List<WeightMatrix> motifs, String seq){
		 motifsLogOdds = new ArrayList<Double>();
		 for(int m=0; m<motifs.size(); m++){
			 WeightMatrix motif = motifs.get(m);
			 WeightMatrixScorer scorer = new WeightMatrixScorer(motif);
			 WeightMatrixScoreProfile profiler = scorer.execute(seq);
			 motifsLogOdds.add(profiler.getMaxScore());
		 }
	 }
	 
	 public void setTargetGene(GeneDomain g){targetGene = g;}
	 public void addHomotypicPeak(Point p){
		 homotypic.add(p);
		 homotypicIndex = homotypic.size();
	}
	 
	public boolean coversPeak(Point p){
		return peak.expand(win).contains(p);
	}
	 
	//Gettors
	public double getBindingIntensity(){return bindingStrength;}
	public String getPeakLocation(){return peak.getLocationString();}
	public double getBindingDynamicsIndex(){return bindingDynamicsIndex;}
	public double getHomotypicIndex(){return homotypicIndex;}
	public int getTargetGeneClusterIndex(){return targetGene.getClusterIndex();}
	public double getTargetGeneFoldChange(){return targetGene.getFoldChange();}
	public double getBestMotifScore(int motifIndex){return motifsLogOdds.get(motifIndex);}
	public String getTargetGeneName(){return targetGene.getGeneName();}
	public String getChrom(){return peak.getChrom();}
	
	
	

}
