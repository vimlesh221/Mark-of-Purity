package com.wb.largestfans;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;



import com.squareup.picasso.Picasso;
import com.wb.largestfans.application.RoyalStagApplication;
import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.constant.AppConstants;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.notification.NotificationActivity;
import com.wb.largestfans.retrofit.apimodel.GetMemberInfoResponse;
import com.wb.largestfans.retrofit.apimodel.MemberInfoData;
import com.wb.largestfans.retrofit.apimodel.PhotoAlbum;
import com.wb.largestfans.retrofit.apimodel.UploadDailyPhotoResponse;
import com.wb.largestfans.retrofit.apimodel.UploadProfilePhotoResponse;
import com.wb.largestfans.utility.RoundedTransformation;
import com.wb.largestfans.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.wb.largestfans.constant.AppConstants.REQUEST_CAMERA_CLICK_profile;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {


    private ImageView ivPhotoOne;
    private ImageView ivPhotoTwo;
    private ImageView ivPhotoThree;
    private ImageView ivPhotoFour;
    private ImageView ivPhotoFive;
    private ImageView ivPhotoSix;
    private CircleImageView activityDashboardProfile;
    private Uri fileUri;
    private Uri fileUrip;
    private TextView tvOverallst;
    private TextView tvTotalScoreValue;
    private TextView tvCurrentRankValue;
    private  TextView tvOverallRankValue;
    private TextView tvStoreName;
    private TextView tvMemberName;
    private TextView tvBottomGallery;
    private TextView tvLeaderBoardBottom;
    private ImageView imgFbIconOne;
    private ImageView imgFbIconTwo;
    private ImageView imgFbIconThree;
    private ImageView imgFbIconFour;
    private ImageView imgFbIconFive;
    private ImageView imgFbIconSix;
    private RelativeLayout relOne;
    private RelativeLayout relTwo;
    private RelativeLayout relThree;
    private RelativeLayout relFour;
    private RelativeLayout relFive;
    private RelativeLayout relSix;
    private List<PhotoAlbum> photoAlbumList;
    /*  ShareDialog shareDialog;
    CallbackManager  callbackManager;*/
    private ShareDialog shareDialog;
    final int PIC_CROP = 15;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        shareDialog = new ShareDialog(this);


//        ShareDialog();
        ivPhotoOne = (ImageView) findViewById(R.id.ivPhotoOne);
        ivPhotoTwo = (ImageView) findViewById(R.id.ivPhotoTwo);
        ivPhotoThree = (ImageView) findViewById(R.id.ivPhotoThree);
        ivPhotoFour = (ImageView) findViewById(R.id.ivPhotoFour);
        ivPhotoFive = (ImageView) findViewById(R.id.ivPhotoFive);
        ivPhotoSix = (ImageView) findViewById(R.id.ivPhotoSix);
        imgFbIconOne = (ImageView) findViewById(R.id.imgFbIconOne);
        imgFbIconOne.setOnClickListener(this);
        imgFbIconTwo = (ImageView) findViewById(R.id.imgFbIconTwo);
        imgFbIconTwo.setOnClickListener(this);
        imgFbIconThree = (ImageView) findViewById(R.id.imgFbIconThree);
        imgFbIconThree.setOnClickListener(this);
        imgFbIconFour = (ImageView) findViewById(R.id.imgFbIconFour);
        imgFbIconFour.setOnClickListener(this);
        imgFbIconFive = (ImageView) findViewById(R.id.imgFbIconFive);
        imgFbIconFive.setOnClickListener(this);
        imgFbIconSix = (ImageView) findViewById(R.id.imgFbIconSix);
        imgFbIconSix.setOnClickListener(this);
        ImageView ivDot = (ImageView) findViewById(R.id.ivDot);
        ivDot.setColorFilter(ContextCompat.getColor(this,R.color.mYellowTextColor),
                PorterDuff.Mode.MULTIPLY);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        relOne = (RelativeLayout) findViewById(R.id.relOne);
        relTwo = (RelativeLayout) findViewById(R.id.relTwo);
        relThree = (RelativeLayout) findViewById(R.id.relThree);
        relFour = (RelativeLayout) findViewById(R.id.relFour);
        relFive = (RelativeLayout) findViewById(R.id.relFive);
        relSix = (RelativeLayout) findViewById(R.id.relSix);
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        tvMemberName = (TextView) findViewById(R.id.tvMemberName);
        ImageView ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLeaderBoard = new Intent(DashboardActivity.this, NotificationActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
            }
        });
        ImageView ivSettingIcon = (ImageView) findViewById(R.id.ivSettingIcon);
        ivSettingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        tvOverallst = (TextView)findViewById(R.id.tvOverallst);
        tvTotalScoreValue = (TextView) findViewById(R.id.tvTotalScoreValue);
        tvCurrentRankValue = (TextView) findViewById(R.id.tvCurrentRankValue);
        tvOverallRankValue =(TextView) findViewById(R.id.tvOverallRankValue);
        Button btnUploadPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        btnUploadPhoto.setOnClickListener(this);
        activityDashboardProfile = (CircleImageView) findViewById(R.id.activityDashboardProfile);
        activityDashboardProfile.setOnClickListener(this);


        //setUpToolBar("");
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Utility.isConnectedToInternet(DashboardActivity.this)) {
            Utility.showProgressDialog("Loading...", DashboardActivity.this);
            WebServiceManager.getMemberInfo(this, SharedPrefs.getString(this, "mobile"), new ResponseCallBack() {
                @Override
                public void success(Object object) {
                    if (object instanceof GetMemberInfoResponse) {
                        GetMemberInfoResponse getMemberInfoResponse = (GetMemberInfoResponse) object;
                        MemberInfoData memberInfoData = getMemberInfoResponse.getData();
                        String memberPhoto = memberInfoData.getMemPhoto();
                        String memberShopName = memberInfoData.getMemShopName();
                        tvStoreName.setText(memberShopName);
                        String memberName = memberInfoData.getMemName();
                        tvMemberName.setText(memberName);
                        String address = memberInfoData.getMemAdddress();
                        String phone = memberInfoData.getMemMobile();
                        String email = memberInfoData.getMemEmail();
                        String point = memberInfoData.getMemMonthTargetPercentage();
                        String rank = memberInfoData.getMemMonthRank();
                        String rankSuffix = memberInfoData.getMemMonthRankSuffix();
                        String monthlycases = memberInfoData.getMemCurrentMonthSaleQty();

                        photoAlbumList = memberInfoData.getPhotoAlbum();

                        if (photoAlbumList.size() > 0) {
                            relOne.setVisibility(View.VISIBLE);
                            ivPhotoOne.setVisibility(View.VISIBLE);

                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(0).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()
                                    .into(ivPhotoOne, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconOne.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relOne.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );


                        } else {
                            ivPhotoOne.setVisibility(View.GONE);
                        }
                        if (photoAlbumList.size() > 1) {
                            relTwo.setVisibility(View.VISIBLE);
                            ivPhotoTwo.setVisibility(View.VISIBLE);
                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(1).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()

                                    .into(ivPhotoTwo, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconTwo.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relTwo.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );
                        } else {
                            ivPhotoTwo.setVisibility(View.GONE);
                        }

                        if (photoAlbumList.size() > 2) {
                            relThree.setVisibility(View.VISIBLE);
                            ivPhotoThree.setVisibility(View.VISIBLE);
                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(2).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()

                                    .into(ivPhotoThree, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconThree.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relThree.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );
                        } else {
                            ivPhotoThree.setVisibility(View.GONE);
                        }
                        if (photoAlbumList.size() > 3) {
                            relFour.setVisibility(View.VISIBLE);
                            ivPhotoFour.setVisibility(View.VISIBLE);
                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(3).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()
                                    .into(ivPhotoFour, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconFour.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relFour.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );
                        } else {
                            ivPhotoFour.setVisibility(View.GONE);
                        }
                        if (photoAlbumList.size() > 4) {
                            relFive.setVisibility(View.VISIBLE);
                            ivPhotoFive.setVisibility(View.VISIBLE);
                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(4).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()

                                    .into(ivPhotoFive, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconFive.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relFive.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );
                        } else {
                            ivPhotoFive.setVisibility(View.GONE);
                        }
                        if (photoAlbumList.size() > 5) {
                            relSix.setVisibility(View.VISIBLE);
                            ivPhotoSix.setVisibility(View.VISIBLE);
                            Picasso.with(DashboardActivity.this)
                                    .load(photoAlbumList.get(5).getZPhotoUrl())
                                    .placeholder(R.mipmap.ic_launcherno)
                                    .error(R.mipmap.logors)
                                    .transform(new RoundedTransformation(10, 0))
                                    .resize(120, 130)
                                    .centerCrop()

                                    .into(ivPhotoSix, new com.squareup.picasso.Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    //Success image already loaded into the view
                                                    imgFbIconSix.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onError() {
                                                    relSix.setVisibility(View.GONE);
                                                    //Error placeholder image already loaded into the view, do further handling of this situation here
                                                }
                                            }
                                    );
                        } else {
                            ivPhotoSix.setVisibility(View.GONE);
                        }
                        RoyalStagApplication.getInstance().setDailyPhotoUrlList(photoAlbumList);
                        tvOverallst.setText(rankSuffix);
                        tvOverallRankValue.setText(rank);
                        tvTotalScoreValue.setText(point+ "%");
                        tvCurrentRankValue.setText(monthlycases);
                        if (memberPhoto != null) {
                            if (!memberPhoto.equals("")) {
                                Picasso.with(DashboardActivity.this).load(memberPhoto).
                                        placeholder(R.mipmap.rank_icon)
                                        .error(R.mipmap.rank_icon)
                                        .resize(100, 100)
                                        .transform(new RoundedTransformation(10, 0))
                                        .into(activityDashboardProfile);
                            }
                        }
                    }
                }

                @Override
                public void failure(String message) {
                    Utility.delertAlert(DashboardActivity.this, message);
                }
            });
        } else {
            Utility.delertAlert(DashboardActivity.this, getString(R.string.no_internet_connection));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activityDashboardProfile:
                try {
                    if (checkCameraAndExternalPermission()) {
                        dispatchTakePictureIntent();
                    } else {
                        giveDynamicPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnUploadPhoto:

                try {
                    if (checkCameraAndExternalPermission()) {
                        dispatchTakePictureIntentProfile();
                    } else {
                        giveDynamicPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //latest comment
               /* boolean resultProfile = checkPermission(DashboardActivity.this);
                if (resultProfile)
                    cameraIntentProfile();*/
                break;

            case R.id.imgFbIconOne:
                if (photoAlbumList.size() > 0) {
                    String imageUrl = photoAlbumList.get(0).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }

                break;
            case R.id.imgFbIconTwo:
                if (photoAlbumList.size() > 1) {
                    String imageUrl = photoAlbumList.get(1).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }
                break;
            case R.id.imgFbIconThree:
                if (photoAlbumList.size() > 2) {
                    String imageUrl = photoAlbumList.get(2).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }
                break;
            case R.id.imgFbIconFour:
                if (photoAlbumList.size() > 3) {
                    String imageUrl = photoAlbumList.get(3).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }
                break;
            case R.id.imgFbIconFive:
                if (photoAlbumList.size() > 4) {
                    String imageUrl = photoAlbumList.get(4).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }
                break;
            case R.id.imgFbIconSix:
                if (photoAlbumList.size() > 5) {
                    String imageUrl = photoAlbumList.get(5).getZPhotoUrl();
                    shareUsingNativeDialog(imageUrl);
                }
                break;
            case R.id.ivBack:
                finish();
                break;


        }
    }




    private void onCaptureImageResultProfile(Intent data) {

        try {
            if (data.getData().getPath() != null) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                File imageFile = new File(data.getData().getPath());

                File compressedImageFile = new Compressor(this).compressToFile(imageFile);
                Log.e("profilepath",""+compressedImageFile.getAbsolutePath());
                if (Utility.isConnectedToInternet(DashboardActivity.this)) {
                    WebServiceManager.uploadProfilePhoto(DashboardActivity.this, SharedPrefs.getString(DashboardActivity.this, "mobile"), compressedImageFile, new ResponseCallBack() {
                        @Override
                        public void success(Object object) {
                            if (object instanceof UploadProfilePhotoResponse) {
                                UploadProfilePhotoResponse uploadProfilePhotoResponse = (UploadProfilePhotoResponse) object;
                                String message = uploadProfilePhotoResponse.getMessage();
                                String url = uploadProfilePhotoResponse.getData().getMemPhoto();
                                Log.e("profilepath22",""+url);
                                Picasso.with(DashboardActivity.this)
                                        .load(url)
                                        .placeholder(R.mipmap.ic_launcherno)
                                        .error(R.mipmap.logors)
                                        .resize(100, 100)
                                        .transform(new RoundedTransformation(10, 0))
                                        .centerCrop()

                                        .into(activityDashboardProfile);


                                Utility.delertAlert(DashboardActivity.this, message);
                            }

                        }

                        @Override
                        public void failure(String message) {
                            Utility.delertAlert(DashboardActivity.this, message);
                        }
                    });
                } else {
                    Utility.delertAlert(DashboardActivity.this, getString(R.string.no_internet_connection));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void onCaptureImageResult() {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            File imageFile = new File(fileUri.getPath());

            File compressedImageFile = new Compressor(this).compressToFile(imageFile);
            if (Utility.isConnectedToInternet(DashboardActivity.this)) {
                WebServiceManager.uploadDailyPhoto(DashboardActivity.this, SharedPrefs.getString(DashboardActivity.this, "mobile"), compressedImageFile, new ResponseCallBack() {
                    @Override
                    public void success(Object object) {
                        if (object instanceof UploadDailyPhotoResponse) {
                            UploadDailyPhotoResponse uploadDailyPhotoResponse = (UploadDailyPhotoResponse) object;
                            final String messageUpload = uploadDailyPhotoResponse.getMessage();
                            String status = uploadDailyPhotoResponse.getStatus();
                            if (status.equals("OK")) {
                                WebServiceManager.getMemberInfo(DashboardActivity.this, SharedPrefs.getString(DashboardActivity.this, "mobile"), new ResponseCallBack() {
                                    @Override
                                    public void success(Object object) {
                                        if (object instanceof GetMemberInfoResponse) {
                                            GetMemberInfoResponse getMemberInfoResponse = (GetMemberInfoResponse) object;
                                            String status = getMemberInfoResponse.getStatus();
                                            String message = getMemberInfoResponse.getMessage();
                                            if (status.equals("OK")) {
                                                Utility.delertAlert(DashboardActivity.this, messageUpload);
                                                MemberInfoData memberInfoData = getMemberInfoResponse.getData();
                                                String memberPhoto = memberInfoData.getMemPhoto();
                                                String memberShopName = memberInfoData.getMemShopName();
                                                tvStoreName.setText(memberShopName);
                                                String memberName = memberInfoData.getMemName();
                                                tvMemberName.setText(memberName);
                                                String address = memberInfoData.getMemAdddress();
                                                String phone = memberInfoData.getMemMobile();
                                                String email = memberInfoData.getMemEmail();
                                                String point = memberInfoData.getMemMonthTargetPercentage();
                                                String rank = memberInfoData.getMemMonthRank();
                                                String rankSuffix = memberInfoData.getMemMonthRankSuffix();
                                                String monthlycases =  memberInfoData.getMemCurrentMonthSaleQty();
                                                photoAlbumList = memberInfoData.getPhotoAlbum();

                                                if (photoAlbumList.size() > 0) {
                                                    relOne.setVisibility(View.VISIBLE);
                                                    ivPhotoOne.setVisibility(View.VISIBLE);

                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(0).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()
                                                            .into(ivPhotoOne, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconOne.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relOne.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );


                                                } else {
                                                    ivPhotoOne.setVisibility(View.GONE);
                                                }
                                                if (photoAlbumList.size() > 1) {
                                                    relTwo.setVisibility(View.VISIBLE);
                                                    ivPhotoTwo.setVisibility(View.VISIBLE);
                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(1).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()

                                                            .into(ivPhotoTwo, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconTwo.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relTwo.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );
                                                } else {
                                                    ivPhotoTwo.setVisibility(View.GONE);
                                                }

                                                if (photoAlbumList.size() > 2) {
                                                    relThree.setVisibility(View.VISIBLE);
                                                    ivPhotoThree.setVisibility(View.VISIBLE);
                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(2).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()

                                                            .into(ivPhotoThree, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconThree.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relThree.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );
                                                } else {
                                                    ivPhotoThree.setVisibility(View.GONE);
                                                }
                                                if (photoAlbumList.size() > 3) {
                                                    relFour.setVisibility(View.VISIBLE);
                                                    ivPhotoFour.setVisibility(View.VISIBLE);
                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(3).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()
                                                            .into(ivPhotoFour, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconFour.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relFour.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );
                                                } else {
                                                    ivPhotoFour.setVisibility(View.GONE);
                                                }
                                                if (photoAlbumList.size() > 4) {
                                                    relFive.setVisibility(View.VISIBLE);
                                                    ivPhotoFive.setVisibility(View.VISIBLE);
                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(4).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()

                                                            .into(ivPhotoFive, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconFive.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relFive.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );
                                                } else {
                                                    ivPhotoFive.setVisibility(View.GONE);
                                                }
                                                if (photoAlbumList.size() > 5) {
                                                    relSix.setVisibility(View.VISIBLE);
                                                    ivPhotoSix.setVisibility(View.VISIBLE);
                                                    Picasso.with(DashboardActivity.this)
                                                            .load(photoAlbumList.get(5).getZPhotoUrl())
                                                            .placeholder(R.mipmap.ic_launcherno)
                                                            .error(R.mipmap.logors)
                                                            .transform(new RoundedTransformation(10, 0))
                                                            .resize(120, 130)
                                                            .centerCrop()

                                                            .into(ivPhotoSix, new com.squareup.picasso.Callback() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            //Success image already loaded into the view
                                                                            imgFbIconSix.setVisibility(View.VISIBLE);
                                                                        }

                                                                        @Override
                                                                        public void onError() {
                                                                            relSix.setVisibility(View.GONE);
                                                                            //Error placeholder image already loaded into the view, do further handling of this situation here
                                                                        }
                                                                    }
                                                            );
                                                } else {
                                                    ivPhotoSix.setVisibility(View.GONE);
                                                }
                                                RoyalStagApplication.getInstance().setDailyPhotoUrlList(photoAlbumList);

                                                tvOverallst.setText(rankSuffix);
                                                tvOverallRankValue.setText(rank);
                                                tvTotalScoreValue.setText(point+ "%");
                                                tvCurrentRankValue.setText(monthlycases);


                                            } else {
                                                Utility.delertAlert(DashboardActivity.this, message);
                                            }
                                        }
                                    }

                                    @Override
                                    public void failure(String message) {
                                        Utility.delertAlert(DashboardActivity.this, message);
                                    }
                                });
                            } else {
                                Utility.delertAlert(DashboardActivity.this, messageUpload);
                            }


                        }

                    }

                    @Override
                    public void failure(String message) {
                        Utility.delertAlert(DashboardActivity.this, message);
                    }
                });
            } else {
                Utility.delertAlert(DashboardActivity.this, getString(R.string.no_internet_connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory().toString() + "/VanguradClaims/");
        // String path =

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                /*Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");*/
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VanguradClaims_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;

    }
    private static File getOutputMediaFilep() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory().toString() + "/VanguradClaimsp/");
        // String path =

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                /*Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");*/
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VanguradClaimsp_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;

    }


    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private Uri getOutputMediaFileUrip() {
        return Uri.fromFile(getOutputMediaFilep());
    }

    private void dispatchTakePictureIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("return-data", true);
        // start the image capture Intent
        startActivityForResult(intent, AppConstants.REQUEST_CAMERA_CLICK_profile);
    }
    private void dispatchTakePictureIntentProfile() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, AppConstants.REQUEST_CAMERA_CLICK);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case AppConstants.REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // fine location grant
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // camera grant
                        if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                            // storage grant
                            // Log.e(">>", ">> all permission are granted.");
                            dispatchTakePictureIntent();
                        } else {
                            showToast(getString(R.string.External_storage_permission_not_grant));
                        }
                    } else {
                        // camera permission
                        showToast(getString(R.string.Camera_and_external_storage_permission_not_grant));
                    }
                } else {
                    showToast(getString(R.string.Permission_not_grant));
                }
                break;
            case AppConstants.REQUEST_CAMERA_Profile:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // fine location grant
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // camera grant
                        if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                            // storage grant
                            // Log.e(">>", ">> all permission are granted.");
                            dispatchTakePictureIntentProfile();

                        } else {
                            showToast(getString(R.string.External_storage_permission_not_grant));
                        }
                    } else {
                        // camera permission
                        showToast(getString(R.string.Camera_and_external_storage_permission_not_grant));
                    }
                } else {
                    showToast(getString(R.string.Permission_not_grant));
                }
                break;
            case AppConstants.REQUEST_ONLY_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // camera grant
                    if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // photo take method
                        dispatchTakePictureIntent();
                    } else {
                        requetExternalStoratePermission();
                    }
                } else {
                    showToast(getString(R.string.Permission_denied_for_camera));
                }
                break;
            case AppConstants.REQUEST_ONLY_CAMERA_profile:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // camera grant
                    if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // photo take method
                        dispatchTakePictureIntentProfile();
                    } else {
                        requetExternalStoratePermissionProfile();
                    }
                } else {
                    showToast(getString(R.string.Permission_denied_for_camera));
                }
                break;
            case AppConstants.REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // External storage grant
                    if (ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        // photo take method
                        dispatchTakePictureIntent();
                    } else {
                        requestCameraPermission();
                    }
                } else {
                    showToast(getString(R.string.Permission_denied_for_write_external_storage));
                }
                break;
            case AppConstants.REQUEST_EXTERNAL_STORAGE_profile:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // External storage grant
                    if (ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        // photo take method
                        dispatchTakePictureIntentProfile();
                    } else {
                        requestCameraPermissionProfile();
                    }
                } else {
                    showToast(getString(R.string.Permission_denied_for_write_external_storage));
                }
                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            switch (requestCode) {
                case AppConstants.REQUEST_CAMERA_CLICK_profile:
                    //  if (data != null) {
                    if (resultCode == RESULT_OK) {

                        // CALL THIS METHOD TO GET THE ACTUAL PATH

                        performCrop(fileUri);
                        //onCaptureImageResult();
                    }
                    break;
                case AppConstants.REQUEST_CAMERA_CLICK:
                    if (resultCode == RESULT_OK) {
                    onCaptureImageResult();
                   // if (resultCode == RESULT_OK) {
                       // onCaptureImageResultProfile(data);

                     //   Bitmap photo = (Bitmap) data.getExtras().get("data");

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                     //   Uri tempUri = getImageUri(getApplicationContext(), photo);

                    }
                    break;
                case PIC_CROP:
                    //  if (data != null) {
                    if (resultCode == RESULT_OK) {
                        onCaptureImageResultProfile(data);
                    }
                    break;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void giveDynamicPermission() {

        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA);
        } else if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requetExternalStoratePermission();
        }

    }
    private void giveDynamicPermissionprofile() {

        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA_Profile);
        } else if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermissionProfile();
        } else if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requetExternalStoratePermissionProfile();
        }
    }

    private boolean checkCameraAndExternalPermission() {
        if (ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /*user : namitas
     * date : 21-06-17
     * description :this method will check the camera request*/
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, AppConstants.REQUEST_ONLY_CAMERA);
    }
    private void requestCameraPermissionProfile() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, AppConstants.REQUEST_ONLY_CAMERA_profile);
    }
    private void requetExternalStoratePermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_EXTERNAL_STORAGE);
    }
    private void requetExternalStoratePermissionProfile() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_EXTERNAL_STORAGE_profile);
    }


    private void showToast(String asd) {
        Toast.makeText(DashboardActivity.this, asd, Toast.LENGTH_LONG).show();
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_one:
                Intent intentLeaderBoard = new Intent(DashboardActivity.this, LeaderBoardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
                return true;

            case R.id.menu_two:
                Intent intentLeaderBoardGallery = new Intent(DashboardActivity.this, GalleryActivity.class);
                intentLeaderBoardGallery.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoardGallery);
                finish();
                return true;
            case R.id.menu_three:
                SharedPrefs.clearTag(DashboardActivity.this, "isLogin");
                Intent intentMerchantListJoinToday = new Intent(DashboardActivity.this, LoginActivity.class);
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

    private static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(fileUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
}
