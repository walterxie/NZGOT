package nzgo.toolkit.core.community;


/**
 * Dereplication is the removal of duplicated sequences
 * http://www.drive5.com/usearch/manual/dereplication.html
 * @author Walter Xie
 */
public class DereplicatedSequence extends Hit{

    // size = duplicated sequences removed + itself
    protected int derepSize = 0;

    public DereplicatedSequence(String name) {
        super(name);
    }

    public DereplicatedSequence(String name, double identity, int derepSize) {
        super(name, identity);
        setDerepSize(derepSize);
    }

    public int getDerepSize() {
        return derepSize;
    }

    public void setDerepSize(int derepSize) {
        this.derepSize = derepSize;
    }

}
