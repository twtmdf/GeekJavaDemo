package geek.week4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentPackageTest {

    public static void main(String[] args) {
//线性数据结构

//        List
//        基于数组，便于按 index 访问，超过数组需要扩容，扩容成本较高
//        使用数组模拟列表，默认大小10，扩容 x1.5，newCapacity = oldCapacity + (oldCapacity >> 1)
//        用途:大部分情况下操作一组数据都可以用 ArrayList
        List<Integer> arrayLists = new ArrayList<>();
        arrayLists.add(1);
//      使用链表实现，无需扩容
//        使用双向指针将所有节点连起来
//        用途:不知道容量，插入变动多的情况
        List<Integer> linkedLists = new LinkedList<>();
        linkedLists.add(2);

//        Vector
//        ArrayList 的方法都加上 synchronized -> Vector
        Vector<Integer> vector = new Vector<>();
        vector.add(3);

//        Stack
//        Stack extends Vector
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.pop();
//        Collections.synchronizedList，强制将 List 的操作加上同步
//        synchronized (mutex)
        List<Integer> syncLists = Collections.synchronizedList(arrayLists);
//        Arrays.asList，不允许添加删除，但是可以 set 替换元素
        List<List<Integer>> asLists = Arrays.asList(arrayLists);
//        java.lang.UnsupportedOperationException
//        asLists.add(new ArrayList<>());
//        Collections.unmodifiableList，不允许修改内容，包括添加删除和 set
        List<Integer> unmodifiableList = Collections.unmodifiableList(arrayLists);
//        java.lang.UnsupportedOperationException
//        unmodifiableList.add(4);

//      只解决了写写冲突，没有解决读写冲突

//        Queue
//      Queue->Deque->LinkedList
        LinkedList linkedList = new LinkedList();
        linkedList.add(4);

//      Map
//        Map:HashMap、LinkedHashMap、TreeMap
//        Dictionary->HashTable->Properties
//        Constructs an empty HashMap with the default initial capacity (16) and the default load factor (0.75)
//        hashmap->空间换时间
//        负载因子0.75，槽数量/数据量=0.75开始扩容，默认扩容一倍
//        hash值冲突，采用链表法
//        jdk8以后，在链表长度到8 & 数组长度到64时，使用红黑树
        HashMap<String,Integer> hashMap = new HashMap();
        hashMap.put("k",1);
//        extends HashMap
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("k",2);
//        实现LRU
        LinkedHashMap linkedHashMap1 = new LinkedHashMap(16,0.75f,Boolean.TRUE);
        linkedHashMap1.put("k1",1);
        linkedHashMap1.put("k2",2);
        linkedHashMap1.put("k3",3);
        System.out.println("accessOrder true 写入顺序 ="+linkedHashMap1);
        linkedHashMap1.get("k3");
        linkedHashMap1.get("k2");
        linkedHashMap1.get("k1");
        System.out.println("accessOrder true 读取顺序 ="+linkedHashMap1);
//        红黑树，用于实现一致性hash
        TreeMap<String,Integer> treeMap = new TreeMap();
        treeMap.put("k",3);
//     ConcurrentHashMap, initial capacity is 16
//     DEFAULT_CONCURRENCY_LEVEL = 16
//        jdk7使用segment加锁，jdk8利用红黑树以及CAS无锁技术实现
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("k",1);
        concurrentHashMap.get("k");
//        a default initial capacity (11) and load factor (0.75).
//     对方法加synchronized,全表锁
        Hashtable hashtable = new Hashtable();
        hashtable.put("k",4);
        hashtable.get("k");
//       HashMap、ConcurrentHashMap、HashTable的区别，参考链接： https://www.jianshu.com/p/c00308c32de4
//        extends Hashtable<K,V> extends Dictionary
        Properties properties = new Properties();
        properties.put("k",4);

//        Set
//        new HashMap()
//        default initial capacity (16) and load factor (0.75).
        HashSet hashSet = new HashSet();
        hashSet.add(5);
        hashSet.remove(5);
//        LinkedHashSet extends HashSet
//        he default initial capacity (16) and load factor (0.75)
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(6);
//        new TreeMap
        TreeSet treeSet = new TreeSet();
        treeSet.add(7);

//      1.写加锁，保证不会写混乱
//      2.写到副本上，读写分离
//       COWIterator
        CopyOnWriteArrayList arrayList = new CopyOnWriteArrayList();
        arrayList.add(9);
        arrayList.get(0);
    }
}
