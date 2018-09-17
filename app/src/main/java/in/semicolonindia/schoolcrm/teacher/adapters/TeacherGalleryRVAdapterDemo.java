package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import in.semicolonindia.schoolcrm.R;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherGalleryRVAdapterDemo extends RecyclerView.Adapter<TeacherGalleryRVAdapterDemo.ViewHolder> {

    private Context context;
    private String[] images;
    private WebView webView;

    public TeacherGalleryRVAdapterDemo(Context context, String[] images, WebView webView) {
        this.context = context;
        this.images = images;
        this.webView = webView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_gallery_list_item, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(images[position])
                .thumbnail(0.5f)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.imagenotfound)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_Gallery);
        holder.img_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String html = "<style>img{max-width: 100%;} body{background-color:#000000;position: absolute;top: 0;bottom: 0;left: 0;" +
                        "right: 0;display: flex;justify-content: center;align-items: center;}</style> <html><head></head><body><center><img src=\""
                        + images[position] + "\"></center></body></html>";
                webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_Gallery;

        public ViewHolder(View itemView) {
            super(itemView);
            img_Gallery = itemView.findViewById(R.id.img_Gallery);
        }
    }
}
