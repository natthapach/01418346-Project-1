package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.managers.ProfileManager;
import cs.sci.ku.cookyalpha.views.UserItemView;

public class UserListActivity extends AppCompatActivity {

    ArrayList<User> users;
    private ListView userListView;
    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int i) {
            return users.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = new UserItemView(UserListActivity.this);
            UserItemView uview = (UserItemView) view;
            uview.setUser(users.get(i));
            return uview;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initInstance();
    }

    private void initInstance() {
        Intent intent = getIntent();
        users = new ArrayList<>();

        String[] ids = intent.getStringArrayExtra("userIds");
        for (String id : ids){
            ProfileManager.getInstance()
                        .loadUser(id, new OnResult<User>() {
                            @Override
                            public void onResult(User user) {
                                users.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        });
        }

        userListView = findViewById(R.id.lv_users);
        userListView.setAdapter(adapter);
    }
}
