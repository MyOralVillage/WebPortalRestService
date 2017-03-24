package org.mov.service;

import org.junit.runner.RunWith;
import org.mov.config.BusinessConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = BusinessConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MOVServiceJpaTest extends AbstractMOVServiceTests {
}
