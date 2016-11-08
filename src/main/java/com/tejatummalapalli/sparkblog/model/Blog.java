package com.tejatummalapalli.sparkblog.model;

import com.github.slugify.Slugify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Teja on 11/4/2016.
 */
public class Blog {
    private List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
    private Slugify slg = new Slugify();


    public Blog(){
        addDefaultBlogs();
    }

    //This method populates the blogEntries with dummy blogs
    public void addDefaultBlogs() {
        blogEntries = new ArrayList<BlogEntry>();
        String title1 = "Lambda's in java 8";
        String slug1 = slg.slugify(title1);
        List<Comment> comments1 = new ArrayList<Comment>();
        comments1.add(new Comment("Awesome","Thanks for this blog"));
        comments1.add(new Comment("Awesome Again","But no Thanks now"));
        comments1.add(new Comment("Holy Cow","You are good man"));

        BlogEntry sample1 = new BlogEntry(title1,new Date(),"New Feature",slug1);
        sample1.setComments(comments1);


        String title2 = "Method References in Java8";
        String desc2 = "It is a feature which is related to Lambda Expression. " +
                "It allows us to reference constructors or methods without executing them." +
                " Method references and Lambda are similar in that they both require a target " +
                "type that consist of a compatible functional interface.";
        String slug2 = slg.slugify(title2);
        BlogEntry sample2 = new BlogEntry(title2,new Date(),desc2,slug2);

        String title3 = "Java9 is coming :)";
        String slug3 = slg.slugify(title3);
        BlogEntry sample3 = new BlogEntry(title3,new Date(),"New Feature",slug3);
        blogEntries.add(sample1);
        blogEntries.add(sample2);
        blogEntries.add(sample3);
    }

    public List<BlogEntry> getBlogEntries() {
        return blogEntries;
    }
}
