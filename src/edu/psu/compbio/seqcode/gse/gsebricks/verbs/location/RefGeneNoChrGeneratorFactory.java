package edu.psu.compbio.seqcode.gse.gsebricks.verbs.location;

import edu.psu.compbio.seqcode.genome.Genome;
import edu.psu.compbio.seqcode.genome.location.Gene;
import edu.psu.compbio.seqcode.genome.location.Region;
import edu.psu.compbio.seqcode.gse.gsebricks.GeneFactory;
import edu.psu.compbio.seqcode.gse.gsebricks.RegionExpanderFactory;
import edu.psu.compbio.seqcode.gse.gsebricks.verbs.Expander;

/**
 * Special case of RefGeneGeneratorFactory to not prepend "chr" to chromosomes
 * @author tdanford
 */
public class RefGeneNoChrGeneratorFactory implements RegionExpanderFactory<Gene>, GeneFactory {
    private String type;

    public RefGeneNoChrGeneratorFactory() {
    }

    public void setType(String t) {type = t;}
    public String getType() {return type;}
    public String getProduct() {return "Gene";}
    public Expander<Region, Gene> getExpander(Genome g) {
        return getExpander(g,type);
    }

    public Expander<Region, Gene> getExpander(Genome g, String type) {
        if (type == null) {
            return new RefGeneGenerator(g, false);
        } else {
            RefGeneGenerator gg = new RefGeneGenerator(g, type, false);
            return gg;
        }
    }
}
