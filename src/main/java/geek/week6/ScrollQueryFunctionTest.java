package geek.week6;

import java.net.CacheRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ScrollQueryFunctionTest {
    private List<Long> fromDB = new ArrayList<>();

    public PageDTO<Long> getNumberFromDB(QueryCondition condition, String scrollId, Long validTime) {
        if (scrollId == null) {
             scrollId = "0";
        }
        int nextScrollId = Integer.valueOf(scrollId) +1;
        int start = Integer.valueOf(scrollId)*condition.size;
        int end = Integer.valueOf(nextScrollId)*condition.size;
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

    }

    public Boolean bussinessMethod(PageDTO pageDTO) {
        System.out.println("业务执行="+pageDTO.toString());
        return Boolean.TRUE;
    }
}

class QueryCondition {
    int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}