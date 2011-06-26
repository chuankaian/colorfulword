package mytrie;
/**
 * Tire类，单词构成一个Trie树。
 * @author Lostmonkey
 * version 1.0
 */
public class Trie {

	TrieNode root=new TrieNode();
	private void search(int level,TrieNode now,String st,WordList mList,boolean checkall){
		//level当前搜多到树的层数，now当前树节点，st搜索的字符串，mList已搜到的单词表，checkall是否曾经出现过*，出现过则要check子树中所有单词。
		if (now==null) return;
		if (now.match(st)) mList.putWord(now.getWord());
		if (mList.getSize()>=mList.getMaxSize()) return;
		Character mCh='*';
		if (!checkall) mCh=st.charAt(level);
		if (mCh=='*') checkall=true;
		if (mCh=='?'||checkall)
			for (ChildLink tmp=now.getHead();tmp!=null;tmp=tmp.getNext())
				search(level+1,tmp.getChild(),st,mList,checkall);
		else if (mCh!='*'&&now.getChild(mCh)!=null)
			search(level+1,now.getChild(mCh),st,mList,checkall);
	}
	/**
	 * 往Trie树中插入一个单词。
	 */
	public void insert(String word){

		TrieNode now=root,next;
		for (int i=0;i<word.length();++i){
			next=now.getChild(word.charAt(i));
			if (next==null) next=now.addChild(word.charAt(i));
			now=next;
		}
		now.setWord(word);
	}
	/**
	 * 返回一个WordList对象，表示被pattern匹配的单词。
	 */
	public WordList getWordList(String pattern,int maxsize){
		WordList mList=new WordList();
		mList.setMaxSize(maxsize);
		if (pattern.charAt(pattern.length()-1)!='*') pattern+='*';
		search(0,root,pattern,mList,false);
		return mList;
	}
	
}

class ChildLink{
	/*
	 * 儿子的链表类
	 * 这个儿子是父亲mChar这个字符对应的儿子
	 * 这个儿子的TrieNode对象为mChild
	 *
	 */
	Character mchar;
	TrieNode mchild;
	ChildLink next;
	ChildLink(Character ch,TrieNode node){
		//构造函数
		mchar=ch;
		mchild=node;			
	}
	Character getChar(){
		return mchar;
	}
	TrieNode getChild(){
		return mchild;
	}
	void setNext(ChildLink node){
		next=node;
	}
	ChildLink getNext(){
		return next;
	}
};

class TrieNode{
	/*
	 * TireNode类，表示Trie树的一个节点。
	 * 若此节点对应一个单词，则mWord为此单词否则为null。
	 * 儿子用ChildLink的链表结构表示。
	 *
	 */
	String mWord;
	ChildLink head;
	TrieNode(){
		head=null;
		mWord=null;
	}
	private ChildLink getByChar(Character ch){
		//得到儿子链表中ch这个字符对应的节点
		ChildLink pre=null;
		for (ChildLink tmp=head;tmp!=null&&tmp.getChar()<=ch;tmp=tmp.getNext()) pre=tmp;
		return pre;
	}
	TrieNode getChild(Character ch){
		//得到ch字符对应的儿子
		ChildLink pre=getByChar(ch);
		if (pre==null||pre.getChar()<ch) return null;
		else return pre.getChild();
	}
	TrieNode addChild(Character ch){
		//添加一个ch字符对应的节点
		ChildLink pre=getByChar(ch);
		ChildLink tmp=new ChildLink(ch,new TrieNode());
		if (pre==null){
			tmp.setNext(head);
			head=tmp;
		} else {
			tmp.setNext(pre.getNext());
			pre.setNext(tmp);
		} 
		return tmp.getChild();
	}
	void setWord(String word){
		mWord=word;
	}
	String getWord(){
		return mWord;
	}
	ChildLink getHead(){
		return head;
	}
	
	boolean match(String pattern){
		//判断我的单词是否和pattern匹配。
		if (mWord==null) return false;
		//应用动态规划算法。
		boolean[][] f=new boolean[mWord.length()+1][pattern.length()+1];
		f[0][0]=true;
		if (pattern.charAt(0)=='*')
			for (int i=0;i<=mWord.length();++i) f[i][1]=true;
		for (int i=1;i<=mWord.length();++i)
			for (int j=1;j<=pattern.length();++j){
				char ch1=mWord.charAt(i-1);
				char ch2=pattern.charAt(j-1);
				if (ch2=='?'||ch1==ch2)
					f[i][j]=f[i][j]|f[i-1][j-1];
				if (ch2=='*') 
					f[i][j]=f[i][j]|f[i-1][j]|f[i][j-1];
			}
		return f[mWord.length()][pattern.length()];
	}
	
}