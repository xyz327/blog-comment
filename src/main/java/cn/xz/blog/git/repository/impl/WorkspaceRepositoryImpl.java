package cn.xz.blog.git.repository.impl;

import cn.xz.blog.git.repository.WorkspaceRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;

/**
 * @author xizhou
 */
@RequiredArgsConstructor
@Slf4j
public class WorkspaceRepositoryImpl implements WorkspaceRepository {

    @NonNull
    private Git git;
    /**
     * 在本地工作空间创建新文件
     *
     * @param fileName 要新增的文件名
     */
    @Override
    public File createFile(String fileName) {
        File directory = git.getRepository().getDirectory();
        File newFile = new File(directory, fileName);
        try {
            Files.createFile(newFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }
}
