import cn.xz.blog.git.config.GitConfig;
import com.google.common.io.MoreFiles;
import java.io.File;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author xizhou
 */
@Slf4j
@Ignore
public class JGitTest {


    @Test
    public void pull() throws Exception {
        GitConfig gitConfig = new GitConfig();


        gitConfig.setRepository("https://github.com/xyz327/dubbo-example.git");
        gitConfig.setDirectory("/home/kiway067/workspace/blog/src/test/git");
        gitConfig.setForceClone(false);

        File localWorkspace = new File(gitConfig.getDirectory());
        if (!localWorkspace.canWrite()) {
            throw new IllegalStateException("directory不可写,请检查权限");
        }
        if (!localWorkspace.exists()) {
            localWorkspace.mkdirs();
        }
        if (gitConfig.isForceClone()) {
            MoreFiles.deleteDirectoryContents(localWorkspace.toPath());
            //localWorkspace.delete();
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
                log.warn("git 目录不为空");
                git = Git.open(localWorkspace);
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }
}
