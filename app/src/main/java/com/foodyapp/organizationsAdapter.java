package com.foodyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class organizationsAdapter extends RecyclerView.Adapter<organizationsAdapter.OrganizationViewHolder> {

    private List<organizationInfo> organizationList;

    public organizationsAdapter(List<organizationInfo> organizationList) {
        this.organizationList = organizationList;
    }


    @Override
    public int getItemCount() {
        return organizationList.size();
    }

    @Override
    public void onBindViewHolder(OrganizationViewHolder organizationViewHolder, int position) {

        organizationInfo ci = organizationList.get(position);
        organizationViewHolder.setData(ci);
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.organization_card, viewGroup, false);

        return new OrganizationViewHolder(itemView);
    }

    public  class OrganizationViewHolder extends RecyclerView.ViewHolder {

        private TextView vName;
        private TextView vAddress;
        private TextView vPhone;
        private ImageButton joinBtn;
        private organizationInfo ci = null;

        public OrganizationViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.latet_title);
            vAddress = (TextView)  v.findViewById(R.id.latet_addressID);
            vPhone = (TextView)  v.findViewById(R.id.latet_phoneID);
            joinBtn = (ImageButton) v.findViewById(R.id.latet_btnID);

            joinBtn.setOnClickListener(view -> {
                organizationList.remove(OrganizationViewHolder.this.ci);
                organizationsAdapter.this.notifyDataSetChanged();
            });
        }

        public void setData(organizationInfo ci){
            this.ci = ci;
            vName.setText(ci.name);
            vAddress.setText(ci.address);
            vPhone.setText(ci.phone);

        }
    }


}
