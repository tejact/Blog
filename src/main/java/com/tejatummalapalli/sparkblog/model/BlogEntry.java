package com.tejatummalapalli.sparkblog.model;

import java.util.Date;
import java.util.List;

//blog entry title, date, body, and comments, along with a comment form
// with that allows anonymous users to post comments
public class BlogEntry {
    String title;
    Date date;
    String body;
    List<Comment> comments;

    public BlogEntry(String title, Date date, String body) {
        this.title = title;
        this.date = date;
        this.body = body;
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
