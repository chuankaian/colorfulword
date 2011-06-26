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
			this.data[i].setR((char)(data[i*2]&255));
			this.data[i].setG((char)(data[i*2]>>8));
			this.data[i].setB((char)(data[i*2+1]&255));
			this.data[i].setA((char)(data[i*2+1]>>8));
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
	public void addColor(Color color){
		for (int i=0;i<STORENUM;++i)
			if (data[i].getA()==0){
				data[i]=color;
				return;
			}
		int j=0,k=-1;
		for (int i=1;i<STORENUM;++i)
			if (data[i].getA()>data[j].getA()){
				k=j;j=i;
			}else if (k==-1 || data[i].getA()>data[k].getA())
				k=i;
		char tot=(char) (data[j].getA()+data[k].getA());
		data[j].setR((char) (data[j].getR()*data[j].getA()/tot+data[k].getR()*data[k].getA()/tot));
		data[j].setG((char) (data[j].getG()*data[j].getA()/tot+data[k].getG()*data[k].getA()/tot));
		data[j].setB((char) (data[j].getB()*data[j].getA()/tot+data[k].getB()*data[k].getA()/tot));
		data[j].setA(tot);
		if (tot>255)
			for (int i=0;i<STORENUM;++i)
				data[i].setA((char) (data[i].getA()>>1));
		data[k]=color;
	}
	public void setR(int index,char r){
		data[index].setR(r);
	}
	public void setG(int index,char g){
		data[index].setG(g);
	}
	public void setB(int index,char b){
		data[index].setB(b);
	}
	public void setA(int index,char a){
		data[index].setA(a);
	}
	public char getR(int index){
		return data[index].getR();
	}
	public char getG(int index){
		return data[index].getG();
	}
	public char getB(int index){
		return data[index].getB();
	}
	public char getA(int index){
		return data[index].getA();
	}
}