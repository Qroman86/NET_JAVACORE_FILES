package ru.netology.tasktwo;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        // Создаем три экземпляра GameProgress
        GameProgress state1 = new GameProgress(100, 3, 10, 254.32);
        GameProgress state2 = new GameProgress(85, 5, 15, 478.95);
        GameProgress state3 = new GameProgress(50, 8, 20, 1024.78);

        // Пути для сохранения
        String saveDir = "D://Games/savegames/";
        String save1 = saveDir + "save1.dat";
        String save2 = saveDir + "save2.dat";
        String save3 = saveDir + "save3.dat";

        // Сохраняем состояния в файлы
        saveGame(save1, state1);
        saveGame(save2, state2);
        saveGame(save3, state3);

        // Архивируем сохранения
        String zipFile = saveDir + "zip.zip";
        List<String> filesToZip = Arrays.asList(save1, save2, save3);
        zipFiles(zipFile, List.of(save1, save2, save3));

        // Удаляем исходные файлы
        cleanupFiles(filesToZip);
    }

    // Метод для архивации файлов
    public static void saveGame(String path, GameProgress state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(state);
            System.out.println("Сохранено в: " + path);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : files) {
                File file = new File(filePath);
                if (!file.exists()) {
                    System.err.println("Файл не найден: " + filePath);
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                    System.out.println("Файл " + filePath + " добавлен в архив " + zipPath);
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла " + filePath + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании архива: " + e.getMessage());
        }

    }

    // Удаление файлов после архивации
    private static void cleanupFiles(List<String> files) {
        for (String file : files) {
            if (new File(file).delete()) {
                System.out.println("Удален: " + file);
            } else {
                System.err.println("Не удалось удалить: " + file);
            }
        }
    }
}
