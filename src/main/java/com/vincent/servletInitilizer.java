package com.vincent;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @@author vincent
 * @create2023-12-30-8:43
 */
public class servletInitilizer extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //打包到服务器之后，运行的时servlet容器。把myMain.class放在builder中
        return builder.sources(MyMain.class);
    }
}
