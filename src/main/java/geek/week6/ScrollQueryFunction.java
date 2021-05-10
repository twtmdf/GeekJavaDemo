package geek.week6;

import lombok.*;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

/**
 * 为基于es的游标查询提供通用查询工具，方便业务方调用
 * @param <T>
 * @param <E>
 */
public class ScrollQueryFunction<T,E> {
    /**
     * 游标查询有效期
     */
    private Long validTime;
    /**
     * bean实例
     */
    private T serviceBean;
    /**
     * 游标查询条件
     */
    private E queryCondition;
    /**
     * 方法
     */
    private Method method;

    public ScrollQueryFunction(T serviceBean, Method method,E queryCondition, Long validTime) {
        this.queryCondition = queryCondition;
        this.serviceBean = serviceBean;
        this.method = method;
        this.validTime = validTime;
    }

    /**
     *  实现入参为PageDTO,返回值为Boolean
     *  返回值为false时则不继续查询,否则循环查询直到查询完成
     * @param function
     */
    public void search(Function<PageDTO,Boolean> function) throws InvocationTargetException, IllegalAccessException {
        Boolean r = Boolean.TRUE;
        PageDTO  pageDTO = null;
        String scrollId = null;
        do {
            pageDTO = (PageDTO) method.invoke(serviceBean,queryCondition, scrollId,validTime);
            if (pageDTO!=null) {
                r = function.apply(pageDTO);
                scrollId = pageDTO.getScrollId();
            }
        } while (r != null && !r.equals(Boolean.FALSE) && pageDTO != null && pageDTO.getScrollId() != null && pageDTO.getContents() != null && pageDTO.getContents().size() >0);
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class PageDTO<T> implements Serializable {
    /**
     * 集合
     */
    private List<T> contents;
    /**
     * 游标ID，仅游标查询时需要
     */
    private String scrollId;

}

