package ru.itmo.wp.form;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 30)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @Size(max = 2000)
    @Pattern(regexp = "([a-z]{1,2000}\\s+)*([a-z]{1,2000})", message = "Expected words(length less then 2000) of lowercase Latin letters")
    private String tags;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getTags() {
        return tags;
    }
}

