package pl.nask.crs.commons.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

public interface Converter<SRC, DST> {

    public /*>>>@Nullable*/ DST to(/*>>>@Nullable*/ SRC src);

    public /*>>>@Nullable*/ SRC from(/*>>>@Nullable*/ DST dst);

    public List<DST> to(List<SRC> srcs);

    public List<SRC> from(List<DST> srcs);
}
