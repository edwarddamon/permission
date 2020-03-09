import com.lhamster.domain.Systemlog;
import com.lhamster.mapper.SystemlogMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {
    @Autowired
    private SystemlogMapper systemlogMapper;

    @org.junit.Test
    public void test(){
        Systemlog systemlog = new Systemlog();
        systemlog.setIp("111");
        systemlogMapper.insert(systemlog);
    }
}
