package cn.xz.blog.config;

import cn.xz.blog.git.config.GitConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xyz32
 * @date 2018/5/31 23:06
 */
public enum Global {
    Instance;
    @Setter
    @Getter
    private SiteConfig siteConfig;
    @Setter
    @Getter
    private GitConfig gitConfig;

}
