package com.tejatummalapalli.sparkblog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//blog entry title, date, body, and comments, along with a comment form
// with that allows anonymous users to post comments
public class BlogEntry {
    String title;
    Date date;
    String body;
    List<Comment> comments;
    String slug;

    public BlogEntry(String title, Date date, String body, String slug) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.slug = slug;
        this.comments = new ArrayList<Comment>();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogEntry)) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        return title != null ? title.equals(blogEntry.title) : blogEntry.title == null;

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
