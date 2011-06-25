package coloring;

import wndata.*;

public class ColorManager{
	private static ColorManager instance;
	private ColorManager(){}
	public static ColorManager getSingleton(){
		if (instance==null){
			instance=new ColorManager();
		}
		return instance;
	}
	public boolean setColorByUser(String word, Synset synset){
		return true;
	}
}
