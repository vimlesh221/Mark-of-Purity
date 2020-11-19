package com.wb.largestfans;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import com.squareup.picasso.Picasso;
import com.wb.largestfans.application.RoyalStagApplication;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.retrofit.apimodel.PhotoAlbum;

import java.util.List;

public class GalleryActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        shareDialog = new ShareDialog(this);
        final ScrollView scroll = (ScrollView) findViewById(R.id.scroll);

        ImageView ivSettingIcon = (ImageView) findViewById(R.id.ivSettingIcon);
        ivSettingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLeaderBoard = new Intent(GalleryActivity.this, DashboardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
            }
        });
        scroll.post(new Runnable() {
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        // setUpToolBar("GALLERY");
        recyclerView.setLayoutManager(layoutManager);
        List<PhotoAlbum> dailyPhotoUrlList = RoyalStagApplication.getInstance().getDailyPhotoUrlList();
        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, dailyPhotoUrlList);
        recyclerView.setAdapter(adapter);
        TextView toolbar_title2 = (TextView) findViewById(R.id.toolbar_title2);
        toolbar_title2.setText("Gallery");

    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View photoView = inflater.inflate(R.layout.gallery_row, parent, false);
            return new MyViewHolder(photoView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //  Photo photo = mPhotoList.get(position);
            ImageView imageView = holder.mPhotoImageView;

            Picasso.with(mContext)
                    .load(dailyPhotoUrlList.get(position).getZPhotoUrl())
                    .placeholder(R.mipmap.ic_launcherno)
                    .error(R.mipmap.ic_launcherno)

                    .resize(180, 180)
                    .centerCrop()
                    .into(imageView);
            //  .transform(new RoundedTransformation(20,0))
        }

        @Override
        public int getItemCount() {
            return (dailyPhotoUrlList.size());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView mPhotoImageView;
            ImageView imggalleryFbIconSix;

            MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                mPhotoImageView.setOnClickListener(this);
                imggalleryFbIconSix = (ImageView) itemView.findViewById(R.id.imggalleryFbIconSix);
                imggalleryFbIconSix.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.iv_photo:
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            PhotoAlbum photoAlbum = dailyPhotoUrlList.get(position);
                            Intent intent = new Intent(mContext, FullImageActivity.class);
                            intent.putExtra("imageUrl", photoAlbum.getZPhotoUrl());
                            startActivity(intent);
                        }
                        break;
                    case R.id.imggalleryFbIconSix:
                        int position1 = getAdapterPosition();
                        if (position1 != RecyclerView.NO_POSITION) {
                            PhotoAlbum photoAlbum = dailyPhotoUrlList.get(position1);
                            String imgurl = photoAlbum.getZPhotoUrl();
                            shareUsingNativeDialog(imgurl);
                        }
                        break;
                }
            }
        }

        private List<PhotoAlbum> dailyPhotoUrlList;
        private Context mContext;

        ImageGalleryAdapter(Context context, List<PhotoAlbum> dailyPhotoUrlList) {
            mContext = context;
            this.dailyPhotoUrlList = dailyPhotoUrlList;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugallery, menu);
        return true;
    }



    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menugallery, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_one:
                Intent intentLeaderBoard = new Intent(GalleryActivity.this, DashboardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
                return true;

            case R.id.menu_two:
                Intent intentLeaderBoardGallery = new Intent(GalleryActivity.this, LeaderBoardActivity.class);
                intentLeaderBoardGallery.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoardGallery);
                finish();
                return true;
            case R.id.menu_three:
                SharedPrefs.clearTag(GalleryActivity.this, "isLogin");
                Intent intentMerchantListJoinToday = new Intent(GalleryActivity.this, LoginActivity.class);
                intentMerchantListJoinToday.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentMerchantListJoinToday);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // use to identify native app is installed or not
    private void shareUsingNativeDialog(String imageUrl) {
        ShareContent shareContent = getLinkContent(imageUrl);
        // checking native app installed on device or not
        if (shareDialog.canShow(shareContent, ShareDialog.Mode.NATIVE)) {
            shareDialog.show(shareContent, ShareDialog.Mode.NATIVE);
        } else {
            shareDialog.show(shareContent);
        }
    }

    // share content with url
    private ShareLinkContent getLinkContent(String imageUrl) {

        return new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(imageUrl))
                .build();
    }
    @Override
    public void onBackPressed() {

        Intent intentLeaderBoard = new Intent(GalleryActivity.this, DashboardActivity.class);
        intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //extra flag indicate that we are navigating with drawer
        startActivity(intentLeaderBoard);
        finish();
    }
}