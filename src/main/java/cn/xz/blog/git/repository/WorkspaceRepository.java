package cn.xz.blog.git.repository;

import java.io.File;

/**
 * 本地工作目录 repository
 * @author xizhou
 */
public interface WorkspaceRepository {
    /**
     * 在本地工作空间创建新文件
     * @param fileName 文件名
     * @return 新创建的文件
     */
    File createFile(String fileName);
}
