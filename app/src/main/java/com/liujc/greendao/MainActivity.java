package com.liujc.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MigrationHelper.DEBUG = true;
        show_msg.setText("hlldf");
    }

    @OnClick({R.id.insert,R.id.delete,R.id.update,R.id.query,R.id.querydataBy,R.id.btn_add})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.insert:
                insertdata(editText.getText().toString());
                Toast.makeText(this,"insert",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                getUserDao().deleteByKey(Long.parseLong(editText.getText().toString()));
                querydata();
                break;
            case R.id.update:
                updatadata(Long.parseLong(editText.getText().toString()));
                break;
            case R.id.query:
                querydata();
                break;
            case R.id.querydataBy:
                querydataBy(editText.getText().toString());
                break;
            case R.id.btn_add:
                insertdata(editText.getText().toString());
                break;
        }
    }
    private void getuserById() {
//        User user =getUserDao().load(1l);
        User user = new UserDbManager().selectByPrimaryKey((long) 11);
        Log.i("tag", "结果：" + user.getId() + "," + user.getName() + "," + user.getAge() + "," + user.getIsBoy() + ";");

    }

    private void insertdata(String name) {
        //插入数据
        User insertData = new User(null, name, 24, false,0);
//        getUserDao().insert(insertData);
        Log.d("TAG",new UserDbManager().insert(insertData)+"");
        querydata();
    }

    private void updatadata(Long id) {
        //更改数据
        List<User> userss = getUserDao().loadAll();
        User user = new User(id, "更改后的数据用户", 22, true,0);
//        getUserDao().update(user);
        new UserDbManager().update(user);
    }

    private void querydata() {
        //查询数据详细
//        List<User> users = getUserDao().loadAll();
        List<User> users = getUserDao().loadAll();
        StringBuffer sb = new StringBuffer();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
            sb.append(users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";\n");

        }
        show_msg.setText(sb.toString());
    }

    private void querydataBy(String name) {////查询条件
        Query<User> nQuery = getUserDao().getQueryBuilder()
                .where(UserDao.Properties.Name.eq(name))//.where(UserDao.Properties.Id.notEq(999))
                .orderAsc(UserDao.Properties.Age)//.limit(5)//orderDesc
                .build();
        List<User> users = nQuery.list();
        Log.i("tag", "当前数量：" + users.size());
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
            sb.append(users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";\n");
        }
        show_msg.setText(sb.toString());

//        QueryBuilder qb = userDao.queryBuilder();
//        qb.where(Properties.FirstName.eq("Joe"),
//                qb.or(Properties.YearOfBirth.gt(1970),
//                        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
//        List youngJoes = qb.list();
    }


    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<User> queryN(String where, String... params){
        return getUserDao().queryRaw(where, params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public boolean saveN(User user){
        return getUserDao().insertOrReplace(user);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        getUserDao().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User user = list.get(i);
                    getUserDao().insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote(){
        getUserDao().deleteAll();
    }

    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(User user){
        getUserDao().delete(user);
    }
//    private UserDao getUserDao() {
//        return GreenDaoManager.getInstance().getSession().getUserDao();
//    }
    private UserDbManager getUserDao() {
        return new UserDbManager();
    }
}
