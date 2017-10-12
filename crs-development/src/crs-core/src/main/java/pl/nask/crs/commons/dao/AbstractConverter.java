package pl.nask.crs.commons.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public abstract class AbstractConverter<SRC, DST> implements Converter<SRC, DST> {
    private final static Logger logger = Logger.getLogger(AbstractConverter.class);

    @Override
    /*>>>@Pure*/
    public /*>>>@Nullable*/ DST to(/*>>>@Nullable*/ SRC src) {
        if (src == null) {
            return null;
        }
        try {
            return _to(src);
        } catch (Exception e) {
            logger.error(this.getClass() + ": error converting entity " + src + ". Error message was: "
                    + e.getMessage());
            logger.error(this.getClass() + ": entity is: " + ReflectionToStringBuilder.toString(src));
            logger.error(this.getClass() + ": Exception was: " + e, e);
            return null;
        }
    }

    @Override
    /*>>>@Pure*/
    public /*>>>@Nullable*/ SRC from(/*>>>@Nullable*/ DST dst) {
        if (dst == null) {
            return null;
        }
        try {
            return _from(dst);
        } catch (Exception e) {
            logger.error(this.getClass() + ": error converting entity " + dst + ". Error message was: "
                    + e.getMessage());
            logger.error(this.getClass() + ": entity is: " + ReflectionToStringBuilder.toString(dst));
            logger.error(this.getClass() + ": Exception was: " + e, e);
            return null;
        }
    }

    @Override
    /*>>>@Pure*/
    public List<DST> to(List<SRC> list) {
        List<DST> ret = new ArrayList<>();
        for (SRC src : list) {
            // skip null values
            DST t = to(src);
            if (t != null)
                ret.add(t);
        }
        return ret;
    }

    @Override
    /*>>>@Pure*/
    public List<SRC> from(List<DST> list) {
        if (list == null) {
            return null;
        }
        List<SRC> ret = new ArrayList<>();
        for (DST dst : list) {
            // skip null values
            SRC f = from(dst);
            if (f != null)
                ret.add(f);
        }
        return ret;
    }

    protected abstract DST _to(SRC src);

    protected abstract SRC _from(DST dst);

}
