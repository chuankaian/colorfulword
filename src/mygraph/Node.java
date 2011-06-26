package mygraph;

import wndata.Synset;

public class Node{
	public boolean focus;
	public Synset synset;
	public Node(Synset synset){
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
