package org.zedoax.objects;

/*******************************************
 * Zedoax - Manga Item, mangapull edition  *
 * @author Elijah Bendinsky                *
 *******************************************/

public class MangaItem {
    private String cover_url;
    private String index;
    private String title;
    private String description;
    private String categories;
    private String author;
    private String rank;

    /**
     * Constructor for MangaItem.  Takes a specific set of Manga field variables
     * for use with several aspects of mangapull, and Manga datasets
     * @param cover_url
     * @param index
     * @param title
     * @param description
     * @param categories
     * @param author
     * @param rank
     */
    public MangaItem(String cover_url, String index, String title, String description, String categories, String author, String rank) {
        this.cover_url = cover_url;
        this.index = index;
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.author = author;
        this.rank = rank;

    }

    public String getCoverUrl() {
        return cover_url;
    }

    public String getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
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