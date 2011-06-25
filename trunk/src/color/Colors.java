package coloring;
/*
 * Colors类来存放多个Color。
 */
public class Colors implements ColorStoreInfo{
	private byte[] r;
	private byte[] g;
	private byte[] b;
	private byte[] a;
	/*
	 * 产生一个全空的Colors
	 */
	public Colors(){
		r=new byte[STORENUM];
		g=new byte[STORENUM];
		b=new byte[STORENUM];
		a=new byte[STORENUM];
	}
	/*
	 * 利用之前序列化的data，还原Colors类
	 */
	public Colors(char[] data){
		this();
		for (int i=0;i<STORENUM;++i){
			r[i]=(byte)(data[i*2]&255);
			g[i]=(byte)(data[i*2]>>8);
			b[i]=(byte)(data[i*2+1]&255);
			a[i]=(byte)(data[i*2+1]>>8);
		}
	}
	/*
	 * 将此类序列化
	 */
	public char[] serialize(){
		char[] res=new char[STORENUM*2];
		for (int i=0;i<STORENUM;++i){
			res[i*2]=(char)(g[i]<<8|r[i]);
			res[i*2+1]=(char)(a[i]<<8|b[i]);
		}
		return res;
	}
	public void setR(int index,byte r){
		this.r[index]=r;
	}
	public void setG(int index,byte g){
		this.g[index]=g;
	}
	public void setB(int index,byte b){
		this.b[index]=b;
	}
	public void setA(int index,byte a){
		this.a[index]=a;
	}
	public byte getR(int index){
		return r[index];
	}
	public byte getG(int index){
		return g[index];
	}
	public byte getB(int index){
		return b[index];
	}
	public byte getA(int index){
		return a[index];
	}
}