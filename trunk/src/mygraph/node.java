package mygraph;

import wndata.Synset;

public class node{
	public boolean focus;
	public Synset synset;
	public node(Synset synset){
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
