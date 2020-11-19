package com.wb.largestfans.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wb.largestfans.R;
import com.wb.largestfans.retrofit.apimodel.LeaderBoard;
import com.wb.largestfans.retrofit.apimodel.Monthly;

import java.util.List;

/**
 * Created by vimleshn on 9/11/2017.
 * description this adapter is use to bind jogging data
 */

public class JoggingDetailAdapter extends RecyclerView.Adapter<JoggingDetailAdapter.JoggingDetailViewHolder> {

    private List<Monthly> leaderBoardList;

    /**
     * uset to initialte instance of object
     * this class is use to create view holder for view
     */

    public class JoggingDetailViewHolder extends RecyclerView.ViewHolder {

        final TextView tvRankValue;
        final TextView tvUserNameValue;
       // final TextView tvRunsValue;
        final TextView tvScoreValue;


        JoggingDetailViewHolder(View view) {
            super(view);

            tvRankValue = view.findViewById(R.id.tvRankValue);
            tvUserNameValue = view.findViewById(R.id.tvUserNameValue);
           // tvRunsValue = view.findViewById(R.id.tvRunsValue);
            tvScoreValue = view.findViewById(R.id.tvScoreValue);


        }
    }

    /**
     * uset to initialte instance of object
     *
     * @param context instance of parent view.
     */

    public JoggingDetailAdapter(Context context, List<Monthly> leaderBoardList) {
        Context mContext = context;
        this.leaderBoardList = leaderBoardList;
    }

    /**
     * Use to attach view holder with view
     *
     * @param parent   instance of parent view.
     * @param viewType type of view.
     * @return instance of view holder
     */


    @NonNull
    @Override
    public JoggingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_row, parent, false);
        return new JoggingDetailViewHolder(itemView);
    }

    /**
     * Use to attach view holder with view
     *
     * @param holder   instance of parent view.
     * @param position type of view.
     */

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JoggingDetailViewHolder holder, int position) {

        try {
            holder.tvRankValue.setText("" + leaderBoardList.get(position).getMemMonthRank());
            holder.tvUserNameValue.setText(""+leaderBoardList.get(position).getMemShopName());
         //   holder.tvRunsValue.setText("" + leaderBoardList.get(position).getMemPoint());
            try {
                //   String Date = Utility.getFormatedDate(mGetPlayerPointHistoryUserInfoList.get(position).getRunningDateTime(), "yyy-MM-dd'T'HH:mm:ss", "EEE, d MMM yyyy");
                holder.tvScoreValue.setText("" + leaderBoardList.get(position).getMemMonthTargetPercentage()+"%");
                // String time = Utility.getFormatedDate(mGetPlayerPointHistoryUserInfoList.get(position).getRunningDateTime(), "yyy-MM-dd'T'HH:mm:ss", "HH:mm:ss");
                //  holder.mTvTimeValue.setText("");
                //  holder.mTvJPointValue.setText(Utility.formatTwodigitAfterDecimal(mGetPlayerPointHistoryUserInfoList.get(position).getJPoint()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Use to attach view holder with view
     *
     * @return size of contact list array
     */

    @Override
    public int getItemCount() {
        return (leaderBoardList==null)?0:leaderBoardList.size();
    }
}