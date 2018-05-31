package cn.xz.blog.config;

import lombok.Data;

/**
 * @author xizhou
 */
@Data
public class SiteConfig {

    /**
     * 工作的dir
     */
    private String workDir;
    /**
     * web页面的文件夹路径
     */
    private String webDir;
    /**
     * 服务器端口
     */
    private int port = 80;
}
