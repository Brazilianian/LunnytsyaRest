package lunnytsya.com.domain.main.page;

import lunnytsya.com.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class BackgroundImage extends BaseEntity {

    @Lob
    private String content;

    public BackgroundImage() {
    }

    public BackgroundImage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
