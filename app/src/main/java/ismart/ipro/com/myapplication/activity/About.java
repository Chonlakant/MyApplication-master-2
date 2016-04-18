package ismart.ipro.com.myapplication.activity;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ismart.ipro.com.myapplication.BuildConfig;
import ismart.ipro.com.myapplication.R;

public class About extends AppCompatActivity {

    PackageInfo pInfo;
    TextView version,contact,company,contactMail,conService,story1,story2,story3;
    String cont,comN,contM,service,his1,his2,his3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        version = (TextView)findViewById(R.id.app_version);
        contact = (TextView)findViewById(R.id.app_contact);
        company = (TextView)findViewById(R.id.app_companyName);
        contactMail = (TextView)findViewById(R.id.app_emailC);
        conService = (TextView)findViewById(R.id.app_service);
//        story1 = (TextView)findViewById(R.id.his1);
//        story2 = (TextView)findViewById(R.id.his2);
//        story3 = (TextView)findViewById(R.id.his3);



//        try {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        comN = "บริษัท ไอโปรเทรนนิ่ง จำกัด";

        cont = "68/301 ซ. ซอยรามคำแหง 164\n" +
                "แขวงมีนบุรี เขตมีนบุรี\n" +
                "กรุงเทพมหานคร 10520";

        contM = "โทร : 02-917-6062 ถึง 3\n" +
                "อีเมล์ : sale@todayissoftware.com\n" +
                "โทรสาร : 02-907-6776";
        service ="ติดต่อ phanuthat@ipro-training.com";

//        his1 ="บริษัท ไอโปรเทรนนิ่ง ได้ก่อตั้งขึ้น เพื่อต่อยอดความสำเร็จของกลุ่มบริษัทไอเอส โดยกลุ่มบริษัท ไอเอส ก่อตั้งเมื่อปี พ.ศ. 2542  มีเป้าหมายเพื่อ ยกระดับ มาตรฐานหน่วยงานบำรุงรักษาของประเทศให้มีมาตรฐานเทียบเท่าสากล";
//        his2 = "บริษัท ไอโปรเทรนนิ่ง เล็งเห็นถึงความสำคัญของการพัฒนาบุคลากรในสายอุตสาหกรรม โดยบริษัทมีแนวความคิดว่า องค์กรจะพัฒนานอกจากระบบการจัดการที่ดี ยังต้องมีบุคลากรที่มีคุณภาพ โดยบุคลากร ถือเป็นทรัพยากรที่มีคุณค่าแก่องค์กรเป็นอย่างยิ่ง";
//        his3 = "ด้วยทีมงานบุคลากรที่มีคุณภาพ มีความชำนาญเฉพาะด้าน มีประสบการณ์ในสายงานซัพพลายเชนโดยตรง สิ่งเหล่านี้จะสามารถตอบโจทย์ความต้องการของธุรกิจ อุตสาหกรรมในปัจจุบัน ได้เป็นอย่างดี";

        version.setText(versionName);
        company.setText(comN);
        contact.setText(cont);
        contactMail.setText(contM);
        conService.setText(service);
//        story1.setText(R.string.aboutpage);
//        story2.setText(R.string.aboutpage2);
//        story3.setText(R.string.aboutpage3);
    }
}
