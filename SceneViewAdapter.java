package com.cosmicnode.hyades.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cosmicnode.hyades.R;
import com.cosmicnode.hyades.core.firestoremodel.GenericState;
import com.cosmicnode.hyades.core.firestoremodel.Scene;
import com.cosmicnode.hyades.core.firestoremodel.Theme;
import com.cosmicnode.hyades.databinding.SceneItemBinding;
import com.cosmicnode.hyades.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit Ostwal on 08-10-2019.
 */
public class SceneViewAdapter extends RecyclerView.Adapter<SceneViewAdapter.SceneViewHolder> {
    private List<Scene> mValues;
    private Context mContext;
    private ItemListener mListener;
    int checkedPosition = -1;
    private List<Theme> listTheme;
    private List<Integer> listPosition;

    public SceneViewAdapter(Context context, List<Scene> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
        listTheme = new ArrayList<>();
        createListTheme();
        listPosition = new ArrayList<>();
    }


    public class SceneViewHolder extends RecyclerView.ViewHolder {
        private Scene scene;
        SceneItemBinding sceneItemBinding;

        public Scene getScene() {
            return scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }


        public SceneViewHolder(SceneItemBinding sceneItemBinding) {
            super(sceneItemBinding.getRoot());
            this.sceneItemBinding = sceneItemBinding;
        }

    }


    @Override
    public SceneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SceneItemBinding sceneItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.scene_item, parent, false);
        return new SceneViewHolder(sceneItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SceneViewHolder Vholder, int position) {


        final Scene item = mValues.get(position);
        Vholder.setScene(item);
        Vholder.sceneItemBinding.sceneTextView.setText(item.getName());

        GenericState gs = item.getGenericState();


        if (gs != null) {

            String mode = gs.getMode();
            Log.d("TAG", mode + "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");

            if (mode != null) {

                Log.d("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>");

                if (mode.equals("color")) {
                    Vholder.sceneItemBinding.sceneImageView.setBackgroundColor(Utils.
                            getColorNew(gs.getColor()));
                } else {

                    String theme = gs.getTheme();

                    switch (theme) {
                        case "Sunset":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.sunset_n);
                            break;
                        case "Candle Light":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.candle_light_n);
                            break;
                        case "DayLight":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.daylight_n);
                            break;
                        case "Soft":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.soft_n);
                            break;
                        case "Sky/Ocenan Blue":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.cool_n);
                            break;
                        case "Night Mode":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.soft_n);
                            break;
                        case "Dimmed":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.dimmed_n);
                            break;
                        case "Lava Red":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.lava_n);
                            break;
                        case "Lavender":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.lavendar_n);
                            break;
                        case "Nature":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.nature_n);
                            break;
                        case "Twilight":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.twilight_blue_n);
                            break;
                        case "Sunflower":
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.sunflower_n);
                            break;
                        default:
                            Vholder.sceneItemBinding.sceneImageView.setImageResource(R.drawable.sunflower_n);
                            break;
                    }

                }
            }

        } else {
            Log.d("TAG", "hello" + position);
        }


        if (checkedPosition == -1) {
            Vholder.sceneItemBinding.sceneSelection.setVisibility(View.GONE);
        } else {
            if (checkedPosition == position) {
                Vholder.sceneItemBinding.sceneSelection.setVisibility(View.VISIBLE);
                Vholder.sceneItemBinding.sceneTextView.setTypeface(Vholder.sceneItemBinding.sceneTextView.getTypeface(), Typeface.BOLD);
            } else {
                Vholder.sceneItemBinding.sceneSelection.setVisibility(View.GONE);
                Vholder.sceneItemBinding.sceneTextView.setTypeface(Vholder.sceneItemBinding.sceneTextView.getTypeface(), Typeface.NORMAL);
            }
        }


        Vholder.sceneItemBinding.relativeLayout.setOnClickListener(v -> {

            if (mListener != null) {

                Log.d("check,pos", checkedPosition + "," + position);

                Vholder.sceneItemBinding.sceneSelection.setVisibility(View.VISIBLE);
                Vholder.sceneItemBinding.sceneTextView.setTypeface(Vholder.sceneItemBinding.sceneTextView.getTypeface(), Typeface.BOLD);

                if (checkedPosition != Vholder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = Vholder.getAdapterPosition();
                }

                mListener.onSceneClick(item, checkedPosition, Vholder.getAdapterPosition());

                Animation animFadein = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);

                Vholder.sceneItemBinding.sceneImageView.startAnimation(animFadein);
            }

        });

        Vholder.sceneItemBinding.sceneOverflow.setOnClickListener(view -> {
            Context wrapper = new ContextThemeWrapper(mContext, R.style.popupStyle);
            PopupMenu popupMenu = new PopupMenu(wrapper, view);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.scene_overflow_edit:
                        if (mListener != null) {
                            mListener.onSceneEdit(item);
                        }
                        break;
                    case R.id.scene_overflow_delete:
                        if (mListener != null) {
                            mListener.onSceneDelete(item, Vholder.getAdapterPosition());
                        }
                        break;
                }
                return false;
            });
            popupMenu.inflate(R.menu.scene_menu);
            popupMenu.show();
        });

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onSceneClick(Scene scene, int prev, int current);

        void onSceneEdit(Scene scene);

        void onSceneDelete(Scene scene, int pos);
    }

    public void removeScene(Scene scene, int pos) {
        //SceneViewAdapter.this.mValues.remove(scene);
        mValues.remove(scene);
        Log.d("TAG", "Hello :: " + scene.getName());
        notifyDataSetChanged();
//        notifyItemRemoved(pos);
//        notifyItemRangeChanged(pos,mValues.size());

    }
}
