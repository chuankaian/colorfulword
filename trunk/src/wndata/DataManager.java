package wndata;
import java.io.RandomAccessFile;

import coloring.*;
/**
 * 这个类应该给全局的底层读写提供支持，最好能有机制保证整个程序中只有一个这个类的实例,这样只要读取一次索引文件,而且我们之前讨论的缓存机制能较好地发挥作用.
 * 实现上我是这样考虑的.现在先不需要用复杂的数据结构去试图提高效率.可以在一开始就将所有的index文件中的条目读到内存中,用一个Map或者什么保存起来.查询的时候,直接在这个Map中取出相应的Index条目返回就可以了
 * 如果是要查Synset，你先看给出的Synset是不是在缓存里,是的话直接返回,不是的话可以用WordNetFileReader读出来.
**/

                                                             
//public class DataManager {
public class DataManager implements ColorStoreInfo                                                        //  颜色,调错
{
                                                            
   static                                                                  //初始化块，将index分类读入内存，以map形式存储
   {
	 RandomAccessFile raf;
	 Synset[] n_cache = new Synset[10000];                    //缓存
	 Synset[] v_cache = new Synset[10000];                    //缓存
	 Synset[] a_cache = new Synset[10000];                    //缓存
	 Synset[] r_cache = new Synset[10000];                    //缓存
	 
	 File n_index_file = new File("../dict/index.noun");                //四个索引的相对路径
	 File v_index_file = new File("../dict/index.verb");
	 File a_index_file = new File("../dict/index.adj");
	 File r_index_file = new File("../dict/index.adv");
	 
	 HashMap n_index = new HashMap();                                     //四种单词建立 单词到对应index行的map
	 HashMap v_index = new HashMap();
	 HashMap a_index = new HashMap();
	 HashMap r_index = new HashMap();
	 
	 File temp = new File("../dict/preindex.adj");
	 RandomAccessFile raf_1 = new RandomAccessFile(temp,"r");                    //单独取出前面的说明编成文件
	 long pre ;                                        	                 //说明的长度换行符
	 String wor,inde;                                                    //wor是单词，inde是该行索引，他们的对放入map                                                                  	
	 String[] strs;                                                      //对每行index切词的数组
	 long totallen;
	 
	 File[] index_file ={n_index_file,v_index_file,a_index_file,r_index_file};
	 HashMap[] index_map = {n_index,v_index,a_index,r_index};
	 
	 for(int i =0 ;i<4;i++)
	 {
	     pre = raf_1.length();                                           //定位????，有待验证
		 raf = new RandomAccessFile(index_file[i], "r");                 //名词
	     totallen = raf.length();                                        //全文长度
	     do
	     {
		    raf.seek(pre);                          //直接定位到正文
		    inde = raf.readLine();                  //读取一行
		    strs = inde.split(" ");                 //切词
		    wor = strs[0];                          //取单词
		    index_map[i].put(wor,inde);                  //放入map保存
		    pre += inde.length()+2;                   //移到下一行的位置,2是换行符
	     }while(pre <= totallen);
	 }
	/* 
	 for(int i = 0;i < 4 ;i++)
	 {
		 while((inde = raf.readLine()).length()!=0)
		 {
			 strs = inde.split(" ");
			 wor = strs[0];
			 if(wor.length()==0)
				 continue;
			 else
			 {
				 index_map[i].put(wor,inde);
			 }
			 
		 }
	 }*/  //装进map的第二个版本
  
   }	 
      
	
   public IndexEntry getIndex(String word,PartOfSpeech pos)   //根据单词 和词性 ，在map中查询 ，解析并返回有关索引的indexentry类
  {
	 String index_line ;
	 String[] strs;
		 
	 switch(pos)
	 {                                   //lemma pos  synset_cnt  p_cnt  [ptr_symbol...]  sense_cnt  tagsense_cnt   synset_offset  [synset_offset...] 
	  case NOUN :                        //abasement n 2 3 @ ~ + 2 1 14248927 00269679  
		 index_line = n_index.get(word); //  0       1 2 3 4 5 6 7 8     9     10                 protected String lemma;
		 break;
		 
	  case VERB:
		  index_line = v_index.get(word);
		  break;
	
	  case ADJ:
		  index_line = a_index.get(word);
		  break;
		  
	  case ADV:
		  index_line = r_index.get(word);
		  break;
		 
	  case ADJS:
		  index_line = a_index.get(word);
		  break;
		 
	 }
	     strs = index_line.split(" ");          //切词                                                                    // protected PartOfSpeech pos;
		 //entry.lemma = word;                                                                              // protected int synset_cnt;
		 //entry.pos = pos;                                                                               //protected int p_cnt;
		 //entry.synset_cnt = strs[2];                                                                      //         protected String[] ptr_symbols;
		 //entry.p_cnt = strs[3];                                                                             //  protected int sense_cnt;
		 String ptr_sym[] = new String[Integer.parseInt(strs[3])];                                            		 //protected int tagsense_cnt;
		 for(int i = 0 ;i<Integer.parseInt(strs[3]);i++)                                              			                //protected int[] synset_offsets;
		      ptr_sym[i] = strs[4+i];
		 //entry.tagsense_cnt = strs[5+strs[3]];
		 
		 int[] syn_offsets = new int[Integer.parseInt(strs[2])];
		 for(int i = 0 ;i < Integer.parseInt(strs[2]) ;i++)
			 syn_offsets[i] =Integer.parseInt(strs[6+Integer.parseInt(strs[3])+i]);
		 		 
		 IndexEntry entry = new IndexEntry(word,pos,strs[2],strs[3],ptr_sym,strs[5+strs[3]],syn_offsets);	 
	     //IndexEntry(String lemma,PartOfSpeech pos,int synset_cnt,int p_cnt,String[] ptr_symbols,int tagsense_cnt,int[] synset_offsets)
	 return entry;
  }
   
   
   
