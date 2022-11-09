package ErikNivala.demo.dao.FileHandling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class ImageDownloader {

    //returns a path to the downloaded images
    public String DownloadImagesFromLinks(ArrayList<String> links, int id) {

        try {
            String fileName = "../RedditImageData/" + id + "/";


            Path filePathObj = Paths.get(fileName);

            boolean fileExists = Files.exists(filePathObj);
            if (fileExists == false) {
                Files.createDirectories(filePathObj);
            }


            for (int i = 0; i < links.size(); i++) {

                String s = links.get(i);
                URL url = new URL(s);

                String file = fileName;
                if (s.contains(".png")) file += i + ".png";
                else file += i + ".jpg";

                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                System.out.println("Downloaded file: " + file);
            }
            return fileName;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            System.out.println("There was an error downloading the files with given URL links");
            System.out.println(e);
            return null;
        }
    }
}
