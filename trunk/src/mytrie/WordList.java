package mytrie;
import java.util.*;

/**
 * WordList类，单词列表类
 */
public class WordList {
	ArrayList<String> strList=new ArrayList<String>();
	int maxsize;
	/**
	 * 无参构造函数
	 */
	public WordList(){
		strList.clear();
		maxsize=0;
	}
	/**
	 * 将Word添加如List
	 */
	public void putWord(String word){	
		strList.add(word);
	}
	/**
	 * 得到第index个单词，从0编号
	 */
	public String getWord(int index){
	
		return strList.get(index);
	}
	/**
	 * 得到List中所含单词数
	 */		
	public int getSize(){
		return strList.size();
	}
	/**
	 * 得到表中所能含最大单词数
	 */
	public int getMaxSize(){
	
		return maxsize;
	}
	/**
	 * 设置列表中所能含最大单词数
	 */
	public void setMaxSize(int size){
		maxsize=size;
	}
}
