package com.example.lenovo.myapplication;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.os.Environment;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class MyFragment extends Fragment {
    private ImageView avatar;
    private Button information, setting, feedback, report;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CROP_REQUEST_CODE=102;

    private File mTmpFile;
    private File mCropImageFile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        information = (Button) view.findViewById(R.id.information);
        setting = (Button) view.findViewById(R.id.setting);
        feedback = (Button) view.findViewById(R.id.feedback);
        report = (Button) view.findViewById(R.id.report);
//        avatar = (ImageView) view.findViewById(R.id.avatar);
        information.setOnClickListener(new informationOnClick());
        setting.setOnClickListener(new settingOnClick());
        feedback.setOnClickListener(new feedbackOnClick());
        report.setOnClickListener(new reportOnClick());
//        avatar.setOnClickListener(new avatarOnClick());
        return view;
    }

    class informationOnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent;
            intent = getActivity().getIntent();
            String name = intent.getStringExtra("name");
            intent = new Intent(getActivity(),MyInformation.class);
            intent.putExtra("name", name);
            startActivityForResult(intent, 1);
        }
    }

    class settingOnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(getActivity(), Setting.class);
            String name = getActivity().getIntent().getStringExtra("name");
            intent.putExtra("name", name);
            startActivityForResult(intent, 1);
        }
    }

    class feedbackOnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(getActivity(), Feedback.class);
            String name = getActivity().getIntent().getStringExtra("name");
            intent.putExtra("name", name);
            startActivityForResult(intent, 1);
        }
    }

    class reportOnClick implements View.OnClickListener {
        public void onClick(View v) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.fg_main,new AnalyzeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
//            Intent intent;
//            intent = new Intent(getActivity(), ContentActivity.class);
//            String name = getActivity().getIntent().getStringExtra("name");
//            intent.putExtra("name", name);
//            startActivityForResult(intent, 1);

        }
    }

//    class avatarOnClick implements View.OnClickListener {
//        public void onClick(View v) {
//            gallery();
//        }
//    }

