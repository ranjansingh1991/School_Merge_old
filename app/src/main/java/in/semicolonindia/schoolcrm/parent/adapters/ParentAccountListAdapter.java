package in.semicolonindia.schoolcrm.parent.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.activities.LoginActivity;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.dialogs.PasswordChangeDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.parent.ParentHomeActivity;
import in.semicolonindia.schoolcrm.parent.beans.AccountBean;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pUploadUserInfoURL;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentAccountListAdapter extends RecyclerView.Adapter<ParentAccountListAdapter.ViewHolder> {

    public static CircleImageView civUserPic;
    private static EditText etName;
    private static EditText etEmail;
    private static EditText etPhone;
    private static EditText etBirthday;
    private static RadioButton rbMale;
    private static RadioButton rbFemale;
    private static EditText etAddress;
    private final int CAMERA = 0;
    private final int GALLERY = 1;
    private Activity activity;
    private Uri mImageCaptureUri;
    private AlertDialog alert;
    private AccountBean mAccountBean;


    public ParentAccountListAdapter(Activity activity, AccountBean mAccountBean) {
        this.activity = activity;
        this.mAccountBean = mAccountBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        ViewHolder mViewHolder = null;
        switch (viewType) {
            case 0:
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_account_list_header, parent, false);
                mViewHolder = new ViewHolder(rootView, viewType);
                break;

            case 1:
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_account_list_body_items, parent, false);
                mViewHolder = new ViewHolder(rootView, viewType);
                break;

            case 2:
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_account_list_control_items, parent, false);
                mViewHolder = new ViewHolder(rootView, viewType);
                break;
        }
        return mViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        final Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        switch (position) {
            case 0:
                holder.tvUserFullName.setTypeface(appFontRegular);
                holder.btnEditProfile.setTypeface(appFontRegular);
                holder.tvUserFullName.setText(mAccountBean.getName());
                Picasso.with(activity).load(mAccountBean.getImageURL()).into(civUserPic);
                holder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PasswordChangeDialog mPasswordChangeDialog = new PasswordChangeDialog(activity, R.style.DialogSlideAnim);
                        mPasswordChangeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mPasswordChangeDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mPasswordChangeDialog.show();
                    }
                });
                holder.imgUploadNewPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagePopUp();
                    }
                });
                break;
            case 1:
                etName.setTypeface(appFontLight);
                etEmail.setTypeface(appFontLight);
                etPhone.setTypeface(appFontLight);
                etBirthday.setTypeface(appFontLight);
                holder.tvGenderLabel.setTypeface(appFontLight);
                rbMale.setTypeface(appFontLight);
                rbFemale.setTypeface(appFontLight);
                etAddress.setTypeface(appFontLight);
                etName.setText(mAccountBean.getName());
                etEmail.setText(mAccountBean.getEmail());
                etPhone.setText(mAccountBean.getPhone());
                etBirthday.setText(mAccountBean.getBirthday());
                etAddress.setText(mAccountBean.getAddress());
                switch (mAccountBean.getGender().toLowerCase()) {
                    case "male":
                        rbMale.setChecked(true);
                        break;
                    case "female":
                        rbFemale.setChecked(true);
                        break;
                }
                break;
            case 2:
                holder.btnCancel.setTypeface(appFontRegular);
                holder.btnSubmit.setTypeface(appFontRegular);
                holder.btnLogout.setTypeface(appFontRegular);
                holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadUserInfo();
                    }
                });
                holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Here when i clicked on cancel button activity will come in HomeActivity.....
                        activity.startActivity(new Intent(activity, ParentHomeActivity.class));
                        activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        activity.finish();
                    }
                });
                holder.btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PreferencesManager(activity).setLogedIn(false);
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                        activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    }
                });
                break;
        }
    }

    private void uploadUserInfo() {

        final ProgressDialog mProgressDialog = new ProgressDialog(activity, activity.getApplicationContext()
                .getResources().getString(R.string.please_wait));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, pUploadUserInfoURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                if (jsonObject.getString("update_status").equalsIgnoreCase("success"))
                                    Toast.makeText(activity, "Updated successfully", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(activity, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                                // dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(activity, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
                mProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("tag", "update_user_info");
                params.put("login_type", "parent");
                params.put("login_user_id", new PreferencesManager(activity).getUserID());
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    private void imagePopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.student_profile_pic_change_dialog, null);
        builder.setView(dialogView);
        final TextView tvCameraPPCD = (TextView) dialogView.findViewById(R.id.tvCameraPPCD);
        final TextView tvGalleryPPCD = (TextView) dialogView.findViewById(R.id.tvGalleryPPCD);
        tvCameraPPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageCaptureUri = Uri.parse("file:///sdcard/photo.jpg");
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                activity.startActivityForResult(intent, CAMERA);
                alert.dismiss();
            }
        });

        tvGalleryPPCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(i, GALLERY);
                alert.dismiss();
            }
        });

        alert = builder.create();
        alert.show();
    }

    private void uploadToServer() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUploadNewPic;
        TextView tvUserFullName;
        RadioGroup rgGender;
        TextView tvGenderLabel;
        Button btnEditProfile;
        Button btnCancel;
        Button btnSubmit;
        Button btnLogout;

        public ViewHolder(View itemView, int nPosition) {
            super(itemView);
            switch (nPosition) {
                case 0:
                    civUserPic = (CircleImageView) itemView.findViewById(R.id.civUserPic);
                    imgUploadNewPic = (ImageView) itemView.findViewById(R.id.imgUploadNewPic);
                    tvUserFullName = (TextView) itemView.findViewById(R.id.tvUserFullName);
                    btnEditProfile = (Button) itemView.findViewById(R.id.btnEditProfile);
                    break;
                case 1:
                    etName = (EditText) itemView.findViewById(R.id.etName);
                    etEmail = (EditText) itemView.findViewById(R.id.etEmail);
                    etPhone = (EditText) itemView.findViewById(R.id.etPhone);
                    etBirthday = (EditText) itemView.findViewById(R.id.etBirthday);
                    tvGenderLabel = (TextView) itemView.findViewById(R.id.tvGenderLabel);
                    rgGender = (RadioGroup) itemView.findViewById(R.id.rgGender);
                    rbMale = (RadioButton) itemView.findViewById(R.id.rbMale);
                    rbFemale = (RadioButton) itemView.findViewById(R.id.rbFemale);
                    etAddress = (EditText) itemView.findViewById(R.id.etAddress);
                    break;
                case 2:
                    btnCancel = (Button) itemView.findViewById(R.id.btnCancel);
                    btnSubmit = (Button) itemView.findViewById(R.id.btnSubmit);
                    btnLogout = (Button) itemView.findViewById(R.id.btnLogout);
                    break;
            }
        }
    }

}