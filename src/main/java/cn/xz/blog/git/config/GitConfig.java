package cn.xz.blog.git.config;

import lombok.Data;

/**
 * @author xizhou
 */
@Data
public class GitConfig {

    /**
     * git仓库url地址
     */
    private String repository;
    /**
     * 仓库分支 默认为master
     */
    private String branch;
    /**
     * 本地工作目录
     */
    private String directory;
    /**
     * 强制clone 不管本地工作目录是否存在
     */
    private boolean forceClone;
}
