package com.wb.largestfans.notification;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.touchlane.exovideo.ExoVideoController;
import com.touchlane.exovideo.ExoVideoView;
import com.wb.largestfans.DashboardActivity;
import com.wb.largestfans.GalleryActivity;
import com.wb.largestfans.LeaderBoardActivity;
import com.wb.largestfans.LoginActivity;
import com.wb.largestfans.R;
import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.constant.AppConstants;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.retrofit.apimodel.GetLeaderBoardResponse;
import com.wb.largestfans.retrofit.apimodel.GetNoticeBoardResponse;
import com.wb.largestfans.retrofit.apimodel.MonthlyLeaderBoard;
import com.wb.largestfans.retrofit.apimodel.NoticeBoard;
import com.wb.largestfans.retrofit.apimodel.NoticeBoardData;
import com.wb.largestfans.utility.RoundedTransformation;
import com.wb.largestfans.utility.Utility;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Context mContext;
    private ExoVideoController mExoVideoController;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ImageView ivSettingIcon = (ImageView) findViewById(R.id.ivSettingIcon);
        mRecyclerView = (RecyclerView) findViewById(R.id.videos);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
                Intent intentLeaderBoard = new Intent(NotificationActivity.this, DashboardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
            }
        });
        mExoVideoController = new ExoVideoController(mContext);
        if (Utility.isConnectedToInternet(NotificationActivity.this)) {
            WebServiceManager.getNoticeBoardData(this, SharedPrefs.getString(this, "mobile"), new ResponseCallBack() {
                @Override
                public void success(Object object) {
                    if (object instanceof GetNoticeBoardResponse) {
                        GetNoticeBoardResponse getNoticeBoardResponse = (GetNoticeBoardResponse) object;
                        List<NoticeBoard> noticeBoardList = getNoticeBoardResponse.getData().getNoticeBoard();



                      /*  ArrayList<Model> list= new ArrayList<>();

                        list.add(new Model(Model.IMAGE_TYPE,"Hi. I display a cool image too besides the omnipresent TextView.",R.drawable.wtc));
                        list.add(new Model(Model.AUDIO_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.",12));
                        list.add(new Model(Model.IMAGE_TYPE,"Hi again. Another cool image here. Which one is better?",R.drawable.snow));
*/
                        mRecyclerView.setAdapter(new VideosAdapter(noticeBoardList,mContext));
                    }
                }

                @Override
                public void failure(String message) {

                }
            });
        }else {
            Utility.delertAlert(NotificationActivity.this, getString(R.string.no_internet_connection));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mExoVideoController.init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExoVideoController.release();
    }

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        LinearLayout card_view;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.card_view = (LinearLayout) itemView.findViewById(R.id.card_view);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
        }

    }
    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {



        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);


            this.image = (ImageView) itemView.findViewById(R.id.background);

        }

    }
    private static class VideoViewHolder extends RecyclerView.ViewHolder {

        private ExoVideoView mExoVideoView;
        private ImageView mPlayButton;

        VideoViewHolder(View view, ExoVideoController exoVideoController,
                ExoVideoView.ThumbnailProvider thumbnailProvider) {
            super(view);
            mPlayButton = (ImageView) view.findViewById(R.id.btn_play);
            mExoVideoView = (ExoVideoView) view.findViewById(R.id.exo_video);
            mExoVideoView.setExoVideoController(exoVideoController);
            mExoVideoView.setThumbnailProvider(thumbnailProvider);

            mExoVideoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mExoVideoView.isPlaying()) {
                        setPlayButtonVisible(true);
                        mExoVideoView.pause();
                    } else {
                        setPlayButtonVisible(false);
                        mExoVideoView.play();
                    }
                }
            });

            mExoVideoView.setVideoEndListener(new ExoVideoView.VideoEndListener() {
                @Override
                public void onVideoEnded() {
                    setPlayButtonVisible(true);
                }

                @Override
                public void onPlayerDisconnected() {
                    setPlayButtonVisible(true);
                }
            });

        }

        void setPlayButtonVisible(boolean visible) {
            mPlayButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }

        void setVideoSource(Uri uri) {
            mExoVideoView.setSource(uri);
        }

    }

    private class VideosAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<NoticeBoard> dataSet;
        Context mContext;
        int total_types;

        public VideosAdapter(List<NoticeBoard> data, Context context) {
            this.dataSet = data;
            this.mContext = context;
            total_types = dataSet.size();

        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            switch (viewType) {
                case AppConstants.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);
                case AppConstants.IMAGE_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                    return new ImageTypeViewHolder(view);
                case AppConstants.AUDIO_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
                    return new VideoViewHolder(view, mExoVideoController,
                            mThumbnailProvider);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            NoticeBoard noticeBoard = dataSet.get(position);
            if (noticeBoard != null) {
                switch (noticeBoard.getNoticeBoardType()) {
                    case "Text":
                        ((TextTypeViewHolder) holder).card_view.setVisibility(View.GONE);
                        ((TextTypeViewHolder) holder).txtType.setText(noticeBoard.getNoticeBoardText());
                        break;
                    case "Image":
                        Glide.with(mContext)
                                .load(noticeBoard.getNoticeBoardImage())
                                .into(((ImageTypeViewHolder) holder).image);

                        break;
                    case "Video":
                        Uri uri = Uri.parse(noticeBoard.getNoticeBoardVideo());
                        ((VideoViewHolder)holder).setVideoSource(uri);
                        break;
                }}
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        @Override
        public int getItemViewType(int position) {

            switch (dataSet.get(position).getNoticeBoardType()) {
                case "Text":
                    return AppConstants.TEXT_TYPE;
                case "Image":
                    return AppConstants.IMAGE_TYPE;
                case "Video":
                    return AppConstants.AUDIO_TYPE;
                default:
                    return -1;
            }


        }
    }

    private ExoVideoView.ThumbnailProvider mThumbnailProvider =
            new ExoVideoView.ThumbnailProvider() {
                @Override
                public void provideThumbnail(ImageView imageView, Uri uri) {
                    // see MyApplication for Picasso configuration
                    Picasso.with(NotificationActivity.this).load(uri).into(imageView);
                }
            };


    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menunotice);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_one:
                Intent dashBoard = new Intent(NotificationActivity.this, DashboardActivity.class);
                dashBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(dashBoard);
                finish();
                return true;
            case R.id.menu_two:
                Intent intentLeaderBoard = new Intent(NotificationActivity.this, LeaderBoardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
                return true;

            case R.id.menu_three:
                Intent intentLeaderBoardGallery = new Intent(NotificationActivity.this, GalleryActivity.class);
                intentLeaderBoardGallery.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoardGallery);
                finish();
                return true;
            case R.id.menu_four:
                SharedPrefs.clearTag(NotificationActivity.this, "isLogin");
                Intent intentMerchantListJoinToday = new Intent(NotificationActivity.this, LoginActivity.class);
                intentMerchantListJoinToday.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentMerchantListJoinToday);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intentLeaderBoard = new Intent(NotificationActivity.this, DashboardActivity.class);
        intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //extra flag indicate that we are navigating with drawer
        startActivity(intentLeaderBoard);
        finish();
    }
}
