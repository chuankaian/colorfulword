package coloring;
/*
 * Colors类来存放多个Color。
 */
public class Colors implements ColorStoreInfo{
	private Color[] data;
	/*
	 * 产生一个全空的Colors
	 */
	public Colors(){
		data=new Color[STORENUM];
	}
	/*
	 * 利用之前序列化的data，还原Colors类
	 */
	public Colors(char[] data){
		this();
		for (int i=0;i<STORENUM;++i){
			this.data[i].setR((byte)(data[i*2]&255));
			this.data[i].setG((byte)(data[i*2]>>8));
			this.data[i].setB((byte)(data[i*2+1]&255));
			this.data[i].setA((byte)(data[i*2+1]>>8));
		}
	}
	/*
	 * 将此类序列化
	 */
	public char[] serialize(){
		char[] res=new char[STORENUM*2];
		for (int i=0;i<STORENUM;++i){
			res[i*2]=(char)(data[i].getG()<<8|data[i].getR());
			res[i*2+1]=(char)(data[i].getA()<<8|data[i].getB());
		}
		return res;
	}
	public void setR(int index,byte r){
		data[index].setR(r);
	}
	public void setG(int index,byte g){
		data[index].setG(g);
	}
	public void setB(int index,byte b){
		data[index].setB(b);
	}
	public void setA(int index,byte a){
		data[index].setA(a);
	}
	public byte getR(int index){
		return data[index].getR();
	}
	public byte getG(int index){
		return data[index].getG();
	}
	public byte getB(int index){
		return data[index].getB();
	}
	public byte getA(int index){
		return data[index].getA();
	}
}