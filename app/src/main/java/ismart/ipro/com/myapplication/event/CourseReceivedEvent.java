package ismart.ipro.com.myapplication.event;

import ismart.ipro.com.myapplication.model.Course;

/**
 * Created by ELMTRIX on 4/4/2559.
 */
public class CourseReceivedEvent {
    private static final String TAG = EnnigyReceivedEvent.class.getSimpleName();
    private Course post;

    public CourseReceivedEvent(Course post){
        this.post = post;
    }

    public Course getPost() {
        return post;
    }
}
