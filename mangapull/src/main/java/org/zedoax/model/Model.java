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

    public String[] _get_manga_chapters(String manga_name) throws IOException {
        List<String> chapters = new ArrayList<>();
        try {
            System.out.println(BASE_URL+MANGA_URL_EXT + manga_name + "/");
            Document page = Jsoup.connect(BASE_URL + MANGA_URL_EXT + manga_name + "/").get();
            Elements manga_chapters = page.getElementsByClass("manga-chapters");
            Elements chaps = manga_chapters.get(0).getElementsByTag("a");
            for (Element chap : chaps) {
                chapters.add(chap.ownText());
            }


        } catch (IOException e) {
            throw new IOException("Error in Page Retrieval _get_manga_chapters");

        }
        return chapters.toArray(new String[chapters.size()]);

    }

    public MangaItem _get_manga(String manga_index) throws IOException {
        // Attempt Manga Retrieval
        try {
            Document page = Jsoup.connect(BASE_URL + MANGA_URL_EXT + manga_index + "/").get();

            Element manga_title = page.getElementsByClass("title").get(0);
            Element manga_cover_url = page.getElementsByClass("detail-cover").get(0);
            Element manga_description = page.getElementsByClass("manga-summary").get(0);
            Elements manga_info = page.select(".detail-info p:not(:last-child)");

            String cover_url = manga_cover_url.attr("src");
            String title = manga_title.ownText();
            String description = "Description:\n" + manga_description.ownText();
            String author = "Author(s): " + manga_info.get(0).getElementsByTag("a").get(0).ownText();
            String rank = manga_info.get(2).ownText();

            String directories = "";
            Elements categories = page.getElementsByClass("manga-genres").get(0).getElementsByAttribute("href");
            for (Element category : categories) {
                String cat = category.ownText();

                if(categories.last() == category) {
                    directories += cat;

                } else {
                    directories += cat + ", ";

                }


            }

            return new MangaItem(cover_url, manga_index, title, description, directories, author, rank);

        } catch (IOException e) {
            throw new IOException("Error in Page Retrieval _get_manga_chapter");

        }

    }

    public MangaItem[] _get_manga_list(String category) throws IOException {
        List<MangaItem> manga = new ArrayList<>();
        try {
            if(category != null) {
                Document page = Jsoup.connect(BASE_URL + MANGA_DIRECTORY_EXT + category + "/").get();
                Elements mangae = page.select("post clearfix");

            }
            else {
                Document page = Jsoup.connect(BASE_URL).get();
                Elements mangae = page.getElementsByClass("post clearfix");

                List<String> info_urls = new ArrayList<>();
                List<String> indexes = new ArrayList<>();

                for (Element man:mangae) {
                    for (Element m : man.getElementsByTag("a")) {
                        if(m.toString().contains("ch-button")) {
                            m.remove();

                        }
                        else {
                            info_urls.add(m.getElementsByTag("a").attr("href"));

                        }

                    }

                }

                for (String s : info_urls) {
                    s = s.substring(s.indexOf("manga/") + 6, s.length() - 1);
                    indexes.add(s);

                }
                for(int i = 0; i < indexes.size(); i++) {
                    manga.add(_get_manga(indexes.get(i)));

                }

            }

        } catch (IOException e) {
            throw new IOException("_get_manga_list: Error in Page Retrieval");

        }
        return manga.toArray(new MangaItem[manga.size()]);

    }

}
