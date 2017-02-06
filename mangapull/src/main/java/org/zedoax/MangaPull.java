package org.zedoax;

import org.zedoax.model.Model;
import org.zedoax.objects.MangaItem;
import java.io.IOException;

public class MangaPull {
    private static Model model = new Model();

    private static MangaPull instance;

    public static MangaPull getInstance() {
        return (instance != null) ? instance : (instance = new MangaPull());

    }

    public String[] request_chapter(String manga_name, String manga_chapter) {
        try {
            return model._get_manga_chapter(manga_name, manga_chapter);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return new String[0];
    }

    public String[] request_chapters(String manga_name) {
        try {
            return model._get_manga_chapters(manga_name);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new String[0];

    }

    public String[] request_chapter_urls(String manga_name) {
        try {
            return model._get_manga_chapter_urls(manga_name);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new String[0];

    }

    /*public String[] request_manga_info(String manga_name, String manga_chapter) {
        try {
            //return model._get_manga_info(manga_name, manga_chapter);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return new String[0];
    }*/

    public MangaItem[] request_mangae(String category) {
        try {
            return model._get_manga_list(category);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return new MangaItem[0];
    }

}