//
//    private void gallery() {
//        //打开图库
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
//
////    private File getmTmpFile() throws IOException {
////        File dir = null;
////        if(TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
////            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
////        }
////        return File.createTempFile("IMG_", ".jpg", dir);
////    }
////    private void camera(){
////        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        if (cameraIntent.resolveActivity(this.getPackageManager()) != null){
////            try{
////                //mTmpFile = FileUtils.createTmpFile(this);
////                mTmpFile = getmTmpFile();
////                Log.d("tmptile", mTmpFile.toString());
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            if (mTmpFile != null && mTmpFile.exists()){
////                Uri pictureUri;
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
////                    ContentValues contentValues = new ContentValues(1);
////                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
////                    pictureUri = getContentResolver()
////                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
////                }else {
////                    pictureUri = Uri.fromFile(mTmpFile);
////                }
////                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
////                startActivityForResult(cameraIntent, REQUEST_CAMERA);
////            }
////        }else {
////            Toast.makeText(getBaseContext(), "没有系统相机", Toast.LENGTH_SHORT).show();
////        }
////    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
////            case REQUEST_CAMERA:
////                if (resultCode == RESULT_OK){
////                    crop(mTmpFile.getAbsolutePath());
////                }else {
////                    delete(mTmpFile);
////                }
////                break;
//            case GALLERY_REQUEST_CODE:
//                if (resultCode == RESULT_OK && data != null) {
//                    String imagePath = handleImage(data);
//                    crop(imagePath);
//                }
//                break;
//            case CROP_REQUEST_CODE:
//                if (resultCode == RESULT_OK) {
//                    avatar.setImageURI(Uri.fromFile(mCropImageFile));
//                } else {
//                    delete(mCropImageFile);
//                }
//                break;
//        }
//    }
//
//    private void crop(String imagePath) {
//        //mCropImageFile = FileUtils.createTmpFile(getBaseContext());
//        mCropImageFile = getmCropImageFile();
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
//        intent.putExtra("crop", true);
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 500);
//        intent.putExtra("outputY", 500);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile));
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, CROP_REQUEST_CODE);
//    }
//
//    private File getmCropImageFile() {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            //File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"temp.jpg");
//            File file = new File(getActivity().getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
//            return file;
//        }
//        return null;
//    }
//
//    public Uri getImageContentUri(File imageFile) {
//        String filePath = imageFile.getAbsolutePath();
//        Cursor cursor = getActivity().getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Images.Media._ID},
//                MediaStore.Images.Media.DATA + "=? ",
//                new String[]{filePath}, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor
//                    .getColumnIndex(MediaStore.MediaColumns._ID));
//            Uri baseUri = Uri.parse("content://media/external/images/media");
//            return Uri.withAppendedPath(baseUri, "" + id);
//        } else {
//            if (imageFile.exists()) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, filePath);
//                return getActivity().getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                return null;
//            }
//        }
//    }
//
//    private String handleImage(Intent data) {
//        Uri uri = data.getData();
//        String imagePath = null;
//        if (Build.VERSION.SDK_INT >= 19) {
//            if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
//                String docId = DocumentsContract.getDocumentId(uri);
//                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                    String id = docId.split(":")[1];
//                    String selection = MediaStore.Images.Media._ID + "=" + id;
//                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
//                            "content://downloads/public_downloads"), Long.valueOf(docId));
//                    imagePath = getImagePath(contentUri, null);
//                }
//            } else if ("content".equals(uri.getScheme())) {
//                imagePath = getImagePath(uri, null);
//            }
//        } else {
//            imagePath = getImagePath(uri, null);
//        }
//        return imagePath;
//    }
//
//    private String getImagePath(Uri uri, String seletion) {
//        String path = null;
//        Cursor cursor = getActivity().getContentResolver().query(uri, null, seletion, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    private void delete(File file) {
//        while (file != null && file.exists()) {
//            boolean success = file.delete();
//            if (success) {
//                file = null;
//            }
//        }
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == GALLERY_REQUEST_CODE) {
//            if (data == null) {
//                return;
//            }
//            Uri uri;
//            uri = data.getData();
//            Uri fileUri = convertUri(uri);
//            startImageZoom(fileUri);
//        }
//        else if (requestCode == CROP_REQUEST_CODE)
//        {
//            if(data==null)
//            {
//                return;
//            }
//            Bundle extras =data.getExtras();
//            Bitmap bm =extras.getParcelable("data");
//           avatar.setImageBitmap(bm);
//        }
//    }
////将content类型的uri转换为File类型的uri
//    public Uri convertUri(Uri uri) {
//        InputStream is = null;
//        try {
//            is = getActivity().getContentResolver().openInputStream(uri);
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//            return saveBitmap(bitmap);
//        }catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//            return null;
//    } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public  Uri saveBitmap(Bitmap bm)
//    {
//        File tmpDir =new File(Environment.getExternalStorageDirectory()+"/com.jikexueyuan.avater");
//        if(!tmpDir.exists())
//        {
//            tmpDir.mkdir(); //若该路径不存在，创建一个文件夹使该路径可用
//        }
//        //保存图像文件
//        File img =new File(tmpDir.getAbsolutePath()+"avater.png");
//        //获取该文件的输出流
//        try {
//            FileOutputStream fos =new FileOutputStream(img);
//            //将图片的数据写入该输出流中,第一个参数为压缩格式，第二个为图片质量，第三个输出流
//            bm.compress(Bitmap.CompressFormat.PNG,85,fos);
//            fos.flush();//输出流刷新
//            fos.close();//关闭输出流
//            return Uri.fromFile(img);//从写入的文件中产生一个File类型的uri作为返回值返回
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public void startImageZoom(Uri uri)
//    {
//        Intent intent =new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri,"image/*");//数据通过uri传给图像裁剪界面，类型为image
//        intent.putExtra("crop","true");//为可裁剪
//        intent.putExtra("aspectX",1);//裁剪比例
//        intent.putExtra("aspectY",1);
//        intent.putExtra("outputX",150);//裁剪宽高
//        intent.putExtra("outputY",150);
//        intent.putExtra("return-data",true);
//        startActivityForResult(intent,CROP_REQUEST_CODE);
//
//    }

}
