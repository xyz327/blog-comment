package cn.xz.blog.git.repository;

import cn.xz.blog.git.config.GitConfig;
import java.io.File;
import org.eclipse.jgit.api.Git;

/**
 * @author xizhou
 */
public interface GitRepository {

    /**
     * clone git仓库
     * @return git对象
     */
    Git cloneRepository(GitConfig gitConfig);


}
