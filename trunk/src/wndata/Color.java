package wndata;
interface ColorStoreInfo{
	int STORENUM=5;
}
public class Color implements ColorStoreInfo{
	private byte[] r;
	private byte[] g;
	private byte[] b;
	private byte[] a;
	private Color(){
	}
	public Color(char[] data){
		r=new byte[STORENUM];
		g=new byte[STORENUM];
		b=new byte[STORENUM];
		a=new byte[STORENUM];
		for (int i=0;i<STORENUM;++i){
			r[i]=(byte)(data[i*2]&255);
			g[i]=(byte)(data[i*2]>>8);
			b[i]=(byte)(data[i*2+1]&255);
			a[i]=(byte)(data[i*2+1]>>8);
		}
	}
	public char[] serialize(){
		res=new char[STORENUM*2];
		for (int i=0;i<STORENUM;++i){
			res[i*2]=(char)g[i]<<8|r[i];
			res[i*2+1]=(char)a[i]<<8|b[i];
		}
	}
}