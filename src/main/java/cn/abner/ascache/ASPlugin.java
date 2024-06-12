package cn.abner.ascache;

/**
 * as cache plugin
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2024/6/12 20:13
 */
public interface ASPlugin {
    void init();

    void startup();

    void shutdown();
}
