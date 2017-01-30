package org.zedoax.objects;

/*******************************************
 * Zedoax - Manga Item, mangapull edition  *
 * @author Elijah Bendinsky                *
 *******************************************/

public class MangaItem {
    private String cover_url;
    private String info_url;
    private String title;
    private String categories;
    private String author;
    private String rank;

    public MangaItem(String cover_url, String info_url, String title, String categories, String author, String rank) {
        this.cover_url = cover_url;
        this.info_url = info_url;
        this.title = title;
        this.categories = categories;
        this.author = author;
        this.rank = rank;

    }

    public String getCoverUrl() {
        return cover_url;
    }

    public String getInfoUrl() {
        return info_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectories() {
        return categories;
    }

    public String getAuthor() {
        return author;
    }

    public String getRank() {
        return rank;
    }

}