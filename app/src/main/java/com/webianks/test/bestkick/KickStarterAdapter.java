package com.webianks.test.bestkick;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.webianks.test.bestkick.database.KickContract;

import java.util.List;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class KickStarterAdapter extends RecyclerView.Adapter<KickStarterAdapter.VH> {

    private Context context;
    private Cursor dataCursor;
    private ItemClickListener itemClickListener;

    public KickStarterAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.dataCursor = cursor;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.kick_project_single, parent, false);
        return new VH(view);
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {

        dataCursor.moveToPosition(position);


        int title_index = dataCursor.getColumnIndex(KickContract.KickEntry.KICK_TITLE);
        int pledged_index = dataCursor.getColumnIndex(KickContract.KickEntry.KICK_AMT_PLEDGED);
        int backers_index = dataCursor.getColumnIndex(KickContract.KickEntry.KICK_BACKERS);
        int no_of_days_index = dataCursor.getColumnIndex(KickContract.KickEntry.KICK_END_TIME);


        holder.tvProjectName.setText(dataCursor.getString(title_index));
        holder.tvPleadge.setText(context.getString(R.string.pledged_amount)+dataCursor.getInt(pledged_index));
        holder.tvBackers.setText(context.getString(R.string.backers_label)+dataCursor.getString(backers_index));
        holder.tvNoOfDaysTOGo.setText(context.getString(R.string.noOfDaysToGoLabel)+dataCursor.getString(no_of_days_index));

    }


    void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }


    Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvProjectName;
        TextView tvPleadge;
        TextView tvBackers;
        TextView tvNoOfDaysTOGo;
        FrameLayout mainLayout;

        VH(View itemView) {
            super(itemView);

            tvProjectName = (TextView) itemView.findViewById(R.id.projectName);
            tvPleadge = (TextView) itemView.findViewById(R.id.pleadge);
            tvBackers = (TextView) itemView.findViewById(R.id.backers);
            tvNoOfDaysTOGo = (TextView) itemView.findViewById(R.id.noOfDaysToGo);
            mainLayout = (FrameLayout) itemView.findViewById(R.id.main_layout);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (itemClickListener!=null)
                        itemClickListener.itemClicked();
                }
            });

        }
    }

    interface ItemClickListener{
        public void itemClicked();
    }

}
