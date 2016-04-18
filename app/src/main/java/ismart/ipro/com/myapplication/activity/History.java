package ismart.ipro.com.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ismart.ipro.com.myapplication.R;

public class History extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        text = (TextView)findViewById(R.id.texthis);
        String string ="บริษัท ไอโปรเทรนนิ่ง ได้ก่อตั้งขึ้น เพื่อต่อยอดความสำเร็จของกลุ่มบริษัทไอเอส โดยกลุ่มบริษัท ไอเอส ก่อตั้งเมื่อปี พ.ศ. 2542  มีเป้าหมายเพื่อ ยกระดับ มาตรฐานหน่วยงานบำรุงรักษาของประเทศให้มีมาตรฐานเทียบเท่าสากล\n" +
                "\n" +
                "บริษัท ไอโปรเทรนนิ่ง เล็งเห็นถึงความสำคัญของการพัฒนาบุคลากรในสายอุตสาหกรรม โดยบริษัทมีแนวความคิดว่า องค์กรจะพัฒนานอกจากระบบการจัดการที่ดี ยังต้องมีบุคลากรที่มีคุณภาพ โดยบุคลากร ถือเป็นทรัพยากรที่มีคุณค่าแก่องค์กรเป็นอย่างยิ่ง\n" +
                "\n" +
                "ด้วยทีมงานบุคลากรที่มีคุณภาพ มีความชำนาญเฉพาะด้าน มีประสบการณ์ในสายงานซัพพลายเชนโดยตรง สิ่งเหล่านี้จะสามารถตอบโจทย์ความต้องการของธุรกิจ อุตสาหกรรมในปัจจุบัน ได้เป็นอย่างดี";
        text.setText(string);
    }
}
