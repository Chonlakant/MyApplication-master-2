package ismart.ipro.com.myapplication.event;


import ismart.ipro.com.myapplication.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class TipReceivedEvent {

    private static final String TAG = TipReceivedEvent.class.getSimpleName();
    private Post post;

    public TipReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}