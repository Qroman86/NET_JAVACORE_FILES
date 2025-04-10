package ru.netology.taskthree;

import java.io.*;
import java.util.zip.*;

import ru.netology.tasktwo.GameProgress;

public class Main {

    public static void main(String[] args) {
        String zipPath = "D://Games/savegames/zip.zip";
        String extractPath = "D://Games/savegames/";

        // Разархивируем файлы
        openZip(zipPath, extractPath);

        // Проверяем распакованные файлы
        File dir = new File(extractPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));

        if (files != null) {
            for (File file : files) {
                GameProgress progress = openProgress(file.getAbsolutePath());
                if (progress != null) {
                    System.out.println("Загружен прогресс: " + progress);
                }
            }
        }
    }

    public static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String filePath = extractPath + File.separator + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    System.out.println("Распакован: " + entry.getName());
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при распаковке: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            GameProgress progress = (GameProgress) ois.readObject();
            return progress;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке прогресса: " + e.getMessage());
            return null;
        }
    }
}