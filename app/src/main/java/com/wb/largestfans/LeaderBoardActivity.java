package com.wb.largestfans;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.wb.largestfans.adapter.JoggingDetailAdapter;
import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.retrofit.apimodel.GetLeaderBoardResponse;
import com.wb.largestfans.retrofit.apimodel.LeaderBoard;
import com.wb.largestfans.retrofit.apimodel.Monthly;
import com.wb.largestfans.retrofit.apimodel.MonthlyLeaderBoard;
import com.wb.largestfans.utility.Utility;

import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener , View.OnClickListener {

    private RecyclerView mRecyclerView = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        TextView toolbar_title2 = (TextView)findViewById(R.id.toolbar_title2);
        toolbar_title2.setText("Leader Board");
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
                Intent intentLeaderBoard = new Intent(LeaderBoardActivity.this, DashboardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
            }
        });

        mContext = this;
        // setUpToolBar("LEADERBOARD");
        mRecyclerView = findViewById(R.id.recycler_view);
        if (Utility.isConnectedToInternet(LeaderBoardActivity.this)) {
            WebServiceManager.getLeaderBoardData(this, SharedPrefs.getString(this, "mobile"), new ResponseCallBack() {
                @Override
                public void success(Object object) {
                    if (object instanceof GetLeaderBoardResponse) {
                        GetLeaderBoardResponse getLeaderBoardResponse = (GetLeaderBoardResponse) object;
                        MonthlyLeaderBoard leaderBoardList = getLeaderBoardResponse.getData().getMonthlyLeaderBoard();
                        JoggingDetailAdapter mAdapter = new JoggingDetailAdapter(mContext, leaderBoardList.getMonthly());
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }

                @Override
                public void failure(String message) {

                }
            });
        }else {
            Utility.delertAlert(LeaderBoardActivity.this, getString(R.string.no_internet_connection));
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

        }
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menuleaderboard);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_one:
                Intent intentLeaderBoard = new Intent(LeaderBoardActivity.this, DashboardActivity.class);
                intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoard);
                finish();
                return true;

            case R.id.menu_two:
                Intent intentLeaderBoardGallery = new Intent(LeaderBoardActivity.this, GalleryActivity.class);
                intentLeaderBoardGallery.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentLeaderBoardGallery);
                finish();
                return true;
            case R.id.menu_three:
                SharedPrefs.clearTag(LeaderBoardActivity.this, "isLogin");
                Intent intentMerchantListJoinToday = new Intent(LeaderBoardActivity.this, LoginActivity.class);
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

        Intent intentLeaderBoard = new Intent(LeaderBoardActivity.this, DashboardActivity.class);
        intentLeaderBoard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //extra flag indicate that we are navigating with drawer
        startActivity(intentLeaderBoard);
        finish();
    }
}
