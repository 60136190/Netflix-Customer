package com.example.moviettn.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.fragments.HomeFragment;
import com.example.moviettn.model.Comment;
import com.example.moviettn.model.request.CommentRequest;
import com.example.moviettn.model.response.CommentResponse;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommentFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<Comment> mCommentList;

    public ListCommentFilmAdapter(Context mContext, List<Comment> mCommentList) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);

        if (comment.getUser() == null) {
            ((ItemViewHolder)holder).constraintLayout.setVisibility(View.GONE);
        } else {
            String idUser = comment.getUser().getId();
            String strComment = comment.getContent();
            String nameOfUser = comment.getUser().getFullname();
            String strImgUser = comment.getUser().getImage().getUrl();
            String strIdComment = comment.getId();
            String strIdFilm = comment.getFilm();
            String strDate = comment.getCreatedAt();

            Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                    StoreUtil.get(mContext, "Authorization"));
            proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    String idUserProfile = response.body().getUser().getId();
                    if (idUserProfile.equals(idUser)) {
                        ((ItemViewHolder) holder).imgEdit.setVisibility(View.VISIBLE);
                        ((ItemViewHolder) holder).imgDelete.setVisibility(View.VISIBLE);
                    } else {
                        ((ItemViewHolder) holder).imgEdit.setVisibility(View.INVISIBLE);
                        ((ItemViewHolder) holder).imgDelete.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {

                }
            });
            ((ItemViewHolder) holder).tvComment.setText(strComment);
            ((ItemViewHolder) holder).tvNameOfUser.setText(nameOfUser);
            ((ItemViewHolder) holder).tvDate.setText(strDate);
            Picasso.with(mContext)
                    .load(strImgUser).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).imgUser);

            // edit comment
            ((ItemViewHolder) holder).imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_edit_comment);
                    Window window = dialog.getWindow();
                    if (window == null) {
                        return;
                    }
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams windowAtribute = window.getAttributes();
                    window.setAttributes(windowAtribute);

                    ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
                    EditText edtComment = dialog.findViewById(R.id.edt_edit_comment);
                    Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                    Button btnSave = dialog.findViewById(R.id.btn_save_comment);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    // show dialog
                    dialog.show();

                    // set comment show on dialog
                    edtComment.setText(strComment);

                    // save comment after update on dialog
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String strcmt = edtComment.getText().toString();
                            if (strcmt.isEmpty()) {
                                Toast.makeText(v.getContext(), "Please insert your comment", Toast.LENGTH_SHORT).show();
                            } else {
                                CommentRequest commentRequest = new CommentRequest(strcmt);
                                Call<ResponseDTO> responseDTOCallEditComment = ApiClient.getFilmService().editComment(
                                        StoreUtil.get(v.getContext(), Contants.accessToken)
                                        , strIdComment, commentRequest);
                                responseDTOCallEditComment.enqueue(new Callback<ResponseDTO>() {
                                    @Override
                                    public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                        if (response.isSuccessful()) {
                                            Circle foldingCube = new Circle();
                                            progressBar.setIndeterminateDrawable(foldingCube);
                                            progressBar.setVisibility(View.VISIBLE);
                                            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    int current = progressBar.getProgress();
                                                    if (current >= progressBar.getMax()) {
                                                        current = 0;
                                                    }
                                                    progressBar.setProgress(current + 10);
                                                }

                                                @Override
                                                public void onFinish() {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    dialog.dismiss();

                                                }

                                            };
                                            countDownTimer.start();

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                    }
                                });


                            }
                        }
                    });
                }
            });

            // delete comment
            ((ItemViewHolder) holder).imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_confirm_delete);
                    Window window = dialog.getWindow();
                    if (window == null) {
                        return;
                    }
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams windowAtribute = window.getAttributes();
                    window.setAttributes(windowAtribute);
                    ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
                    Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                    Button btnDelete = dialog.findViewById(R.id.btn_confirm_delete);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    // show dialog
                    dialog.show();

                    // save address after update on dialog
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Call<ResponseDTO> responseDTOCallEditComment = ApiClient.getFilmService().deleteComment(
                                    StoreUtil.get(v.getContext(), Contants.accessToken), strIdComment);
                            responseDTOCallEditComment.enqueue(new Callback<ResponseDTO>() {
                                @Override
                                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                    if (response.isSuccessful()) {
                                        Circle foldingCube = new Circle();
                                        progressBar.setIndeterminateDrawable(foldingCube);
                                        progressBar.setVisibility(View.VISIBLE);
                                        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                int current = progressBar.getProgress();
                                                if (current >= progressBar.getMax()) {
                                                    current = 0;
                                                }
                                                progressBar.setProgress(current + 10);
                                            }

                                            @Override
                                            public void onFinish() {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                dialog.dismiss();
                                                mCommentList.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                            }

                                        };
                                        countDownTimer.start();

                                        // get list comment after add a new comment
                                        Call<CommentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCommentFollowFilm(
                                                StoreUtil.get(mContext, Contants.accessToken), strIdFilm);
                                        listFavoriteFilmResponseCall.enqueue(new Callback<CommentResponse>() {
                                            @Override
                                            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
//                                            listCommentFilmAdapter = new ListCommentFilmAdapter(DetailFilmActivity.this, response.body().getData());
//                                            recyclerViewComment.setAdapter(listCommentFilmAdapter);
//                                            listCommentFilmAdapter.notifyDataSetChanged();
                                                notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFailure(Call<CommentResponse> call, Throwable t) {
                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                }
                            });
                        }
                    });
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        if (mCommentList != null) {
            return mCommentList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgUser;
        public ImageView imgEdit;
        public ImageView imgDelete;
        public TextView tvNameOfUser;
        public TextView tvDate;
        public TextView tvComment;
        public ConstraintLayout constraintLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            tvNameOfUser = itemView.findViewById(R.id.tv_name_of_user);
            tvDate = itemView.findViewById(R.id.tv_date_comment);
            tvComment = itemView.findViewById(R.id.tv_comment);
            constraintLayout = itemView.findViewById(R.id.ct_lis_comment);
        }
    }

}
