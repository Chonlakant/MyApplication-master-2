package ismart.ipro.com.myapplication.service;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import ismart.ipro.com.myapplication.event.ApiBus;
import ismart.ipro.com.myapplication.event.ArticlesReceivedEvent;
import ismart.ipro.com.myapplication.event.ArticlesRequestedEvent;
import ismart.ipro.com.myapplication.event.CourseReceivedEvent;
import ismart.ipro.com.myapplication.event.CourseRequestedEvent;
import ismart.ipro.com.myapplication.event.EnnigyReceivedEvent;
import ismart.ipro.com.myapplication.event.EnningRequestedEvent;
import ismart.ipro.com.myapplication.event.FeedReceivedEvent;
import ismart.ipro.com.myapplication.event.FeedRequestedEvent;
import ismart.ipro.com.myapplication.event.IsoReceivedEvent;
import ismart.ipro.com.myapplication.event.IsoRequestedEvent;
import ismart.ipro.com.myapplication.event.LogisiticsRequestedEvent;
import ismart.ipro.com.myapplication.event.LogisticsReceivedEvent;
import ismart.ipro.com.myapplication.event.MaintenanceReceivedEvent;
import ismart.ipro.com.myapplication.event.MaintenanceRequestedEvent;
import ismart.ipro.com.myapplication.event.ManagemantReceivedEvent;
import ismart.ipro.com.myapplication.event.ManagementRequestedEvent;
import ismart.ipro.com.myapplication.event.NewsReceivedEvent;
import ismart.ipro.com.myapplication.event.NewsRequestedEvent;
import ismart.ipro.com.myapplication.event.PhotoReceivedEvent;
import ismart.ipro.com.myapplication.event.PhotoRequestedEvent;
import ismart.ipro.com.myapplication.event.ProductionReceivedEvent;
import ismart.ipro.com.myapplication.event.ProductionRequestedEvent;
import ismart.ipro.com.myapplication.event.PurchaseReceivedEvent;
import ismart.ipro.com.myapplication.event.PurchaseRequestedEvent;
import ismart.ipro.com.myapplication.event.QualityReceivedEvent;
import ismart.ipro.com.myapplication.event.QualityRequestedEvent;
import ismart.ipro.com.myapplication.event.SafetyReceivedEvent;
import ismart.ipro.com.myapplication.event.SafetyRequestedEvent;
import ismart.ipro.com.myapplication.event.SaleReceivedEvent;
import ismart.ipro.com.myapplication.event.SaleRequestedEvent;
import ismart.ipro.com.myapplication.event.SuccessReceivedEvent;
import ismart.ipro.com.myapplication.event.SuccessRequestedEvent;
import ismart.ipro.com.myapplication.event.TipReceivedEvent;
import ismart.ipro.com.myapplication.event.TipRequestedEvent;
import ismart.ipro.com.myapplication.event.TraingReceivedEvent;
import ismart.ipro.com.myapplication.event.TraingRequestedEvent;
import ismart.ipro.com.myapplication.model.Course;
import ismart.ipro.com.myapplication.model.Post;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ApiHandlerCourse {

    public Context context;
    private ApiServiceCourse api;
    private ApiBus apiBus;

    public ApiHandlerCourse(Context context, ApiServiceCourse api,
                            ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }


    @Subscribe
    public void onPhoto(final CourseRequestedEvent event) {
        api.getCourse(new Callback<Course>() {
            @Override
            public void success(Course post, Response response) {
                    Log.e("asds",post.getPost().size()+"");
                    ApiBus.getInstance().postQueue(new CourseReceivedEvent(post));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
