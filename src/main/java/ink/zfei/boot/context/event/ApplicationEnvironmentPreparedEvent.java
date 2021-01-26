package ink.zfei.boot.context.event;

import ink.zfei.boot.SpringApplication;
import ink.zfei.summer.core.ApplicationEvent;
import ink.zfei.summer.core.env.ConfigurableEnvironment;

public class ApplicationEnvironmentPreparedEvent extends ApplicationEvent {

    private final ConfigurableEnvironment environment;
    private final String[] args;

    /**
     * Create a new {@link ApplicationEnvironmentPreparedEvent} instance.
     * @param application the current application
     * @param args the arguments the application is running with
     * @param environment the environment that was just created
     */
    public ApplicationEnvironmentPreparedEvent(SpringApplication application, String[] args,
                                               ConfigurableEnvironment environment) {
        super(application);
        this.args = args;
        this.environment = environment;
    }

    /**
     * Return the environment.
     * @return the environment
     */
    public ConfigurableEnvironment getEnvironment() {
        return this.environment;
    }

    public SpringApplication getSpringApplication() {
        return (SpringApplication) getSource();
    }


}
