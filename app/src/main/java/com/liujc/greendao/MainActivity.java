package com.liujc.greendao;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liujc.greendao.Bean.User;
import com.liujc.greendao.DataMigrate.MigrationHelper;
import com.liujc.greendao.dao.UserDao;
import com.liujc.greendao.manager.UserDbManager;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //@Transient，该注解表示这个属性将不会作为数据表中的一个字段
    //@NotNull表示该字段不可以为空，@Unique表示该字段唯一

    @BindView(R.id.insert)
    TextView insert;
    @BindView(R.id.delete)
    TextView delete;
    @BindView(R.id.update)
    TextView update;
    @BindView(R.id.query)
    TextView query;
    @BindView(R.id.querydataBy)
    TextView querydataBy;
    @BindView(R.id.et_name)
    EditText editText;
    @BindView(R.id.show_msg)
    TextView show_msg;

    private UserDbManager mUserDbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mUserDbManager = new UserDbManager();
        MigrationHelper.DEBUG = true;
        show_msg.setText("hlldf");
    }

    @OnClick({R.id.insert,R.id.delete,R.id.update,R.id.query,R.id.querydataBy,R.id.btn_add})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.insert:
                insertData(editText.getText().toString());
                Toast.makeText(this,"insert",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                mUserDbManager.deleteByKey(Long.parseLong(editText.getText().toString()));
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
                queryData();
                break;
            case R.id.update:
                updateData(Long.parseLong(editText.getText().toString()));
                Toast.makeText(this,"update",Toast.LENGTH_SHORT).show();
                break;
            case R.id.query:
                queryData();
                Toast.makeText(this,"query",Toast.LENGTH_SHORT).show();
                break;
            case R.id.querydataBy:
                queryDataBy(editText.getText().toString());
                break;
            case R.id.btn_add:
                insertData(editText.getText().toString());
                Toast.makeText(this,"insert",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void getuserById() {
//        User user =getUserDao().load(1l);
        User user = mUserDbManager.selectByPrimaryKey((long) 11);
        Log.i("tag", "结果：" + user.getId() + "," + user.getName() + "," + user.getAge() + "," + user.getIsBoy() + ";");

    }

    //插入数据
    private void insertData(String name) {
        User insertData = new User(null, name, 24, false,0);
        Log.d("TAG",mUserDbManager.insert(insertData)+"");
        queryData();
    }

    //更改数据
    private void updateData(Long id) {
        User user = new User(id, "更改后的数据用户", 22, true,0);
        new UserDbManager().update(user);
        queryData();
    }

    //查询数据详细
    private void queryData() {
        List<User> users = mUserDbManager.loadAll();
        StringBuilder sb = new StringBuilder();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
            sb.append(users.get(i).getId()).append(",").append(users.get(i).getName()).append(",").append(users.get(i).getAge()).append(",").append(users.get(i).getIsBoy()).append(";\n");

        }
        show_msg.setText(sb.toString());
    }

    private void queryDataBy(String name) {////查询条件
        Query<User> nQuery = mUserDbManager.getQueryBuilder()
                .where(UserDao.Properties.Name.eq(name))//.where(UserDao.Properties.Id.notEq(999))
                .orderAsc(UserDao.Properties.Age)//.limit(5)//orderDesc
                .build();
        List<User> users = nQuery.list();
        Log.i("tag", "当前数量：" + users.size());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
            sb.append(users.get(i).getId()).append(",").append(users.get(i).getName()).append(",").append(users.get(i).getAge()).append(",").append(users.get(i).getIsBoy()).append(";\n");
        }
        show_msg.setText(sb.toString());
    }


    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<User> queryN(String where, String... params){
        return mUserDbManager.queryRaw(where, params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public boolean saveN(User user){
        return mUserDbManager.insertOrReplace(user);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        mUserDbManager.runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User user = list.get(i);
                    mUserDbManager.insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote(){
        mUserDbManager.deleteAll();
    }

    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(User user){
        mUserDbManager.delete(user);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View view = getCurrentFocus();
            if(view != null && isHideInput(view,ev)){
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View view, MotionEvent ev) {
        if(view instanceof EditText){
            int[] l = {0,0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            if(ev.getX() > left && ev.getX() < right && ev.getY() < bottom && ev.getY() > top){
                return false;
            }else {
                return true;
            }
        }
        return true;
    }

    private void hideSoftInput(IBinder token){
        InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(token,InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
