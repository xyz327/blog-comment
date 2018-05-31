package cn.xz.blog.git.repository.impl;

import cn.xz.blog.git.config.GitConfig;
import cn.xz.blog.git.repository.GitRepository;
import com.google.common.io.MoreFiles;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;

/**
 * @author xizhou
 */
@Slf4j
public class GitRepositoryImpl implements GitRepository {
    /**
     * clone git仓库
     */
    @Override
    public Git cloneRepository(GitConfig gitConfig) {
        File localWorkspace = new File(gitConfig.getDirectory());
        if (!localWorkspace.canWrite()) {
            throw new IllegalStateException("directory不可写,请检查权限");
        }
        if (!localWorkspace.exists()) {
            try {
                Files.createDirectory(localWorkspace.toPath());
            } catch (IOException e) {
                throw new IllegalStateException("directory创建失败:"+e.getMessage());
            }
        }
        if (gitConfig.isForceClone()) {
            try {
                MoreFiles.deleteDirectoryContents(localWorkspace.toPath());
            } catch (IOException e) {
                throw new IllegalStateException("directory删除失败:"+e.getMessage());
            }
        }
        Git git = null;
        try {
            CloneCommand cloneRepository = Git.cloneRepository();
            git = cloneRepository.setURI(gitConfig.getRepository())
                .setBranch(gitConfig.getBranch())
                .setDirectory(localWorkspace)
                .setBare(false)
                .call();
        } catch (JGitInternalException e) {
            log.warn(e.getMessage());
            String message = MessageFormat.format(
                JGitText.get().cloneNonEmptyDirectory, localWorkspace.getName());
            if(e.getMessage().equals(message)){
                try {
                    git = Git.open(localWorkspace);
                } catch (IOException e1) {
                    throw new IllegalStateException("打开本地git仓库出错:"+e.getMessage());
                }
            }
        } catch (GitAPIException e) {
            throw new IllegalStateException("clone仓库出错:"+e.getMessage());
        }
        return git;
    }

}
