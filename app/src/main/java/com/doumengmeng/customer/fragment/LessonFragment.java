package com.doumengmeng.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.LessonAdapter;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragment;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.SearchLessonResponse;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.view.XLoadMoreCustomerFooter;
import com.doumengmeng.customer.view.XRefreshCustomerHeader;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 萌课堂
 * 创建日期: 2018/1/8 14:06
 */
public class LessonFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv;
    private LessonAdapter adapter;
    private List<LessonAdapter.LessonData> list = new ArrayList<>();

    public final static int PAGE_SIZE = 4;
    private int page = 0;
    private int originalPage = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_lesson, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        xrv = view.findViewById(R.id.xrv);
        initView();
    }

    private void initView() {
        rl_back.setVisibility(View.INVISIBLE);
        tv_title.setText(R.string.meng_lesson);

        xrv.setRefreshHeader(new XRefreshCustomerHeader(getContext()));
        xrv.setFootView(new XLoadMoreCustomerFooter(getContext()));
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingMoreEnabled(true);
        xrv.setLoadingListener(loadingListener);

        adapter = new LessonAdapter(list);
        xrv.setAdapter(adapter);
//        initTestData();
        refresh();
    }

    private void refresh(){
        originalPage = page;
        page = 0;
        searchLessonItem();
    }

    private void loadMore(){
        //TODO
        originalPage = page;
        page++;
        searchLessonItem();
    }

    private RequestTask searchLessonTask = null;
    private void searchLessonItem(){
        try {
            if ( searchLessonTask != null ){
                searchLessonTask.cancel(true);
            }
            searchLessonTask = new RequestTask.Builder(getContext(),searchLessonTaskCallback)
                                .setContent(buildRequestContent())
                                .setType(RequestTask.JSON)
                                .setUrl(UrlAddressList.URL_SEARCH_LESSON)
                                .build();
            searchLessonTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack searchLessonTaskCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {
            loadingFailed();
        }

        @Override
        public void onPostExecute(String result) {
            SearchLessonResponse response = GsonUtil.getInstance().fromJson(result, SearchLessonResponse.class);
            if ( response.getResult() != null && response.getResult().getMengClassList() != null ) {
                updateView(response.getResult().getMengClassList());
            } else {
                loadingFailed();
                //TODO
            }
        }
    };

    private Map<String,String> buildRequestContent(){
        Map<String,String> map = new HashMap<>();
        UserData userData = BaseApplication.getInstance().getUserData();

        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("page",page+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private void loadingFailed(){
        if ( page == 0 ){
            xrv.refreshFailed();
        } else {
            xrv.loadMoreFailed();
        }
        page = originalPage;
    }

    private void updateView(List<SearchLessonResponse.LessonItem> items){
        if ( page == 0){
            xrv.refreshComplete();
            list.clear();
        } else if ( items.size() == 0 ) {
            xrv.setNoMore(true);
        } else {
            xrv.loadMoreComplete();
        }
        for (SearchLessonResponse.LessonItem item:items){
            LessonAdapter.LessonData data = new LessonAdapter.LessonData();
            data.setContentUrl(item.getClassurl());
            data.setTitle(item.getClasstitle());
            data.setContent(item.getClassdesc());
            data.setImgUrl(item.getClassimg());
            data.setDate(item.getClasstime());
            list.add(data);
        }
        adapter.notifyDataSetChanged();
    }

//    private String title = "测试标题：";
//    private String content = "测试内容：";
//    private void initTestData(){
//        for (int i= 0;i<4;i++){
//            LessonAdapter.LessonData data = new LessonAdapter.LessonData();
//            data.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529649072338&di=6c626ed0bec072730b0313a579a242ad&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201509%2F23%2F140610ebbookekbr1qbte1.jpg");
//            data.setTitle(title+(list.size()+1));
//            data.setContent(content+(list.size()+1));
//            title = title + title;
//            content = content + content;
//            data.setContentUrl("http://www.baidu.com");
//            data.setDate("2018-6-22");
//            list.add(data);
//        }
//        adapter.notifyDataSetChanged();
//    }

//    private int count = 0;
    private XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            refresh();
//            xrv.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    xrv.refreshComplete();
//                }
//            },1000);
        }

        @Override
        public void onLoadMore() {
            loadMore();
//            xrv.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    count++;
//                    if ( count > 3 ){
//                        xrv.setNoMore(true);
//                    } else {
//                        initTestData();
//                        xrv.loadMoreComplete();
//                    }
//                }
//            },1000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if ( isVisible() && !isHidden() ) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){

        }
    }
}
