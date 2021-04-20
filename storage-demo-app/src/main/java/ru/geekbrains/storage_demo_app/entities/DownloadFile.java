package ru.geekbrains.storage_demo_app.entities;

import org.primefaces.model.StreamedContent;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DownloadFile implements StreamedContent, Serializable {

    private File file;


    public DownloadFile(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public InputStream getStream() throws OutOfMemoryError {
        return new ByteArrayInputStream(file.getContent());
    }

    @Override
    public String getContentType() {
        return file.getType();
    }

    @Override
    public String getContentEncoding() {
        return Arrays.toString(file.getName().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Integer getContentLength() {
        return file.getSize().intValue();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
