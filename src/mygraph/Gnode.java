package mygraph;

import wndata.Synset;

public class Gnode{
	public Synset synset;
	public Gnode(Synset synset){
		this.synset=synset;
	}
	public String toString(){
		if (synset.getWordCount()==1)
			return "<html><center>"+synset.getWords()[0].getWord();
		else if (synset.getWordCount()==2)
			return "<html><center>"+synset.getWords()[0].getWord()
					+"<p>"+synset.getWords()[1].getWord();
		else
			return "<html><center>"+synset.getWords()[0].getWord()
					+"<p>"+synset.getWords()[1].getWord()
					+"<p>"+synset.getWords()[2].getWord();
	}
}
