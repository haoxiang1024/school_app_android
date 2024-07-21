package com.xuexiang.templateproject.fragment.look;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.Found;
import com.xuexiang.templateproject.adapter.entity.User;
import com.xuexiang.templateproject.adapter.lostandfoundnav.FoundDetailAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentFoundInfoBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@Page()
public class FoundInfoFragment extends BaseFragment<FragmentFoundInfoBinding> implements AdapterView.OnItemClickListener {

    private FoundDetailAdapter foundDetailAdapter;

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentFoundInfoBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentFoundInfoBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        startAnim();//显示加载动画
        initData();//初始化列表数据
    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.found_info);
    }
    private void initData() {
        foundDetailAdapter = new FoundDetailAdapter(getContext());
        binding.listview.setAdapter(foundDetailAdapter);
        getData();//请求数据
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.listview.setOnItemClickListener(this);
    }

    private void getData() {
        //获取用户信息
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/getAllFoundUserId?user_id=" + user.getId(), getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        getActivity().runOnUiThread(FoundInfoFragment.this::stopAnim);//结束加载动画
                        //没有发布信息
                        if (JsonOperate.getValue(result, "msg").equals("还未发布任何信息")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.no_info_posted_yet));
                            return;
                        }
                        getActivity().runOnUiThread(() -> setAdapter(result));//设置适配器
                    }
                });
            }
        }.start();

    }

    private void setAdapter(String result) {
        foundDetailAdapter = new FoundDetailAdapter(getContext());
        binding.listview.setAdapter(foundDetailAdapter);
        //结果转换
        //数据list
        List detailList = JsonOperate.getList(result, Found.class);
        //设置数据
        foundDetailAdapter.setData(detailList, 1);
    }

    //显示加载动画
    private void startAnim() {
        binding.avLoad.show();
    }

    //结束加载动画
    private void stopAnim() {
        binding.avLoad.hide();
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Found found = foundDetailAdapter.getItem(position);
        openPage(FoundInfoDetailFragment.class, FoundInfoDetailFragment.KEY_FOUND, found);

    }
}