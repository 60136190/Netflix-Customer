package com.example.moviettn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.moviettn.R;


public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    public int[] slide_background ={
            0,
            0,
            0,
            R.drawable.backgroundslider

    };

    public int[] slide_image ={
            R.drawable.slidera,
            R.drawable.sliderb,
            R.drawable.sliderc,
            0

    };

    public String[] slide_heading={
            "Xem trên bất kì thiết bị nào",
            "Tải xuống và xem ngoại tuyến",
            "Không có hợp đồng phiền toái",
            "Tôi xem bằng cách nào?"
    };
    public String[] slide_descs={
            "Phát trực tiếp trên điện thoại, máy tính bảng, laptop và TV của bạn mà không cần trả thêm phí.",
            "Tiết kiệm dung lượng, xem ngoại tuyến trên máy bay, tàu hoặc tàu ngầm...",
            "Tham gia ngay hôm nay, hủy bất cứ lúc nào",
            "Thành viên đăng ký Netflix có thể xem ngay trong ứng dụng"
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider,container,false);
        ConstraintLayout slideImageView = view.findViewById(R.id.slide_img);
        ImageView imageView = view.findViewById(R.id.img_slider);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescs = view.findViewById(R.id.slide_descs);

        slideImageView.setBackgroundResource(slide_background[position]);
        imageView.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescs.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
