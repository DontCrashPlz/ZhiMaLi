package com.zhimali.zheng.module_mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zheng.zchlibrary.apps.BaseActivity;
import com.zheng.zchlibrary.utils.DensityUtil;
import com.zheng.zchlibrary.utils.FileUtils;
import com.zheng.zchlibrary.utils.LogUtil;
import com.zheng.zchlibrary.utils.PhotoUtils;
import com.zheng.zchlibrary.widgets.progressDialog.ProgressDialog;
import com.zhimali.zheng.R;
import com.zhimali.zheng.apps.MyApplication;
import com.zhimali.zheng.http.HttpUtils;
import com.zhimali.zheng.http.Network;
import com.zhimali.zheng.http.ResponseTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2018/4/19.
 */

public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

//    private final int requestCode_head= 0x01;
    private final int requestCode_name= 0x02;
    private final int requestCode_mobile= 0x03;
//    public static final int resultCode_head= 0x04;
    public static final int resultCode_name= 0x05;
    public static final int resultCode_mobile= 0x06;
//    public static final String DATA_TAG_HEAD= "head";
    public static final String DATA_TAG_NAME= "name";
    public static final String DATA_TAG_MOBILE= "mobile";

    private final int REQUEST_CODE_TAKE_PICTURE = 1001;// 拍照
    private final int REQUEST_CODE_PICK_PICTURE = 1002;// 相册选择
    private final int REQUEST_CODE_PHOTO_RESULT = 1003;// 结果

    private ImageView mBackBtn;
    private TextView mTitleTv;
    private TextView mFuncationTv;

    private RelativeLayout mHeadRly;
    private RelativeLayout mNameRly;
    private RelativeLayout mPhoneRly;

    private CircleImageView mUserHeadCiv;
    private TextView mNameTv;
    private TextView mPhoneTv;

    private String mPhotoName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initUI();
    }

    @Override
    public void initProgress() {
        mProgressDialog= new ProgressDialog(getRealContext());
        mProgressDialog.setLabel("正在上传..");
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearNetWork();
            }
        });
    }

    private void initUI() {
        mBackBtn= findViewById(R.id.toolbar_back);
        mBackBtn.setOnClickListener(this);
        mTitleTv= findViewById(R.id.toolbar_text);
        mTitleTv.setText("帐号设置");
        mFuncationTv= findViewById(R.id.toolbar_funcation);
        mFuncationTv.setVisibility(View.GONE);

        mHeadRly= findViewById(R.id.user_detail_rly_head);
        mHeadRly.setOnClickListener(this);
        mNameRly= findViewById(R.id.user_detail_rly_name);
        mNameRly.setOnClickListener(this);
        mPhoneRly= findViewById(R.id.user_detail_rly_phone);
        mPhoneRly.setOnClickListener(this);

        mUserHeadCiv= findViewById(R.id.user_detail_civ_head);
        Glide.with(this)
                .load(MyApplication.appUser.getAvatar())
                .asBitmap()
                .placeholder(R.mipmap.yonghu)
                .error(R.mipmap.yonghu)
                .into(mUserHeadCiv);
        mNameTv= findViewById(R.id.user_detail_tv_name);
        mNameTv.setText(MyApplication.appUser.getNickname());
        mPhoneTv= findViewById(R.id.user_detail_tv_phone);
        mPhoneTv.setText(MyApplication.appUser.getMobile());
    }

    @Override
    public void onClick(View v) {
        int vId= v.getId();
        switch (vId){
            case R.id.toolbar_back:{
                finish();
                break;
            }
            case R.id.user_detail_rly_head:{
//                showSelectMenu();
                addNetWork(Network.getInstance()
                        .changeHead(MyApplication.appToken, getRealContext().getExternalFilesDir("")+"/test.jpeg")
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(ResponseTransformer.<String>handleResult())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dismissProgressDialog();
                                showShortToast("头像上传成功");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dismissProgressDialog();
                                showShortToast(HttpUtils.parseThrowableMsg(throwable));
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showProgressDialog();
                            }
                        }));
                break;
            }
            case R.id.user_detail_rly_name:{
                startActivityForResult(
                        new Intent(getRealContext(), NameSetActivity.class),
                        requestCode_name);
                break;
            }
            case R.id.user_detail_rly_phone:{
                startActivityForResult(
                        new Intent(getRealContext(), BindPhoneActivity.class),
                        requestCode_mobile);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode== requestCode_head && resultCode== resultCode_head){
//
//        }
        if (requestCode== requestCode_name && resultCode== resultCode_name){
            mNameTv.setText(data.getStringExtra(DATA_TAG_NAME));
        }
        if (requestCode== requestCode_mobile && resultCode== resultCode_mobile){
            mPhoneTv.setText(data.getStringExtra(DATA_TAG_MOBILE));
        }
        if (requestCode == REQUEST_CODE_PICK_PICTURE) {//选择图片页返回
            if (resultCode == Activity.RESULT_OK) {

                // 构造裁剪输出路径
                mPhotoName = PhotoUtils.createJPEGTempFileName();
                File picture = new File(getRealContext().getExternalFilesDir(""), mPhotoName);

                if (Build.VERSION.SDK_INT < 19) {
                    if (data != null) {
                        // 获取源图片路径
                        Uri uri = data.getData();
                        startPhotoZoom(uri, Uri.fromFile(picture));
                    }
                } else {
                    // 获取源图片路径
                    Uri uri = data.getData();
                    String thePath = PhotoUtils.getPath(this, uri);
                    Uri resUri = Uri.fromFile(new File(thePath));
                    startPhotoZoom(resUri, Uri.fromFile(picture));
                }
            }
        }else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {//照相界面返回
            if (resultCode == Activity.RESULT_OK) {
                File picture = new File(getRealContext().getExternalFilesDir(""), mPhotoName);

                if (picture.exists()) {
                    Uri uri = Uri.fromFile(picture);
                    startPhotoZoom(uri, uri);
                }
            }

        } else if (requestCode == REQUEST_CODE_PHOTO_RESULT) {//裁剪之后返回的结果
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    final String photoPath= getRealContext().getExternalFilesDir("") + "/" + mPhotoName;
                    LogUtil.d("头像路径：", photoPath);
                    addNetWork(Network.getInstance().changeHead(MyApplication.appToken, photoPath)
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(ResponseTransformer.<String>handleResult())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    dismissProgressDialog();
                                    showShortToast("头像上传成功");
                                    Glide.with(getRealContext())
                                            .load(photoPath)
                                            .asBitmap()
                                            .placeholder(R.mipmap.yonghu)
                                            .error(R.mipmap.yonghu)
                                            .into(mUserHeadCiv);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    dismissProgressDialog();
                                    showShortToast(HttpUtils.parseThrowableMsg(throwable));
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {

                                }
                            }, new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
                                  showProgressDialog();
                                }
                            }));
                }
            }
        }
    }

    /**
     * 弹出图片选择方式菜单
     */
    private void showSelectMenu(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_choose_icon_menu, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(this, R.style.dialog_no_title);
        dialog.setContentView(view);
        dialog.show();

        // 拍照按钮监听
        view.findViewById(R.id.choose_icon_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                //构造输出文件路径
                mPhotoName = PhotoUtils.createJPEGTempFileName();
                File picture = new File(getRealContext().getExternalFilesDir(""), mPhotoName);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            }
        });

        // 从相册选取按钮监听
        view.findViewById(R.id.choose_icon_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用Intent调用系统相册或者文件管理器 获取内容
                dialog.dismiss();

                //构造输出文件路径
                mPhotoName = PhotoUtils.createJPEGTempFileName();
                File picture = new File(getRealContext().getExternalFilesDir(""), mPhotoName);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                startActivityForResult(intent, REQUEST_CODE_PICK_PICTURE);
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri, Uri output) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        /** 这里定义 */
//        int width = ScreenUtils.getScreenWidth(this);
        int width = DensityUtil.dp2px(this, 60);
//        if (mUserHeadCiv != null) {
//            width = mUserHeadCiv.getWidth();
//        } else {
//            width = DensityUtil.dp2px(this, 65);
//        }

        int height = DensityUtil.dp2px(this, 60);
//        if (mUserHeadCiv != null) {
//            height = mUserHeadCiv.getHeight();
//        } else {
//            height = DensityUtil.dp2px(this, 65);
//        }

        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        // 设置图片输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", "true");
        intent.putExtra("return-data", "true");

        startActivityForResult(intent, REQUEST_CODE_PHOTO_RESULT);
    }

}
