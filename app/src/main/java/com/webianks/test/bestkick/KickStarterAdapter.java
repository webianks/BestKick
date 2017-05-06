package com.webianks.test.bestkick;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class KickStarterAdapter extends RecyclerView.Adapter<KickStarterAdapter.VH> {

    private Context context;
    private List<KickProject> projectsList;

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
        holder.tvPleadge.setText(projectsList.get(position).getPleadge());
        holder.tvBackers.setText(projectsList.get(position).getBackers());
        holder.tvNoOfDaysTOGo.setText(projectsList.get(position).getNoOfDaysToGo());

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

        VH(View itemView) {
            super(itemView);

            tvProjectName = (TextView) itemView.findViewById(R.id.projectName);
            tvPleadge = (TextView) itemView.findViewById(R.id.pleadge);
            tvBackers = (TextView) itemView.findViewById(R.id.backers);
            tvNoOfDaysTOGo = (TextView) itemView.findViewById(R.id.noOfDaysToGo);

        }
    }
}
