package ismart.ipro.com.myapplication.service;



import ismart.ipro.com.myapplication.model.Course;
import ismart.ipro.com.myapplication.model.Post;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiServiceCourse {



    @GET("/i_community/json_video.php")
    void getCourse(Callback<Course> callback);

}
