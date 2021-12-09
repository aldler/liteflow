package com.yomahub.liteflow.test.tag;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.function.Function;

/**
 * springboot环境下隐私投递的测试
 * @author Bryan.Zhang
 * @since 2.5.0
 */
@RunWith(SpringRunner.class)
@TestPropertySource(value = "classpath:/tag/application.properties")
@SpringBootTest(classes = NodeTagSpringbootTest.class)
@EnableAutoConfiguration
@ComponentScan({"com.yomahub.liteflow.test.tag.cmp"})
public class NodeTagSpringbootTest extends BaseTest {

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    public void testTag1() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("123",response.getSlot().getData("test"));
    }

    @Test
    public void testTag2() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain2", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a==>a==>a==>c==>e", response.getSlot().printStep());
    }

    //测试多线程when情况下的tag取值是否正确
    //这里循环多次的原因是，因为when多线程，有时候因为凑巧，可能正确。所以多次情况下在2.6.4版本肯定出错
    @Test
    public void testTag3() throws Exception{
        for (int i = 0; i < 50; i++) {
            LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain3", "arg");
            Assert.assertTrue(response.isSuccess());
            ConcurrentHashSet<String> testSet = response.getSlot().getData("test");
            Assert.assertEquals(3, testSet.size());
        }
    }
}
