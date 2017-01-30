package org.zedoax.mangana.objects;

/********************************
 * Zedoax - Mangana Item Holder *
 * @author Elijah Bendinsky     *
 ********************************/

public class MangaItem {
    private String cover_url;
    private String info_url;
    private String title;
    private String categories;
    private String author;
    private String rank;

    public MangaItem(String cover_url, String info_url, String title, String directories, String author, String rank) {
        this.cover_url = cover_url;
        this.info_url = info_url;
        this.title = title;
        this.categories = directories;
        this.author = author;
        this.rank = rank;
    }

    public MangaItem(org.zedoax.objects.MangaItem mItem) {
        this.cover_url = mItem.getCoverUrl();
        this.info_url = mItem.getInfoUrl();
        this.title = mItem.getTitle();
        this.categories = mItem.getDirectories();
        this.author = mItem.getAuthor();
        this.rank = mItem.getRank();

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