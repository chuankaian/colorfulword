package wndata;

/**
 * 这个类应该给全局的底层读写提供支持，最好能有机制保证整个程序中只有一个这个类的实例,这样只要读取一次索引文件,而且我们之前讨论的缓存机制能较好地发挥作用.
 * 实现上我是这样考虑的.现在先不需要用复杂的数据结构去试图提高效率.可以在一开始就将所有的index文件中的条目读到内存中,用一个Map或者什么保存起来.查询的时候,直接在这个Map中取出相应的Index条目返回就可以了
 * 如果是要查Synset，你先看给出的Synset是不是在缓存里,是的话直接返回,不是的话可以用WordNetFileReader读出来.
 **/

public class DataManager {

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
}

