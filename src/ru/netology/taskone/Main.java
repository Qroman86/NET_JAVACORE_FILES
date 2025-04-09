package ru.netology.taskone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {


    public static StringBuilder sbLogger = new StringBuilder();

    public static void main(String[] args) {

        String rootDirPath = "D://Games";

        File rootDir = new File(rootDirPath);

        if (rootDir.exists() && rootDir.canWrite()) {

            String srcDirPath = path(rootDirPath, "src");

            if (createIfNotExistsDir(srcDirPath)) {
                String mainDirPath = path(srcDirPath, "main");
                if (createIfNotExistsDir(mainDirPath)) {
                    String mainFilePath = path(mainDirPath, "Main.java");
                    createIfNotExistsFile(mainFilePath);

                    String utilsFilePath = path(mainDirPath, "Utils.java");
                    createIfNotExistsFile(utilsFilePath);
                }

                createIfNotExistsDir(path(srcDirPath, "test"));
            }

            String resDirPath = path(rootDirPath, "res");
            if (createIfNotExistsDir(resDirPath)) {
                createIfNotExistsDir(path(resDirPath, "drawables"));
                createIfNotExistsDir(path(resDirPath, "vectors"));
                createIfNotExistsDir(path(resDirPath, "icons"));
            }

            String tempDirPath = path(rootDirPath, "temp");
            if (createIfNotExistsDir(tempDirPath)) {
                String tempFilePath = path(tempDirPath, "temp.txt");
                if (createIfNotExistsFile(tempFilePath)) {
                    try (FileWriter writer = new FileWriter(tempFilePath)) {
                        writer.write(sbLogger.toString());
                        System.out.println("Лог успешно записан в файл " + tempFilePath);
                    } catch (IOException e) {
                        System.out.println("Ошибка при записи в файл: " + e.getMessage());
                    }
                }
            }

        }
    }

    private static String path(String parentDirPath, String dirOrFileName) {
        return parentDirPath + "/" + dirOrFileName;
    }


    private static boolean createIfNotExistsFile(String newFilePath) {
        boolean isFileExistedOrCreated = false;
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            isFileExistedOrCreated = true;
            sbLogger.append("Файл ").append(newFilePath).append(" уже существует.\n");
        } else {
            try {
                newFile.createNewFile();
                isFileExistedOrCreated = true;
                sbLogger.append("Файл ").append(newFilePath).append(" был успешно создан.\n");
            } catch (IOException e) {
                isFileExistedOrCreated = false;
                sbLogger.append("Файл ").append(newFilePath).append(" не был создан\n");
            }
        }
        return isFileExistedOrCreated;
    }

    private static boolean createIfNotExistsDir(String newDirPath) {
        boolean isDirExistedOrCreated = false;
        File newDir = new File(newDirPath);
        if (newDir.exists()) {
            isDirExistedOrCreated = true;
            sbLogger.append("Директория ").append(newDirPath).append(" уже существует.\n");
        } else if (newDir.mkdir()) {
            isDirExistedOrCreated = true;
            sbLogger.append("Директория ").append(newDirPath).append(" была успешно создана.\n");
        } else {
            isDirExistedOrCreated = false;
            sbLogger.append("Директория ").append(newDirPath).append(" не был создан\n");
        }
        return isDirExistedOrCreated;
    }
}
