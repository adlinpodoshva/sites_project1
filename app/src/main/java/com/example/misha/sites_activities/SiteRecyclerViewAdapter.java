package com.example.misha.sites_activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.bumptech.glide.Glide;
import com.example.misha.R;

import java.util.Comparator;
import java.util.List;

public class SiteRecyclerViewAdapter extends RecyclerView.Adapter<SiteRecyclerViewAdapter.ItemViewHolder> {
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View rootView;
        private ImageView imageView;
        private TextView textView;

        public ItemViewHolder(View v) {
            super(v);
            this.rootView = v;
            this.imageView = v.findViewById(R.id.siteItemImageView);
            this.textView = v.findViewById(R.id.siteItemTextView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private final SortedList<eSite> sortedList = new SortedList<>(eSite.class, new SortedList.Callback<eSite>() {
        private final Comparator<eSite> locationNameComparator = new Comparator<eSite>() {
            @Override
            public int compare(eSite s1, eSite s2) {
                return s1.getTitle().compareTo(s2.getTitle());
            }
        };

        @Override
        public int compare(eSite a, eSite b) {
            return this.locationNameComparator.compare(a, b);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(eSite oldItem, eSite newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areItemsTheSame(eSite S1, eSite S2) {
            return S1.equals(S2);
        }
    });

    private final Context context;
    public SiteRecyclerViewAdapter(List<eSite> list, Context context) {
        replaceAll(list);
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.site_item, parent, false);

        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int position) {
        final eSite site = this.sortedList.get(position);
        int siteImageResourceId = this.context.getResources().getIdentifier(site.name().toLowerCase(),
                "drawable", this.context.getPackageName());

        itemViewHolder.textView.setText(site.getTitle());
        Glide.with(this.context).load(siteImageResourceId).into(itemViewHolder.imageView);
        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SiteDisplayActivity.class);
                intent.putExtra(SiteDisplayActivity.SITE_NAME_EXTRA_KEY, site.name());
                context.startActivity(intent);
            }
        });
    }

    public void replaceAll(List<eSite> newItems) {
        this.sortedList.beginBatchedUpdates();

        for (int i = this.sortedList.size() - 1; i >= 0; i--) {
            final eSite site = this.sortedList.get(i);

            if (!newItems.contains(site)) {
                this.sortedList.remove(site);
            }
        }

        this.sortedList.addAll(newItems);
        this.sortedList.endBatchedUpdates();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.sortedList.size();
    }
}