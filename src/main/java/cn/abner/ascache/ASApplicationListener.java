package cn.abner.ascache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * plugins entrypoint
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2024/6/12 20:14
 */
@Component
public class ASApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    List<ASPlugin> plugins;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent are) {
            for (ASPlugin plugin : plugins) {
                plugin.init();
                plugin.startup();
            }
        } else if (event instanceof ContextClosedEvent cce){
            for (ASPlugin plugin : plugins) {
                plugin.shutdown();
            }
        }
    }
}
