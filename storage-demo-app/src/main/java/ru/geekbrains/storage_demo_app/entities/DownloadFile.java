package ru.geekbrains.storage_demo_app.entities;

import org.primefaces.model.StreamedContent;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
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
    public InputStream getStream() {
        return new BufferedInputStream(new ByteArrayInputStream(file.getContent()), 8189);
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
