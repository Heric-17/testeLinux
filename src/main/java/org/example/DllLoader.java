package org.example;

import org.opencv.core.Mat;

import java.io.*;
import java.util.Locale;

public class DllLoader {
    public static void loadLibrary() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String pathLocation = "";
        String sufix = "";
        if (os.contains("win")) {
            pathLocation = "/opencv_java4100.dll";
            sufix = ".dll";
        } else {
            pathLocation = "/libopencv_java410.so";
            sufix = ".so";
        }
        try (InputStream in = DllLoader.class.getResourceAsStream(pathLocation)) {
            if (in == null) {
                throw new FileNotFoundException("DLL não encontrada no classpath: " + pathLocation);
            }

            File tempFile = File.createTempFile("lib", sufix);
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            System.load(tempFile.getAbsolutePath());
            Mat matrix = new Mat();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " FileNotFoundException");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " IOException");
        }
    }

    public static void main(String[] args) {
        loadLibrary();
    }
}