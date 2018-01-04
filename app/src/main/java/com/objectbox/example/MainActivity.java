package com.objectbox.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.objectbox.example.entity.UserInfo;
import com.objectbox.example.entity.UserInfo_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;

public class MainActivity extends Activity {
    Box<UserInfo> userInfoBox;
    DataSubscription observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfoBox = App.getBoxStore().boxFor(UserInfo.class);

    }

    UserInfo userInfo;

    public void insert(View view) {
        //保存单个对象
        userInfo = new UserInfo();
        userInfo.username = "张三";
        userInfo.password = "12345";
        long geterateId = userInfoBox.put(userInfo);

    }

    public void update(View view) {
        /**
         * update逻辑同put：同样id的对象就会做update
         */
    }

    public void delete(View view) {
        //根据id删除
        userInfoBox.remove(1);

        //删除对象
        if (null != userInfo) {
            userInfoBox.remove(userInfo);
        }

        //删除所有的
        userInfoBox.removeAll();
    }

    public void query(View view) {
        //1、根据id查询
        UserInfo userInfo = userInfoBox.get(1);
        System.out.println("根据id查询 userinfo=" + userInfo);

        //2、查询所有的
        List<UserInfo> userInfos = userInfoBox.getAll();
        System.out.println("查询所有的 userInfos=" + userInfos);

        //3、带条件的查询
        QueryBuilder<UserInfo> query = userInfoBox.query();
        //构建查询条件
        query.equal(UserInfo_.id, 1); //id=1
        query.equal(UserInfo_.username, "张三"); //username="张三"
        Query<UserInfo> builder = query.build();
        //开始查询
        userInfos = builder.find();
        System.out.println("带条件的查询 userInfos=" + userInfos);

        //当前查询条件的数据个数
        long count = builder.count();
        System.out.println("count=" + count);

        //查询满足条件的唯一值，如果多于一个则抛出异常。
        try {
            UserInfo unique = builder.findUnique();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //监听数据变化
        observer = builder.subscribe().on(AndroidScheduler.mainThread()).observer(new DataObserver<List<UserInfo>>() {
            @Override
            public void onData(List<UserInfo> data) {

            }
        });


        //重用Query  setParameter 改变条件值
        Query<UserInfo> userQuery = userInfoBox.query().equal(UserInfo_.username, "").build();
        List<UserInfo> lisi = userQuery.setParameter(UserInfo_.username, "李四").find();
        List<UserInfo> wangwu = userQuery.setParameter(UserInfo_.username, "王五").find();

        //分页查询   find(long offset, long limit)
        builder.find(5, 10);// 从第6条开始，返回10条数据


        //聚合函数
        /*
        min / minDouble: Finds the minimum value for the given property over all objects matching the query.
        max / maxDouble: Finds the maximum value.
        sum / sumDouble : Calculates the sum of all values. Note: the non-double version detects overflows and throws an exception in that case.
        avg : Calculates the average (always a double) of all values.
         */

        /*
          query.remove()；移除满足条件的对象
         */

    }

    public void transaction(View view) {
        /**
         * 1、put操作默认使用隐式事务
         * 2、如果在一个循环中需要大量的DB交互，建议使用runInTx()
         */
        App.getBoxStore().runInTx(new Runnable() {
            @Override
            public void run() {
                List<UserInfo> userInfos = new ArrayList<UserInfo>();
                for (UserInfo user : userInfos) {
                    if (true) {
                        userInfoBox.put(user);
                    } else {
                        userInfoBox.remove(user);
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销数据监听
        if (null != observer) {
            observer.cancel();
        }
    }
}
