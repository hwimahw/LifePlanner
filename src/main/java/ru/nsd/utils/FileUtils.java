package ru.nsd.utils;

import ru.nsd.exceptions.WriteToFileException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class FileUtils {

    public static void sendFile(HttpServletRequest request, HttpServletResponse response,
                                String filePath){
        try {
            File downloadFile = new File(filePath);
            downloadFile.createNewFile();
            FileInputStream inStream = new FileInputStream(downloadFile);
            ServletContext context = request.getServletContext();
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();
            byte[] byteArray = new byte[4096];
            int bytesRead = -1;
            //byte[] byteArray = FileUtils.readFileToByteArray(new File("out.txt"));
            while ((bytesRead = inStream.read(byteArray)) != -1) {
                outStream.write(byteArray, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        }catch (Exception ex){
            throw new WriteToFileException("Write file exception");
        }
    }
}
