package com.nasserver.gkmept.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.nasserver.gkmept.Dao.ImageFile;
import com.nasserver.gkmept.EnlargeImageActivity;
import com.nasserver.gkmept.R;

import java.io.File;
import java.util.List;

public class AdapterImageFile extends RecyclerView.Adapter <AdapterImageFile.PhotoViewHolder>{


    private List<ImageFile> imageFileList;
    private Context imageFileContext;

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewFileShow;
        public TextView textViewFileTime;
        public TextView textViewTitle;
        public PhotoViewHolder(View view){
            super(view);
        }
    }

    public class ViewHolderTitle extends PhotoViewHolder{
        public ViewHolderTitle(View view){
            super(view);
            textViewTitle=(TextView)view.findViewById(R.id.textTitleShow);
        }
    }

    //region 用于设置列数，如果是标题则只用一列，如果为图片则设置四列
    /**
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager=((GridLayoutManager)layoutManager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(getItemViewType(position)==1){
                        return 4;
                    }else {
                        return 1;
                    }
                }
            });
        }
    }
    //endregion

    public class ViewHolderImage extends PhotoViewHolder{
        public ViewHolderImage(View view){
            super(view);
            imageViewFileShow=(ImageView)view.findViewById(R.id.imageViewPhotoImage);
            textViewFileTime=(TextView)view.findViewById(R.id.textViewPhotoTime);
        }
    }

    public AdapterImageFile(List<ImageFile> imageFiles){
        imageFileList=imageFiles;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder viewHolder, final int position) {
        ImageFile imageFile=imageFileList.get(position);
        if(viewHolder instanceof ViewHolderTitle){
            viewHolder.textViewTitle.setText(imageFile.getStringImageTime());
        }else {
            if(new File(imageFile.getStringImagePath())!=null){
                Glide.with(imageFileContext).load(imageFile.getStringImagePath()).into(viewHolder.imageViewFileShow);
                viewHolder.textViewFileTime.setText(imageFile.getStringImageTime());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imageFileList.get(position).getAnIntTitle();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        imageFileContext=viewGroup.getContext();
        if(viewType==1){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_title_item,viewGroup,false);
            PhotoViewHolder photoViewHolder=new ViewHolderTitle(view);
            return photoViewHolder;
        }else {
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_file_item,viewGroup,false);
            final PhotoViewHolder photoViewHolder=new ViewHolderImage(view);
            photoViewHolder.imageViewFileShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), EnlargeImageActivity.class);
                    intent.putExtra("ImagePath",imageFileList.get(photoViewHolder.getAdapterPosition()).getStringImagePath());
                    v.getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)v.getContext(),v,"shareView").toBundle());
                }
            });
            return photoViewHolder;
        }
    }

    @Override
    public int getItemCount() {
        return imageFileList.size();
    }

}
