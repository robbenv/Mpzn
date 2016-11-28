package com.mpzn.mpzn.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.code19.library.CipherUtils;
import com.code19.library.ImageUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AccountEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.entity.UploadIconEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.http.MyStringCallBack;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ChangIconActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.btn_add_image)
    Button btnAddImage;

    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            String base64File = encodeBase64File(Environment.getExternalStorageDirectory()+"/mpzn_icon.jpg");  //将文件转为BASE64

            uploadImagePost(base64File);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_chang_icon;
    }

    @Override
    public void initHolder() {
        mImageManager.loadUrlImage(MyApplication.getInstance().mUserMsg.getmIconUrl(),ivImage);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    public void bindListener() {
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Log.e("TAG", "mBitmap"+mBitmap);

                if(mBitmap!=null) {
                    setResult(RESULT_OK, intent);
                }else{
                    setResult(RESULT_CANCELED, intent);
                }
                finish();
            }
        });

    }
    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangIconActivity.this);
        builder.setTitle("添加图片");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Photo_test1", "onClick()__");
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        Log.i("Photo_test1", "onClick()__选择本地图片");
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 240);
        intent.putExtra("outputY", 240);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data)  {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            ivImage.setImageBitmap(mBitmap);//显示图片

            saveBitmapFile(mBitmap);   //保存到本地 路径为Environment.getExternalStorageDirectory()+"/temp_image.jpg"


            //在这个地方可以写上上传该图片到服务器的代码



        }
    }
    protected void uploadImagePost(String base64File){
        Log.e("TAG", "bitmapBase64"+base64File);


        OkHttpUtils.post()
//                .addFile("headimage","temp_image.jpg",Environment
//                        .getExternalStorageDirectory())
                .url(API.UPLOADICON)
                .addParams("token",MyApplication.getInstance().token)
                .addParams("headimage",base64File)
                .build()
                .execute(new MyStringCallBack() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 playShortToast("请检查网络");
                             }

                             @Override
                             public void onResponse(String response, int id) {
                                 Log.e("TAG", "response"+response);
                                 UploadIconEntity uploadIconEntity = new Gson().fromJson(response, UploadIconEntity.class);
                                 if(uploadIconEntity.getCode()==200){
                                     String headimage = uploadIconEntity.getData().getHeadimage();
                                     AccountEntity account = (AccountEntity) CacheUtils.getObject(mContext, "account");
                                     account.setIconurl(headimage);
                                     MyApplication.getInstance().mUserMsg.setmIconUrl(headimage);
                                     playShortToast("上传成功");

                                 }else{
                                     playShortToast("上传失败");
                                 }
                             }

                             @Override
                             public void inProgress(float progress, long total, int id) {

                             }
                         }

                );

    }





    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        Log.e("TAG", "mBitmap"+mBitmap);

        if(mBitmap!=null) {
            intent.putExtra("bitmap", mBitmap);
            setResult(RESULT_OK, intent);
        }else{
            setResult(RESULT_CANCELED, intent);
        }
        super.onBackPressed();
    }



   // Bitmap对象保存为图片文件
    public void saveBitmapFile(Bitmap bitmap){
        File file=new File(Environment.getExternalStorageDirectory()+"/mpzn_icon.jpg");//将要保存图片的路径
        try {
            ImageUtils.bitmap2File(bitmap,file);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            handler.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件转成base64 字符串
     * @param path 文件路径
     * @return  *
     * @throws Exception
     */
    public String encodeBase64File(String path){
        String str="";
        try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            str= Base64.encodeToString(buffer, Base64.DEFAULT);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return str;

    }



}
