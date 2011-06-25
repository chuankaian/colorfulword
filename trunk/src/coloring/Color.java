package coloring;

public class Color {
	private byte r;
	private byte g;
	private byte b;
	private byte a;
	Color(float r, float g, float b, float a){
		this.r=(byte)(r*255+0.5);
		this.g=(byte)(g*255+0.5);
		this.b=(byte)(b*255+0.5);
		this.a=(byte)(a*255+0.5);
	}
	public void setR(byte r){
		this.r=r;
	}
	public void setG(byte g){
		this.g=g;
	}
	public void setB(byte b){
		this.b=b;
	}
	public void setA(byte a){
		this.a=a;
	}
	public byte getR(){
		return r;
	}
	public byte getG(){
		return g;
	}
	public byte getB(){
		return b;
	}
	public byte getA(){
		return a;
	}
}
