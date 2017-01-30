package org.zedoax;

import org.zedoax.model.Model;
import org.zedoax.objects.MangaItem;

import java.io.IOException;

public class MangaPull {
    Model model = new Model();

    public String[] request_chapter(String manga_name, String manga_chapter) {
        try {
            return model._get_manga_chapter(manga_name, manga_chapter);
        } catch (IOException e) {
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
