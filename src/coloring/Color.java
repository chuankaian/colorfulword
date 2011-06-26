package coloring;

public class Color {
	private char r;
	private char g;
	private char b;
	private char a;
	Color(float r, float g, float b, float a){
		this.r=(char)(r*255+0.5);
		this.g=(char)(g*255+0.5);
		this.b=(char)(b*255+0.5);
		this.a=(char)(a*255+0.5);
	}
	Color(int r,int g,int b,int a){
		this.r=(char)r;
		this.g=(char)g;
		this.b=(char)b;
		this.a=(char)a;
	}
	public void setR(char r){
		this.r=r;
	}
	public void setG(char g){
		this.g=g;
	}
	public void setB(char b){
		this.b=b;
	}
	public void setA(char a){
		this.a=a;
	}
	public char getR(){
		return r;
	}
	public char getG(){
		return g;
	}
	public char getB(){
		return b;
	}
	public char getA(){
		return a;
	}
}
