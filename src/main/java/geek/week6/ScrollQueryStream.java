package geek.week6;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ScrollQueryStream {

    static final PageDTO NONE = new PageDTO();

    public static <T extends PageDTO> Stream<T> scrollQueryAsStream(final UnaryOperator<T> f) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new Iterator<T>() {
                            @SuppressWarnings("unchecked")
                            T t = (T) NONE;

                            @Override
                            public T next() {
                                return t = f.apply(t);
                            }

                            @Override
                            public boolean hasNext() {
                                return t == NONE || (t != null && t.getScrollId() != null && t.getContents() != null && t.getContents().size()>0);
                            }
                        },
                        Spliterator.ORDERED), false).filter(t -> t != null && t.getScrollId() != null && t.getContents() != null && t.getContents().size()>0);
    }

}
