package org.zedoax.model;

import org.zedoax.objects.MangaItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**********************************************************
 * Model Class for Website Interaction and Data Stripping *
 * 1/25/2017                                              *
 * @author Elijah Bendinsky                               *
 **********************************************************/

public class Model {

    public static String MANGA_DEFAULT_STRING = "DEFAULT";

    private static String BASE_URL = "http://m.mangahere.co/";
    private static String MANGA_URL_EXT = "manga/";
    private static String MANGA_DIRECTORY_EXT = "directory/";
    private static String MANGA_VIEW_EXT = "roll_manga/";

    public String[] _get_manga_chapter(String manga_name, String manga_chap) throws IOException {
        List<String> image_urls = new ArrayList<>();

        try {
            Document page = Jsoup.connect(BASE_URL + MANGA_VIEW_EXT + manga_name + "/" + manga_chap).get();
            Elements images = page.select("img[data-original]");
            for (Element image : images) {
                image_urls.add(image.attr("data-original"));
            }


        } catch (IOException e) {
            throw new IOException("Error in Page Retrieval _get_manga_chapter");

        }
        return image_urls.toArray(new String[image_urls.size()]);

    }

    /*public String[] _get_manga(String manga_name) throws IOException {
        //TODO: Implement pulling a manga page, and receive the data
        List<String> image_urls = new ArrayList<>();

        try {
            //TODO: STUB
            Document page = Jsoup.connect(BASE_URL + MANGA_URL_EXT + manga_name + "/").get();
        } catch (IOException e) {
            throw new IOException("Error in Page Retrieval _get_manga_chapter");

        }
        return image_urls.toArray(new String[image_urls.size()]);

    }*/

    public MangaItem[] _get_manga_list(String category) throws IOException {
        List<MangaItem> manga = new ArrayList<>();
        try {
            if(category != null) {
                Document page = Jsoup.connect(BASE_URL + MANGA_DIRECTORY_EXT + category + "/").get();
                Elements mangae = page.select("post clearfix");

                for (Element m:mangae) {
                    String cover_url = m.attr("href");
                    String info_url = m.attr("src");
                    String title = m.getElementById("title").toString();
                    String description = m.getElementById("cover-info").toString();
                    //manga.add(new MangaItem(cover_url, info_url, title, description));

                }

            }
            else {
                Document page = Jsoup.connect(BASE_URL).get();
                Elements mangae = page.getElementsByClass("post clearfix");

                List<String> cover_urls = new ArrayList<>();
                List<String> info_urls = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                List<String> directories = new ArrayList<>();
                List<String> authors = new ArrayList<>();
                List<String> ranks = new ArrayList<>();

                for (Element man:mangae) {
                    for (Element m : man.getElementsByTag("a")) {
                        if(m.toString().contains("ch-button")) {
                            m.remove();
                        }
                        else {
                            info_urls.add(m.getElementsByTag("a").attr("href"));
                            cover_urls.add(m.getElementsByTag("img").attr("src"));

                            Elements m_deep = m.getElementsByClass("cover-info");
                            for (Element n: m_deep) {
                                titles.add(n.child(0).ownText());
                                directories.add(n.child(1).ownText());
                                authors.add(MANGA_DEFAULT_STRING);
                                ranks.add(MANGA_DEFAULT_STRING);

                            }
                        }

                    }

                }
                for(int i = 0; (i < titles.size() && i < cover_urls.size()
                        && i < info_urls.size() && i < directories.size()
                        && i < authors.size() && i < ranks.size()); i++) {
                    manga.add(new MangaItem(cover_urls.get(i), info_urls.get(i), titles.get(i), directories.get(i), authors.get(i), ranks.get(i)));
                    System.out.println(directories.get(i));

                }

            }


        } catch (IOException e) {
            throw new IOException("Error in Page Retrieval _get_manga_list");

        }
        return manga.toArray(new MangaItem[manga.size()]);

    }

}
