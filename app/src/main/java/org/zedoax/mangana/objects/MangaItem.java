package org.zedoax.mangana.objects;

import java.util.ArrayList;
import java.util.Arrays;

/********************************
 * Zedoax - Mangana Item Holder *
 * @author Elijah Bendinsky     *
 ********************************/

public class MangaItem {
    private String cover_url;
    private String index;
    private String title;
    private String description;
    private String categories;
    private String author;
    private String rank;
    private ArrayList<String> chapters = new ArrayList<>();

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

    public MangaItem(org.zedoax.objects.MangaItem mItem) {
        this.cover_url = mItem.getCoverUrl();
        this.index = mItem.getIndex();
        this.title = mItem.getTitle();
        this.description = mItem.getDescription();
        this.categories = mItem.getDirectories();
        this.author = mItem.getAuthor();
        this.rank = mItem.getRank();

    }

    public void setChapters(String[] chapters) {
        if(chapters.length > 0) {
            this.chapters.addAll(Arrays.asList(chapters));
        }
    }

    public String[] getChapters() {
        return chapters.toArray(new String[chapters.size()]);
    }

    public String getCoverUrl() {
        return cover_url;
    }

    public String getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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