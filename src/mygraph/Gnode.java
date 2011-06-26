package mygraph;

import wndata.Synset;

public class Gnode{
	public boolean focus;
	public Synset synset;
	public Gnode(Synset synset){
		this.focus=false;
		this.synset=synset;
	}
	public String toString(){
		if (focus)
			return synset.toString();
		else
			return synset.getWords()[0].getWord();
	}
}
