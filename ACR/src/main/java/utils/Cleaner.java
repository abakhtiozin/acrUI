package main.java.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Andrii.Bakhtiozin on 10.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Cleaner {

    public static void deleteFolders(File file, String fileNameContains) {
        for (String name : file.list()) {
            if (new File(file.toString() + "\\" + name).isDirectory()) {
                if (name.contains(fileNameContains)) {
                    try {
                        removeRecursive(FileSystems.getDefault().getPath(file.toString() + "\\" + name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void deleteFilesInFolder(File file, String fileNameContains) {
        for (String name : file.list()) {
            if (!new File(file.toString() + "\\" + name).isDirectory()) {
                if (name.contains(fileNameContains)) {
                    try {
                        Files.delete(FileSystems.getDefault().getPath(file.toString() + "\\" + name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void removeRecursive(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // try to delete the file anyway, even if its attributes
                // could not be read, since delete-only access is
                // theoretically possible
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed; propagate exception
                    throw exc;
                }
            }
        });
    }
}
