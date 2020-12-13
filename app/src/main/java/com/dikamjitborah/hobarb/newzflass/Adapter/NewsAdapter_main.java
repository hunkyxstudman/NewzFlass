package com.dikamjitborah.hobarb.newzflass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikamjitborah.hobarb.newzflass.Model.NewsSchema;
import com.dikamjitborah.hobarb.newzflass.R;
import com.dikamjitborah.hobarb.newzflass.WebViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class NewsAdapter_main extends ArrayAdapter<NewsSchema> {

    Context df_context;
    ArrayList<NewsSchema> newsSchema;
    ArrayList<NewsSchema> newsSchema_all;

    public NewsAdapter_main(Context c, ArrayList<NewsSchema> newsSchema){
        super(c, R.layout.rows_main, R.id.title_rm, newsSchema);
        this.df_context = c;
        this.newsSchema = newsSchema;
        newsSchema_all = new ArrayList<>(newsSchema);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        NewsSchema newsData = newsSchema.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.rows_main, parent, false);
        TextView rTitle, rDate, rSummary, rSite;
        ImageView rImg;
        rImg = row.findViewById(R.id.img_rm);
        Glide.with(df_context).load(newsData.getImageUrl()).fitCenter().into(rImg);

        rTitle = row.findViewById(R.id.title_rm);
        rDate = row.findViewById(R.id.date_rm);
        rSummary = row.findViewById(R.id.summary_rm);
        rSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  WebView mWebView =new WebView(df_context);
               // mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl(newsData.getUrl());*/
                Toast.makeText(df_context, "Opening " + newsData.getId(), Toast.LENGTH_SHORT).show();

                Intent indisplay = new Intent(df_context, WebViewActivity.class);
                indisplay.putExtra("link",newsData.getUrl());
                indisplay.putExtra("id",newsData.getId());
                indisplay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                df_context.startActivity(indisplay);

            }
        });

        rSite = row.findViewById(R.id.site_rm);



        rTitle.setText(newsData.getTitle());
        rDate.setText(newsData.getPublishedAt());
        rSummary.setText(newsData.getSummary());
        rSite.setText(newsData.getNewsSite());

        return row;



    }

    public void addListItemToAdapter(List<NewsSchema> list){
        newsSchema.addAll(list);
        this.notifyDataSetChanged();


    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<NewsSchema> filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(newsSchema_all);
            } else {
                String filter_pattern = charSequence.toString().toLowerCase().trim();

                for (NewsSchema itemsBean : newsSchema_all) {
                    if (itemsBean.getTitle().toLowerCase().contains(filter_pattern)) {
                        filtered.add(itemsBean);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            newsSchema.clear();
            newsSchema.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
