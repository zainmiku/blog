package com.fanhoufang.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlogApplicationTests {

	@Test
	void generator() {
		CodeGenerator.build("m_user").generate();
	}

}
