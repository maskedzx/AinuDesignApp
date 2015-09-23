package com.example.yuuya.ainudesignapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ShowActivity extends Activity implements AdapterView.OnItemClickListener {
    // 要素をArrayListで設定
    private List<String> items = new ArrayList<String>();
    private List<String> imagePaths = new ArrayList<String>();
    private List<Boolean> itemBackGround = new ArrayList<Boolean>();

    private BaseAdapter adapter;

    // タップされた要素の位置
    private int tappedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // /asset/image/以下に image0.jpg ～ image7.jpg を入れています
        // それのパスを取り出す method
        getImagePath();


        // ListViewのインスタンスを生成
        ListView listView = (ListView)findViewById(R.id.listView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // 子要素のレイアウトファイル list_items.xml を activity_main.xml に inflate するためにadapterに引数として渡す
        adapter = new ListViewAdapter(this.getApplicationContext(), R.layout.list_items, items, imagePaths, itemBackGround);

        // ListViewにadapterをセット
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }


    class ViewHolder {
        TextView textView;
        ImageView imageView;
        View itemView;
    }

    class ListViewAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private Bitmap bmp = null;
        private int itemLayoutId;

        public ListViewAdapter(Context context, int itemLayoutId, List<String> items, List<String> imagePaths, List<Boolean> itemBackGround) {
            super();
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.itemLayoutId = itemLayoutId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            // 最初だけ View を inflate して、それを再利用する
            if (convertView == null) {
                // activity_main.xml の ＜ListView .../＞ に list_items.xml を inflate して convertView とする
                convertView = inflater.inflate(itemLayoutId, parent, false);
                // ViewHolder を生成
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                //holder.itemView = (View) convertView.findViewById(R.id.item_view);
                convertView.setTag(holder);
            }
            // holder を使って再利用
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 現在の position にある画像リストのパスをデコード
            bmp = BitmapFactory.decodeFile(imagePaths.get(position));
            // holder の imageView にセット
            holder.imageView.setImageBitmap(bmp);
            // 現在の position にあるファイル名リストを holder の textView にセット
            holder.textView.setText(items.get(position));

            // Cellの背景色を変更
            if(itemBackGround.get(position)){
                holder.itemView.setBackgroundColor(Color.rgb(180, 180, 180));
            }
            else{
                holder.itemView.setBackgroundColor(Color.rgb(255, 255, 255));
            }

            return convertView;
        }

        @Override
        public int getCount() {
            // items の全要素数を返す
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    private void getImagePath() {
        // asset のデータを取り出す
        AssetManager assetManager = getResources().getAssets();

        String[] fileList = null;
        String destPath = null;

        try {
            // assets/image/image0.jpg があるのでフォルダパスをしていする
            fileList = assetManager.list("image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream input;
        FileOutputStream output;


        for(int i=0; i< fileList.length ; i++){
            try{
                input = assetManager.open("image/"+fileList[i]);
                //
                destPath = "/data/data/"+this.getPackageName()+"/" + fileList[i];

                output=new FileOutputStream(destPath);

                int DEFAULT_BUFFER_SIZE = 10240 * 4;
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int n = 0;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                output.close();
                input.close();
            }catch (IOException e) {
                e.printStackTrace();
            }

            items.add(fileList[i]);
            imagePaths.add(destPath);
            itemBackGround.add(false);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id ) {
        String item = items.get(position);
        // 選択された位置を保持する
        setPosition(position);
        alertCheck(item, position);
    }

    private void setPosition(int position){
        tappedPosition = position;
    }
    private int getPosition(){
        return tappedPosition;
    }

    private void alertCheck(String item, int position){
        String[] alert_menu = {"上に移動", "下に移動","背景変更", "削除", "cancel"};

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(item);
        alert.setItems(alert_menu, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                // リストアイテムを選択したときの処理
                // 上に移動
                if(idx == 0){
                    moveAbove();
                }
                // 下に移動
                else if(idx == 1){
                    moveBelow();
                }
                // 背景変更
                else if(idx == 2){
                    changeBackGround();
                }
                // アイテムの削除
                else if(idx == 3){
                    deleteCheck();
                }
                // cancel"
                else{
                    // nothing to do
                }
            }});
        alert.show();
    }

    private void deleteCheck(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // AlertDialogのタイトル設定します
        alertDialogBuilder.setTitle("削除");
        // AlertDialogのメッセージ設定
        alertDialogBuilder.setMessage("本当に削除しますか？");
        // AlertDialogのYesボタンのコールバックリスナーを登録
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem();
                    }
                });
        // AlertDialogのNoボタンのコールバックリスナーを登録
        alertDialogBuilder.setNeutralButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // AlertDialogのキャンセルができるように設定
        alertDialogBuilder.setCancelable(true);

        AlertDialog alertDialog = alertDialogBuilder.create();
        // AlertDialogの表示
        alertDialog.show();
    }

    private void moveAbove(){
        int position = getPosition();
        String temp = null;
        if(position>0){
            temp = items.get(position-1);
            items.set(position-1, items.get(position));
            items.set(position, temp);

            temp = imagePaths.get(position-1);
            imagePaths.set(position-1, imagePaths.get(position));
            imagePaths.set(position, temp);
        }
        else{
            // top
        }

        // ListView の更新
        adapter.notifyDataSetChanged();
    }

    private void moveBelow(){
        int position = getPosition();
        String temp = null;
        if(position < items.size()-1){
            temp = items.get(position+1);
            items.set(position+1, items.get(position));
            items.set(position, temp);

            temp = imagePaths.get(position+1);
            imagePaths.set(position+1, imagePaths.get(position));
            imagePaths.set(position, temp);
        }
        else{
            // last
        }

        // ListView の更新
        adapter.notifyDataSetChanged();
    }

    private void changeBackGround(){
        int position = getPosition();

        boolean ck = itemBackGround.get(position);
        if(ck){
            itemBackGround.set(position, false);
        }
        else{
            itemBackGround.set(position, true);
        }
        // ListView の更新
        adapter.notifyDataSetChanged();
    }
    private void deleteItem(){
        int position = getPosition();
        // それぞれの要素を削除
        items.remove(position);
        imagePaths.remove(position);

        // ListView の更新
        adapter.notifyDataSetChanged();
    }

}