   public Synset getSynset(int offset,PartOfSpeech pos)       //读取synset，  先进入缓存，有则直接返回，否则进入data，写缓存，赋值返回
   {
	   Synset   syn = new Synset();                           //返回变量
	   String[] strs;
	   String  data;                                          //辅助拆词
	   int add = offset%10000;                                   //转换cache地址
	   int local,lenofgloss;
	   boolean judge_cache =false;
	   switch(pos)                                                         //verb有frames
	   {
	     case NOUN: 
		   if(n_cache[add].offset == offset && n_cache[add].pos.equals(pos))                            
		   {
			   return n_cache[add];
		   }
		   break;
		   
	     case VERB:
	    	 if(v_cache[add].offset == offset && v_cache[add].pos.equals(pos))
	    	 {
	    		 return v_cache[add];
	    	 }
	     break;	 
		   
	     case ADV:
	    	 if(r_cache[add].offset == offset && r_cache[add].pos.equals(pos))
	    	 {
	    		 return r_cache[add];
	    	 }
	     break;	
	     
	     case ADJ:
	    	 if(a_cache[add].offset == offset && a_cache[add].pos.equals(pos))
	    	 {
	    		 return a_cache[add];
	    	 }
	     break;	
	     
	     case ADJS:
	    	 if(a_cache[add].offset == offset && a_cache[add].pos.equals(pos))
	    	 {
	    		 return a_cache[add];
	    	 }
	     break;	
	  }  
	     
	     switch(pos)
	     {
	        case NOUN:
	        	RandomAccessFile raf = new RandomAccessFile("../dict/data.noun");
	        break;
	        
	        case VERB:
	        	RandomAccessFile raf = new RandomAccessFile("../dict/data.verb");
	        break;
	        
	        case ADV:
	        	RandomAccessFile raf = new RandomAccessFile("../dict/data.adv");
	        break;
	        
	        case ADJ:
	        	RandomAccessFile raf = new RandomAccessFile("../dict/data.adj");
	        break;
	        
	        case ADJS:
	        	RandomAccessFile raf = new RandomAccessFile("../dict/data.adj");
	        break;
	     }
			
	     
		  raf.seek(offset);                                                                      //定位
		  data = raf.readLine();                                                                 //读行
		  strs = data.split(" ");                                                                //拆词
          // synset_offset  lex_filenum  ss_type  w_cnt  word  lex_id  [word  lex_id...]  p_cnt  [ptr...]  [frames...]  |   gloss 
		//    00072592        04          n        02        omission 1 skip 0             005   @ 00068933 n 0000   + 02588754 v 0202   + 00607944 v 0105 + 00607346 v 0103 ~ 00064448 n 0000 | a mistake resulting from neglect  			  
		  int offset = strs[0];                                // protected int offset;
		  int lex_filenum = strs[1];                           // protected int lex_filenum;
		  PartOfSpeech ss_type = new PartOfSpeech().forString(strs[2]);                            // protected PartOfSpeech ss_type;
		  int w_cnt = Integer.parseInt(strs[3], 16);       //16进制         // protected int w_cnt;
			
		  WordSense[] wordsen = new WordSense[w_cnt];
		                                       		  // // protected int lex_id;数组
		  for(int i =0;i<w_cnt;i++)                                                        
		  {
			  if(str[2]=="a"||str[2]=="s")  //adj 和adjs要去掉marker
			  {
				String worsen_tmp = strs[4+2*i];
				int loc = worsen_tmp.lastIndexOf("(");
				tmp = tmp.substring(0,loc);
				wordsen[i] = new WordSense(tmp,Integer.parseInt(strs[5+2*i]));
			  }
			  else
			  {
				  wordsen[i] = new WordSense(strs[4+2*i],Integer.parseInt(strs[5+2*i]));
			  }
		  }
		  
		  local = 4+2*syn.w_cnt;
		  int p_cnt = Integer.parseInt(strs[local]);                                  // protected int p_cnt;
			  
			  SynsetPointer[] ptrs = new SynsetPointer[p_cnt];                                // protected SynsetPointer[] ptrs;        
			   for(int i = 0;i<syn.p_cnt ;i++)
			   {
				   //PointerSymbol pointer_symbol,int synset_offset,PartOfSpeech pos,int source_target
				   ptrs[i] = new SynsetPointer(new PointerSymbol().forString(strs[local+1+i*4]),Integer.parseInt(strs[local+2+i*4]),new PartOfSpeech().forString(strs[local+3+i*4]),Integer.parseInt(strs[local+4+i*4]));
				   //syn.ptrs[i].PointerSymbol = strs[local+1+i*4];                   //
 				   //syn.ptrs[i].synset_offset = Integer.parseInt(strs[local+2+i*4]);  //构造函数
				   //syn.ptrs[i].pos = strs[local+3+i*4];   // forstring
				   //syn.ptrs[i].source_target = strs[local+4+i*4];    //两组十六进制拼出来的????  SynsetPointer里面用int记录这两组数
			   }
			   
			   if(pos.equals(VERB))                                         //verb有frames 
			   {
				   local += 4*syn.p_cnt+1;    //定位到numofframes
				   int lenofframes = Integer.parseInt(strs[local]);
				   SynsetFrame[] frames =new SynsetFrame[lenofframes];
				   for(int i =0 ;i< lenofframes ;i++)
				   {
					   frames[i] = new SynsetFrame(Integer.parseInt(strs[local+2+2*i]),Integer.parseInt(strs[local+3+2*i]));
					   //syn.frames[i].f_num = Integer.parseInt(strs[local+2+2*i]);
				       //syn.frames[i].w_num = Integer.parseInt(strs[local+3+2*i]);
				   }
				   		   
				   
				   lenofgloss = strs.size()-local-2-2*lenofframes;     // protected String[] glosses;
				   String[] glosses = new String[lenofgloss];
				   for(int i =0; i<lenofgloss ;i++)
				   {
					   glosses[i] = strs[local+syn.p_cnt*4+1+i];
				   }
				   				  
				   
				   
				   Synset syn =new Synset(offset,lex_filenum,ss_type,w_cnt,worsen,p_cnt,ptrs,frames,glosses); 
			   }
			   else
			   {                                                // protected SynsetFrame[] frames;
			      lenofgloss = strs.size()-local-syn.p_cnt*4-1;     // protected String[] glosses;
			      String[] glosses = new String[lenofgloss];
			      for(int i =0; i<lenofgloss ;i++)
			      {
				     glosses[i] = strs[local+syn.p_cnt*4+1+i];
			      }
			   		                          
			      Synset syn =new Synset(offset,lex_filenum,ss_type,w_cnt,worsen,p_cnt,ptrs,glosses);
	//public Synset(int offset,int lex_filenum,PartOfSpeech ss_type,int w_cnt,WordSense[] words,int p_cnt,SynsetPointer[] ptrs,SynsetFrame[] frames,String[] glosses)		   
			   }
			   switch(pos) //5个
			   {
			     case NOUN:
				    n_cache[add] = new Synset(syn);                 //放入缓存
			     break;
			     
			     case VERB:
					    v_cache[add] = new Synset(syn);                 //放入缓存
				 break;
			     
			     case ADJ:
					    a_cache[add] = new Synset(syn);                 //放入缓存
				 break;
				 
			     case ADV:
					    r_cache[add] = new Synset(syn);                 //放入缓存
				 break;
				 
			     case ADJS:
					    a_cache[add] = new Synset(syn);                 //放入缓存
				 break;
			   }
        return syn;
   }
             
