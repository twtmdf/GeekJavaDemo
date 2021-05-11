package geek.week6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 参考链接：前面Lambda、函数式编程讲的很好，后面一般：https://segmentfault.com/a/1190000022887572
 * stream流实现原理介绍：https://www.cnblogs.com/xiaoxiongcanguan/p/10511233.html
 */
public class ScrollQueryFunctionTest {
    private List<Long> fromDB = new ArrayList<>();

    public PageDTO<Long> getNumberFromDB(QueryCondition condition, String scrollId, Long validTime) {
        if (scrollId == null) {
             scrollId = "0";
        }
        int nextScrollId = Integer.valueOf(scrollId) +1;
        int start = Integer.valueOf(scrollId)*condition.getSize();
        int end = Integer.valueOf(nextScrollId)*condition.getSize();
        PageDTO<Long> pageDTO = new PageDTO<>();
        if (start > fromDB.size()) {
            return pageDTO;
        }
        if (end>fromDB.size()) {
            end = fromDB.size();
        }
        List<Long> contents = fromDB.subList(start,end);
        pageDTO.setContents(contents);
        pageDTO.setScrollId(String.valueOf(nextScrollId));
        return pageDTO;
    }

    public List<Long> getFromDB() {
        return fromDB;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ScrollQueryFunctionTest test= new ScrollQueryFunctionTest();
        for (long i = 1; i <=36 ; i++) {
            test.getFromDB().add(i);
        }
        System.out.println("fromDB="+test.getFromDB());

        QueryCondition condition = new QueryCondition();
        condition.setSize(5);
        ScrollQueryFunction<ScrollQueryFunctionTest,QueryCondition> function = new ScrollQueryFunction(test,test.getClass().getMethod("getNumberFromDB",QueryCondition.class,String.class,Long.class),condition,10L);
        try {
            function.search(test:: bussinessMethod);
        } catch (Exception e) {
            System.out.println(e);
        }

//        stream
        Stream<PageDTO<Long>> subprogramContentPageDTOStream = ScrollQueryStream.scrollQueryAsStream(pageDto -> test.getNumberFromDB(condition,  pageDto.getScrollId(), 10L));
        subprogramContentPageDTOStream.forEach(pageDto -> {
            System.out.println("处理数据业务逻辑="+pageDto.toString());
        });

    }

    public Boolean bussinessMethod(PageDTO pageDTO) {
        System.out.println("业务执行="+pageDTO.toString());
        return Boolean.TRUE;
    }
}

class QueryCondition {
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}