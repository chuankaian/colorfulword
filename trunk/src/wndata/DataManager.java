package wndata;

/**
 * 这个类应该给全局的底层读写提供支持，最好能有机制保证整个程序中只有一个这个类的实例,这样只要读取一次索引文件,而且我们之前讨论的缓存机制能较好地发挥作用.
 * 实现上我是这样考虑的.现在先不需要用复杂的数据结构去试图提高效率.可以在一开始就将所有的index文件中的条目读到内存中,用一个Map或者什么保存起来.查询的时候,直接在这个Map中取出相应的Index条目返回就可以了
 * 如果是要查Synset，你先看给出的Synset是不是在缓存里,是的话直接返回,不是的话可以用WordNetFileReader读出来.
**/

<<<<<<< .mine
=======
public class DataManager {
>>>>>>> .r6

<<<<<<< .mine
public DataManager                                                         //返回相应的对象  高级要求：单例类  缓存  颜色
{
                                                            
   static                                                                  //初始化块，将index分类读入内存，以map形式存储
   {
	 public RandomAccessFile raf;
	 
	 File n_index_file = new File("F:\\WordNet\\2.1\\dict\\index.noun");  //四个索引的路径 
	 File v_index_file = new File("F:\\WordNet\\2.1\\dict\\index.verb");
	 File a_index_file = new File("F:\\WordNet\\2.1\\dict\\index.adj");
	 File r_index_file = new File("F:\\WordNet\\2.1\\dict\\index.adv");
	 
	 HashMap n_index = new HashMap();                                     //四种单词建立 单词到对应index行的map
	 HashMap v_index = new HashMap();
	 HashMap a_index = new HashMap();
	 Hashmap r_index = new HashMap();
	 
	 raf = new RandomAccessFile("F:\\WordNet\\2.1\\dict\\preindex.adj")   //单独取出前面的说明编成文件 
	 long pre = raf.length();                                        	 //说明的长度
	 String wor,inde;                                                    //wor是单词，inde是该行索引，他们的对放入map                                                                  	
	 String[] strs;                                                      //对每行index切词的数组
	 
	 raf = new RandomAccessFile(n_index_file, "r");                       //名词
	 long totallen = raf.length();                                        //全文长度
	 do
	 {
		 raf.seek(pre);                          //直接定位到正文
		 inde = raf.readLine();                  //读取一行
		 strs = inde.split(" ");                 //切词
		 wor = strs[0];                          //取单词
		 n_index.put(wor,inde);                  //放入map保存
		 pre += inde.length()+2;                   //移到下一行的位置
	 }while(pre< = totallen);
	
	 
	 raf = new RandomAccessFile(v_index_file, "r");                       //动词
	 long totallen = raf.length();                                        //全文长度
	 do
	 {
		 raf.seek(pre);                          //直接定位到正文
		 inde = raf.readLine();                  //读取一行
		 strs = inde.split(" ");                 //切词
		 wor = strs[0];                          //取单词
		 v_index.put(wor,inde);                  //放入map保存
		 pre += inde.length()+2;                   //移到下一行的位置
	 }while(pre< = totallen);
	
	 
	 raf = new RandomAccessFile(a_index_file, "r");                       //形容词  
	 long totallen = raf.length();                                        //全文长度
	 do
	 {
		 raf.seek(pre);                          //直接定位到正文
		 inde = raf.readLine();                  //读取一行
		 strs = inde.split(" ");                 //切词
		 wor = strs[0];                          //取单词
		 a_index.put(wor,inde);                  //放入map保存
		 pre += inde.length()+2;                   //移到下一行的位置
	 }while(pre< = totallen);
		 
	
	 raf = new RandomAccessFile(r_index_file, "r");                       //副词
	 long totallen = raf.length();                                        //全文长度
	 do
	 {
		 raf.seek(pre);                          //直接定位到正文
		 inde = raf.readLine();                  //读取一行
		 strs = inde.split(" ");                 //切词
		 wor = strs[0];                          //取单词
		 r_index.put(wor,inde);                  //放入map保存
		 pre += inde.length()+2;                   //移到下一行的位置
	 }while(pre< = totallen);
		 
   }	 
                                                          
	
   public IndexEntry getIndex(String word,PartOfSpeech pos)   //根据单词 和词性 ，在map中查询 ，解析并返回有关索引的indexentry类
  {
	 String index_line ;
	 String[] strs;
	 IndexEntry entry = new IndexEntry();
	 
	 switch(pos)
	 {                                   //lemma pos  synset_cnt  p_cnt  [ptr_symbol...]  sense_cnt  tagsense_cnt   synset_offset  [synset_offset...] 
	  case NOUN :                        //abasement n 2 3 @ ~ + 2 1 14248927 00269679  
		 index_line = n_index.get(word); //  0       1 2 3 4 5 6 7 8     9     10                 protected String lemma;
		 strs = index_line.split(" ");          //切词                                                                    // protected PartOfSpeech pos;
		 entry.lemma = word;                                                                              // protected int synset_cnt;
		 entry.pos = pos;                                                                               //protected int p_cnt;
		 entry.synset_cnt = strs[2];                                                                      //         protected String[] ptr_symbols;
		 entry.p_cnt = strs[3];                                                                             //  protected int sense_cnt;
		 entry.ptr_symbols = new String[strs[3]];                                            		 //protected int tagsense_cnt;
		 for(int i = 0 ;i<strs[3];i++)                                              			                //protected int[] synset_offsets;
		      entry.ptr_symbols[i] = strs[4+i];
		 
		 entry.tagsense_cnt = strs[5+strs[3]];
		 
		 entry.synset_offsets = new int[strs[2]];
		 for(int i = 0 ;i < strs[2] ;i++)
			 entry.synset_offsets[i] = strs[6+strs[3]+i];
		 		 
		 break;
	  
	  case  VERB：                                                                   // 动词abuse v 4 3 @ ~ + 4 2 02492450 00200244 00836339 00200587  
		 index_line = v_index.get(word);   //      0    1 2 3 4 5 6 7 8 9           10     11          12   
	     strs = index_line.split(" ");
	     entry.lemma = word;
	     entry.pos = pos;
	     entry.synset_cnt = strs[2];
	     entry.p_cnt = strs[3];
	     entry.ptr_symbols = new String[strs[3]];
	     for(int i = 0;i<strs[3];i++)
	    	 entry.ptr_symbols[i] = strs[4+i];
	     
	     entry.tagsense_cnt = strs[5+strs[3]];
	     entry.synset_offsets = new int[strs[2]];
	     for(int i =0 ;i< strs[2];i++)
	    	 entry.synset_offsets[i] = strs [6+strs[3]+i];
		 
	     
		 break;
		 
	  case ADJ:                                                         
	  	 index_line = a_index.get(word);                              
	     strs = index_line.split(" ");
	     entry.lemma = word;
	     entry.pos = pos;
	     entry.synset_cnt = strs[2];
	     entry.p_cnt = strs[3];
	     entry.ptr_symbols = new String[strs[3]];
	     for(int i = 0;i<strs[3];i++)
	        entry.ptr_symbols[i] = strs[4+i];
	     
	     entry.tagsense_cnt = strs[5+strs[3]];
	     entry.synset_offsets = new int[strs[2]];
	     for(int i =0 ;i< strs[2];i++)
	        entry.synset_offsets[i] = strs [6+strs[3]+i];
	      
	   	 break;
		 
	  case ADV:                                                         
		  	 index_line = r_index.get(word);                              
		     strs = index_line.split(" ");
		     entry.lemma = word;
		     entry.pos = pos;
		     entry.synset_cnt = strs[2];
		     entry.p_cnt = strs[3];
		     entry.ptr_symbols = new String[strs[3]];
		     for(int i = 0;i<strs[3];i++)
		        entry.ptr_symbols[i] = strs[4+i];
		     
		     entry.tagsense_cnt = strs[5+strs[3]];
		     entry.synset_offsets = new int[strs[2]];
		     for(int i =0 ;i< strs[2];i++)
		        entry.synset_offsets[i] = strs [6+strs[3]+i];
		      
		   	 break; 
		
		   	 
	  case ADJS:                                                         
		  	 index_line = a_index.get(word);                              
		     strs = index_line.split(" ");
		     entry.lemma = word;
		     entry.pos = pos;
		     entry.synset_cnt = strs[2];
		     entry.p_cnt = strs[3];
		     entry.ptr_symbols = new String[strs[3]];
		     for(int i = 0;i<strs[3];i++)
		        entry.ptr_symbols[i] = strs[4+i];
		     
		     entry.tagsense_cnt = strs[5+strs[3]];
		     entry.synset_offsets = new int[strs[2]];
		     for(int i =0 ;i< strs[2];i++)
		        entry.synset_offsets[i] = strs [6+strs[3]+i];
		      
		   	 break; 
	     
	 }
	 return entry;
  }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   public Synset getSynset(int offset,PartOfSpeech pos);       //进入data，读取一行，返回synset
   
   public Synset[] lookup(String word, PartOfSpeech pos);      //返回单词对应词性的所有 synset
=======
    public IndexEntry getIndex(String word,PartOfSpeech pos)
    {
        return null;
    }
    public Synset getSynset(int offset,PartOfSpeech pos)
    {
        return null;
    }
    public Synset[] lookup(String word,PartOfSpeech pos)
    {
        return null;
    }
>>>>>>> .r6
}

