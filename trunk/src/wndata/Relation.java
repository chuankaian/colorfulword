package wndata;

import java.io.IOException;

/**
 * Describes the relation between one synset and another.
 */
public class Relation {

    protected PointerSymbol pointer_symbol;
    protected int synset_offset;
    protected PartOfSpeech pos;
    protected int source_target;

    public Relation(PointerSymbol _pointer_symbol,PartOfSpeech _pos,int _synset_offset,int _source_target)
    {
        pointer_symbol = _pointer_symbol;
        pos = _pos;
        synset_offset = _synset_offset;
        source_target = _source_target;
    }

    public PointerSymbol getPointerSymbol() {
        return pointer_symbol;
    }
    public int getSynsetOffset() {
        return synset_offset;
    }
    public PartOfSpeech getPartOfSpeech() {
        return pos;
    }
    public int getSourceTarget() {
        return source_target;
    }

    public String toString() {
        return pointer_symbol + "(" +
            synset_offset + ", " + pos + ", " + source_target + ")";
    }

    /**
     * Return true if this SynsetPointer denotes a semantic relation
     * between the source and target synsets.
     * @see http://wordnet.princeton.edu/wordnet/man/wndb.5WN.html
     */
    public boolean isSemantic() {
        return source_target == 0;
    }

    /**
     * Return string description of this SynsetPointer's type.
     * @see http://wordnet.princeton.edu/wordnet/man/wninput.5WN.html
     */
    //       LZYNOTE:======这个函数还要细看一下========
    public String getDescription() {
        // Special case for backslash
        if (pointer_symbol == PointerSymbol.BACKSLASH) {
            // If it's for an adjective... (perhaps ADJS also?)
            if (pos.equals(PartOfSpeech.ADJ)) {
                // ...Then it's a pertainym
                return "pertainym (pertains to noun)";
            } else if (pos.equals(PartOfSpeech.ADV)) {
                // ... Or if it's for an adverb, then it's derived from adj
                return "derived from adjective";
            } else {
                return "unknown (backslash)";
            }
        } else {
            return pointer_symbol.getDescription();
        }
    }
}
