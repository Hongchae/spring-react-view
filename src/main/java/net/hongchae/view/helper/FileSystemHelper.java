package net.hongchae.view.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileSystemHelper {

    private final ClassLoader loader;

    public FileSystemHelper() {
        this.loader = getClass().getClassLoader();
    }

    public boolean isFile(final String fileName) {
        final URL url = this.loader.getResource(fileName);
        if (url == null) {
            return false;
        }

        final String[] strings = url.getPath().split("/");
        final String last = strings[strings.length - 1];

        if (last.contains(".")) { //XXX: assume is a file
            try (InputStream in = this.loader.getResourceAsStream(fileName)) {
                return in != null;
            } catch (final IOException e) {
                // ignore
            }
        }
        return false;
    }

    public String readFile(final String path) throws IOException {
        try (InputStream in = this.loader.getResourceAsStream(path)) {
            return new java.util.Scanner(in, "UTF-8").useDelimiter("\\Z").next();
        }
    }

    public String getDirectory(final String path) throws FileNotFoundException {
        final URL url = this.loader.getResource(path);
        final String[] strings = url.getPath().split("/");
        final String last = strings[strings.length - 1];
        if (last.contains(".")) { //XXX: assume is not a directory
            return strings[strings.length - 2];
        }
        return last;
    }

    public String getFilename(final String path) throws FileNotFoundException {
        final URL url = this.loader.getResource(path);
        final String[] strings = url.getPath().split("/");
        return strings[strings.length - 1];
    }
}