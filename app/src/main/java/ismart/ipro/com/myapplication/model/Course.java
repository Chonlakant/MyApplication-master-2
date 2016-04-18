package ismart.ipro.com.myapplication.model;

import java.util.List;

/**
 * Created by ELMTRIX on 4/4/2559.
 */
public class Course {
    /**
     * title : การสื่อสารเบื้องต้น ตอนที่ 1
     * url : http://mn-community.com/video/Communication1rev1.mp4
     * image : http://todayissoftware.com/i_community/uploads/slide1.png
     */

    private List<PostEntity> post;

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public static class PostEntity {
        private String title;
        private String url;
        private String image;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getImage() {
            return image;
        }
    }
}
