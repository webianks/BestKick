package com.webianks.test.bestkick;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class KickStarterAdapter extends RecyclerView.Adapter<KickStarterAdapter.VH> {

    private Context context;
    private List<KickProject> projectsList;
    private ItemClickListener itemClickListener;

    public KickStarterAdapter(Context context, List<KickProject> projectsList) {
        this.context = context;
        this.projectsList = projectsList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.kick_project_single, parent, false);
        return new VH(view);
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.tvProjectName.setText(projectsList.get(position).getTitle());
        holder.tvPleadge.setText(context.getString(R.string.pledged_amount)+projectsList.get(position).getPledge());
        holder.tvBackers.setText(context.getString(R.string.backers_label)+projectsList.get(position).getBackers());
        holder.tvNoOfDaysTOGo.setText(context.getString(R.string.noOfDaysToGoLabel)+projectsList.get(position).getNoOfDaysToGo());

    }


    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return (projectsList != null) ? projectsList.size() : 0;
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
