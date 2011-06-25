package coloring;

import wndata.*;

public class ColorManager implements ColorStoreInfo{
	private static ColorManager instance;
	private ColorManager(){}
	public static ColorManager getSingleton(){
		if (instance==null){
			instance=new ColorManager();
		}
		return instance;
	}
	public boolean setColor(Synset synset, Color color){
		return true;
	}
	public Color getColor(Synset synset){
		float r=0;
		float g=0;
		float b=0;
		DataManager p=new DataManager();//Zero
		Colors colors=p.getColor(synset);
		int totWeight=0;
		for (int i=0;i<STORENUM;++i)
			totWeight+=colors.getA(i);
		for (int i=0;i<STORENUM;++i){
			r+=colors.getR(i)/255.0*colors.getA(i)/totWeight;
			g+=colors.getG(i)/255.0*colors.getA(i)/totWeight;
			b+=colors.getB(i)/255.0*colors.getA(i)/totWeight;
		}
		return new Color(r,g,b,0);
	}
}
