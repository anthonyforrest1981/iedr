package pl.nask.crs.commons.dictionary;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.List;

/**
 *
 * @author Artur Gniadzik
 * @param <KEY>
 *            key of the dictionary entry
 * @param <ENTRY>
 *            dictionary entry
 */
public interface Dictionary<KEY, ENTRY> {
    /**
     * Returns dictionary entry with given key
     *
     * @param key
     * @return entry with key given as an argument, or null if not found
     * @throws IllegalArgumentException
     *             if 'key' argument is null
     */
    /*>>>@Pure*/
    /*>>>@Nullable*/ ENTRY getEntry(KEY key);

    /**
     *
     * @return list of all entries from the dictionary. If no entry exist, empty
     *         list is returned.
     */
    List<ENTRY> getEntries();
}
