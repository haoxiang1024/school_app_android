package com.xuexiang.templateproject.fragment.personal.user;

import static com.xuexiang.templateproject.activity.SplashActivity.LANG_KEY;

import android.annotation.SuppressLint;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.webview.AgentWebActivity;
import com.xuexiang.templateproject.databinding.FragmentSuggestionBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.Locale;

@Page()
public class SuggestionFragment extends BaseFragment<FragmentSuggestionBinding> implements SuperTextView.OnSuperTextViewClickListener, View.OnClickListener {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentSuggestionBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentSuggestionBinding.inflate(inflater, container, false);
    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.help);
    }
    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.hot1.setOnSuperTextViewClickListener(this);
        binding.hot2.setOnSuperTextViewClickListener(this);
        binding.hot3.setOnSuperTextViewClickListener(this);
        binding.suBtn.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        //获取app当前语言
        String currentLanguage = Utils.language(getContext());
        if(currentLanguage.equals("zh")){
            switch (id) {
                case R.id.hot1:
                    //闪退如何解决
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/appcrash.html", getContext()));
                    break;
                case R.id.hot2:
                    //手机号/账户问题
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/account.html", getContext()));
                    break;
                case R.id.hot3:
                    //隐私保护问题
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/privacy.html", getContext()));
                    break;
            }
        } else if (currentLanguage.equals("en")) {
            switch (id) {
                case R.id.hot1:
                    //闪退如何解决
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/appcrash_en.html", getContext()));
                    break;
                case R.id.hot2:
                    //手机号/账户问题
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/account_en.html", getContext()));
                    break;
                case R.id.hot3:
                    //隐私保护问题
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/privacy_en.html", getContext()));
                    break;
            }
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == binding.suBtn.getId()) {
            //意见反馈页
            AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/contract.html", getContext()));
        }
    }
}