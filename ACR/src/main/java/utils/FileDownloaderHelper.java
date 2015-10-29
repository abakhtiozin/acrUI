package main.java.utils;

import com.codeborne.selenide.impl.FileDownloader;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Andrii.Bakhtiozin on 14.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class FileDownloaderHelper extends FileDownloader {
    public File download(String url) throws IOException {
        if (url != null && !url.trim().isEmpty()) {
            HttpResponse response = this.executeHttpRequest(url);
            File downloadedFile = this.prepareTargetFile(url, response);
            if (response.getStatusLine().getStatusCode() >= 500) {
                throw new RuntimeException("Failed to download file " + downloadedFile.getName() + ": " + response.getStatusLine());
            } else if (response.getStatusLine().getStatusCode() >= 400) {
                throw new FileNotFoundException("Failed to download file " + downloadedFile.getName() + ": " + response.getStatusLine());
            } else {
                return this.saveFileContent(response, downloadedFile);
            }
        } else {
            throw new IllegalArgumentException("Can't download anything : " + url);
        }
    }
}