        /*       
			     
		 case VERB:  //多了 frame  
			 if(v_cache[add]!=null)                            //
			   {
				   return v_cache[add];
			   }
			 else
			 {
				   RandomAccessFile raf_n = new RandomAccessFile("../dict/data.verb");    //
				   raf.seek(offset);                                                                      //定位
				   data = raf.readLine();                                                                 //读行
				   strs = data.split(" ");                                                                //拆词
	         	//synset_offset  lex_filenum  ss_type  w_cnt  word  lex_id  [word  lex_id...]  p_cnt  [ptr...]  [frames...]  |   gloss 
			   //00003826 29                   v        02        hiccup 0 hiccough 0          003    @ 00001740 v 0000 + 14168180 n 0202 + 14168180 n 0101    01 + 02 00 | breathe spasmodically,                             
				   
				   syn.offset = strs[0];                                // protected int offset;
				   syn.lex_filenum = strs[1];                           // protected int lex_filenum;
				   syn.ss_type = strs[2];                            // protected PartOfSpeech ss_type;
				   syn.w_cnt = Integer.parseInt(strs[3], 16);       //16进制         // protected int w_cnt;
				                                                                                  
				                                                                  // // protected int lex_id;数组
				   local = 4+2*syn.w_cnt;
				   syn.p_cnt = Integer.parseInt(strs[local]);               // protected int p_cnt;
				  
				   syn.ptrs = new SynsetPointer[syn.p_cnt];        // protected SynsetPointer[] ptrs;        
				   for(int i = 0;i<syn.p_cnt ;i++)
				   {
					   syn.ptrs[i].PointerSymbol = strs[local+1+i*4];                   //
					   syn.ptrs[i].synset_offset = Integer.parseInt(strs[local+2+i*4]);
					   syn.ptrs[i].pos = strs[local+3+i*4];
					   syn.ptrs[i].source_target = strs[local+4+i*4];
				   }
				   
				                                       // protected SynsetFrame[] frames;
				   local += 4*syn.p_cnt+1;    //定位到numofframes
				   int lenofframes = Integer.parseInt(strs[local]);
				   syn.frames =new SynsetFrame[lenofframes];
				   for(int i =0 ;i< lenofframes ;i++)
				   {
					   syn.frames[i].f_num = Integer.parseInt(strs[local+2+2*i]);
				       syn.frames[i].w_num = Integer.parseInt(strs[local+3+2*i]);
				   }
				   		   
				   
				   lenofgloss = strs.size()-local-2-2*lenofframes;     // protected String[] glosses;
				   syn.glosses = new String[lenofgloss];
				   for(int i =0; i<lenofgloss ;i++)
				   {
					   syn.glosses[i] = strs[local+syn.p_cnt*4+1+i];
				   }
                 
			   v_cache[add] = syn;                 //放入缓存	       
	   		   return syn;
			 }
	      break;				   
	   }//switch
   */
    
   
   
   
   public Synset[] lookup(String word, PartOfSpeech pos)      //返回单词对应词性的所有 synset
   {
	   
	   IndexEntry tmp = getIndex(word,pos);     //???缺构造函数 
	   int[] offsets = tmp.getSynsetOffets();
	   Synset[] syn = new Synset[tmp.getSynsetCount()];
	   for(int i =0 ;i < tmp.synset_cnt;i++)
	   {
		   syn[i] = getSynset(offsets[i],pos);
	   }
	   return syn;   
   }
   /**
    * 以下是新加入的Color相关的读写操作，分别是Color的写入和读出操作，Color类的声明详见Color。java
    * By Zero，
    */
   public boolean setColor(Synset synset, Colors colors){
   }
   public Colors getColor(Synset synset){
   }
}

