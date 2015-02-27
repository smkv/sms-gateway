package ee.smkv.sms.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SmsSenderFactory {
    @Autowired
    Environment environment;
    
    @Autowired
    ApplicationContext applicationContext;
    
    public SmsSender getSmsSender(){
        return applicationContext.getBean(environment.getProperty("sms.sender","gammu"), SmsSender.class);
    }
}
