package com.epicburger.epicburgerapp;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CaptionedImagesAdapter
        extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions;
    private double[] costs;
    private int[] imageIds;

    private Listener listener;

    public interface Listener {
        void onClick(int position);
    }

    // ViewHolder in RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public CaptionedImagesAdapter(String[] captions, double[] costs, int[] imageIds) {
        this.captions = captions;
        this.costs = costs;
        this.imageIds = imageIds;
    }


    // Inform adapter about count of elements
    @Override
    public int getItemCount() {
        return captions.length;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // Сreate ViewHolder
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    // Connect data with ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (captions[position] != null) {
            CardView cardView = holder.cardView;
            ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
            TextView textView_cost = (TextView) cardView.findViewById(R.id.info_cost);
            TextView textView_text = (TextView) cardView.findViewById(R.id.info_text);

            Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
            imageView.setImageDrawable(drawable);
            imageView.setContentDescription(captions[position]);

            textView_cost.setText("$" + String.valueOf(costs[position]));
            textView_cost.setTypeface(Typeface.DEFAULT_BOLD);
            textView_cost.setTextSize(16);

            textView_text.setText(captions[position]);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });
        } else {
            // Do not fill in the cards
        }
    }
}
