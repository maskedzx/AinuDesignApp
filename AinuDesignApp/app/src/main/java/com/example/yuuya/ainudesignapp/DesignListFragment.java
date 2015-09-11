package com.example.yuuya.ainudesignapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class DesignListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String TAG = DesignListFragment.class.getSimpleName();
    public static final String ACTION_CREATE = "action-create";
    private static final int MENU_ID_DELETE = 1;
    private ListAdapter mAdapter;
    private List<Design> mList;

    public static DesignListFragment newInstance() {
        return new DesignListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        //ListViewを初期化
        ListView listView = (ListView) rootView.findViewById(R.id.show_list);
        listView.setOnItemClickListener(this);
        listView.setAdapter(mAdapter);

        //ListViewにコンテキストメニューを設定
        registerForContextMenu(listView);

        //BroadcastReceiverを登録
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mAddReceiver, new IntentFilter(ACTION_CREATE));

        return rootView;
    }

    @Override
    public void onDestroy() {
        //BroadcastReceiverを解除
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mAddReceiver);
        super.onDestroy();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        //ListViewのコンテキストメニューを作成
        if (view.getId() == R.id.show_list) {
            menu.setHeaderTitle("選択アイテム");
            menu.add(0, MENU_ID_DELETE, 0, "削除");
        }
    }

    //コンテキストメニュークリック時のリスナー
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int itemId = item.getItemId();
        if (itemId == MENU_ID_DELETE) {
            //アイテムを削除
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * designリストの作成・変更を検知するBroadcastReceiver.
     */
    BroadcastReceiver mAddReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //デザインデータを作成
            String value = intent.getStringExtra(DesignSaveFragment.ARGS_VALUE);
            long createdTime = intent.getLongExtra(DesignSaveFragment.ARGS_CREATEDTIME, 0);
            Design newItem = new Design(value, createdTime);

            //作成時間を既に存在するデータか確認
            int updateIndex = -1;
            for (int i = 0; i < mAdapter.getCount(); i++) {
                Design item = mAdapter.getItem(i);
                if (item.getCreatedTime() == newItem.getCreatedTime()) {
                    updateIndex = i;
                }
            }
            if (updateIndex == -1) {
                //既存データがなければ新規デザインとして追加
                mList.add(newItem);
            } else {
                //既存データがあれば上書き
                mList.remove(updateIndex);
                mList.add(updateIndex, newItem);
            }

            //デザインリストを更新
            mAdapter.notifyDataSetChanged();

        }
    };

}
